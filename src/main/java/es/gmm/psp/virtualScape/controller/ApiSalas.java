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
     /*
    // GET reservas/id
    @Operation(summary = "Obtener detalles de una reserva", description = "Devuelve los detalles de una reserva específica dado su identificador")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Reserva encontrada",
                    content = @Content(
                            schema = @Schema(
                                    example = "{\"id\": \"1\", \"nombreSala\": \"Sala A\", \"fecha\": \"2025-02-20T10:00:00\", \"horaInicio\": \"10:00\", \"horaFin\": \"12:00\"}"
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Reserva no encontrada",
                    content = @Content(
                            schema = @Schema(
                                    example = "{\"exito\": false, \"mensajeError\": \"Reserva no encontrada\", \"idGenerado\": null}"
                            )
                    )
            )

    })

    @GetMapping("/{id}")
    public ResponseEntity<?> getReservaById(@PathVariable String id) {
      //  Reserva reserva = reservaService.findById(id);
        if (reserva != null) {
            return ResponseEntity.ok(reserva);
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiRespuesta(false, "Reserva no encontrada", null));}
    }*/

}
