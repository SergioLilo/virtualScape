package es.gmm.psp.virtualScape.controller;

import es.gmm.psp.virtualScape.model.ApiRespuesta;
import es.gmm.psp.virtualScape.model.Reserva;
import es.gmm.psp.virtualScape.service.ReservaService;
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
@RequestMapping("api/virtual-escape/reservas")
public class ApiReservas {

    @Autowired
    private ReservaService reservaService;

    @Operation(summary = "Obtener todas las reservas", description = "Devuelve una lista de todas las reservas")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Hay Reserva",
                    content = @Content(schema = @Schema(implementation = Reserva.class))),
            @ApiResponse(responseCode = "204", description = "No Content")})
    @GetMapping
    public List<Reserva> getReservas() {

        return reservaService.findAll();
    }

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
        Reserva reserva = reservaService.findById(id);
        if (reserva != null) {
            return ResponseEntity.ok(reserva);
        }else{
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ApiRespuesta(false, "Reserva no encontrada", null));}
    }
    // POST respuestas
    @Operation(summary = "Crear una nueva reserva", description = "Crea una nueva reserva si no hay conflicto de horarios")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Reserva creada correctamente",
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
                                    example = "{\"exito\": false, \"mensajeError\": \"Los datos enviados no son válidos: nombre de sala no encontrado\", \"idGenerado\": null}"

                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Conflicto: choque de horarios con otra reserva",
                    content = @Content(
                            schema = @Schema(
                                    example = "{\"exito\": false, \"mensajeError\": \"Conflicto de horarios: ya existe una reserva para ese horario\", \"idGenerado\": null}"
                            )
                    )
            )
    })
    @PostMapping
    public ResponseEntity<ApiRespuesta> crearReserva(@Valid @RequestBody Reserva reserva) {
        boolean comprobacion = reservaService.verificarConflicto(reserva);
        if (comprobacion) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ApiRespuesta(false, "Conflicto de horarios con otra reserva", null));
        }

        Reserva nuevaReserva = reservaService.save(reserva);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiRespuesta(true, "Reserva creada con éxito", nuevaReserva.getId()));
    }
    @Operation(summary = "Actualizar una reserva", description = "Actualiza de forma completa una reserva existente con los nuevos datos")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Reserva actualizada correctamente",
                    content = @Content(
                            schema = @Schema(
                                    example = "{\"exito\": true, \"mensajeError\": \"\", \"idGenerado\": null}"
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Datos inválidos en la petición",
                    content = @Content(
                            schema = @Schema(
                                    example = "{\"exito\": false, \"mensajeError\": \"Datos inválidos en la petición\", \"idGenerado\": null}"
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
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Conflicto: choque de horarios con otra reserva",
                    content = @Content(
                            schema = @Schema(
                                    example = "{\"exito\": false, \"mensajeError\": \"Conflicto de horarios con otra reserva\", \"idGenerado\": null}"

                            )
                    )
            )
    })
    @PutMapping("/{id}")
    public ResponseEntity<ApiRespuesta> actualizarReserva(@PathVariable String id, @Valid @RequestBody Reserva reserva) {
        Reserva reservaExistente = reservaService.findById(id);
        if (reservaExistente == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiRespuesta(false, "Reserva no encontrada", null));
        }

        reserva.setId(id); // Asegurar que se usa el ID correcto
        boolean hayConflicto = reservaService.verificarConflicto(reserva);
        if (hayConflicto) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ApiRespuesta(false, "Conflicto de horarios con otra reserva", null));
        }

        try {
            Reserva reservaActualizada = reservaService.actualizarReserva(reserva);
            Reserva verificacion = reservaService.findById(reservaActualizada.getId());
            if (verificacion == null) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(new ApiRespuesta(false, "Error al verificar la actualización", null));
            }
            return ResponseEntity.ok(new ApiRespuesta(true, "Reserva actualizada con éxito", reservaActualizada.getId()));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiRespuesta(false, "Datos inválidos en la petición", null));
        }
    }
    @Operation(summary = "Eliminar una reserva", description = "Elimina una reserva existente dado su identificador")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Reserva eliminada con éxito",
                    content = @Content(
                            schema = @Schema(
                                    example = "{\"exito\": true, \"mensajeError\": \"\", \"idGenerado\": null}"
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
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiRespuesta> eliminarReserva(@PathVariable String id) {
        Reserva reserva = reservaService.findById(id);
        if (reserva == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiRespuesta(false, "Reserva no encontrada", null));
        }

        reservaService.eliminarReserva(id);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiRespuesta(true, "Reserva eliminada con éxito", id));
    }

}
