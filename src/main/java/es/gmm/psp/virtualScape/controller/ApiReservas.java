package es.gmm.psp.virtualScape.controller;

import es.gmm.psp.virtualScape.Model.ApiRespuesta;
import es.gmm.psp.virtualScape.Model.Reserva;
import es.gmm.psp.virtualScape.service.ReservaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
