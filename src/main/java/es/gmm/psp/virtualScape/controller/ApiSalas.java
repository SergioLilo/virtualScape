package es.gmm.psp.virtualScape.controller;

import es.gmm.psp.virtualScape.model.ApiRespuesta;
import es.gmm.psp.virtualScape.model.Reserva;
import es.gmm.psp.virtualScape.model.Sala;
import es.gmm.psp.virtualScape.service.ReservaService;
import es.gmm.psp.virtualScape.service.SalaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/virtual-escape/salas")
public class ApiSalas {

    @Autowired
    private SalaService salaService;

    @Operation(summary = "Obtener todas las salas", description = "Devuelve una lista de todas las salas")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Hay Sala",
                    content = @Content(schema = @Schema(implementation = Sala.class))),
            @ApiResponse(responseCode = "204", description = "No Content")})
    @GetMapping
    public List<Sala> getSalas() {

        return salaService.findAll();
    }
    @Operation(summary = "Crear una nueva Sala", description = "Crea una nueva Sala si no hay conflicto de Capacidad")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Sala creada correctamente",
                    content = @Content(
                            schema = @Schema(
                                    example = "{\"exito\": true, \"mensajeError\": \"\", \"idGenerado\": \"123456789\"}"
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Datos inválidos",
                    content = @Content(
                        schema = @Schema(
                            example = "{\"exito\": false, \"mensajeError\": \"Datos no Validos\", \"idGenerado\":null}"
            )
    )
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Conflicto: Capacidad no valida",
                    content = @Content(
                            schema = @Schema(
                                    example = "{\"exito\": false, \"mensajeError\":La capacidad debe ser menor a 30 \"\", \"idGenerado\": null}"
                            )
                    )

            )
    })
    @PostMapping
    public ResponseEntity<ApiRespuesta> crearSala(@Valid @RequestBody Sala sala) {

        if (sala.getCapacidadMax()>30) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ApiRespuesta(false, "Capacidad de sala incorrecta", null));
        }

        Sala nuevaSala = salaService.save(sala);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiRespuesta(true, "Sala creada con éxito", nuevaSala.getId()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getSalaById(@PathVariable String id) {

        Sala sala=salaService.findById(id);

        if (sala != null) {
            return ResponseEntity.ok(sala);
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiRespuesta(false, "Reserva no encontrada", null));}

    }
    @PutMapping("/{id}")
    public ResponseEntity<ApiRespuesta> updateSala(@PathVariable String id, @Valid @RequestBody Sala salaActualizada) {
        // Verificar si la sala existe
        Sala salaExistente = salaService.findById(id);
        if (salaExistente == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiRespuesta(false, "Sala no encontrada", null));
        }

        // Validar datos de la sala
        if (salaActualizada.getCapacidadMax() > 30) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiRespuesta(false, "Capacidad no válida (máx. 30)", null));
        }

        salaActualizada.setId(id);
        salaService.save(salaActualizada);

        // Confirmar la actualización con un GET
        Sala salaVerificada = salaService.findById(id);
        if (salaVerificada!=null && salaVerificada.equals(salaActualizada)) {
            return ResponseEntity.ok(new ApiRespuesta(true, "Sala actualizada correctamente", id));
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiRespuesta(false, "Error verificando la actualización", null));
        }
    }

}
