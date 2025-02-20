package es.gmm.psp.virtualScape.controller;

import es.gmm.psp.virtualScape.Model.Reserva;
import es.gmm.psp.virtualScape.repository.ReservaRepository;
import es.gmm.psp.virtualScape.service.ReservaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
import java.util.Optional;

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

    @Operation(summary = "Obtener reserva por id", description = "Obtener reserva por id")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Hay Reserva",
                    content = @Content(schema = @Schema(implementation = Reserva.class))),
            @ApiResponse(responseCode = "404", description = "No Encontrada")})

    @GetMapping("/reserva/{paco}")
    public ResponseEntity<Reserva> getReservasPorId(
            @Parameter(description = "ID de la serie", example = "1")
            @PathVariable String paco) {
         Reserva reserva =  reservaService.encontrarPorId(paco);

        if(reserva != null){
            return new ResponseEntity<>(reserva, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }
}
}
