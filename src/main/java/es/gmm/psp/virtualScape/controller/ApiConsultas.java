package es.gmm.psp.virtualScape.controller;

import es.gmm.psp.virtualScape.model.*;
import es.gmm.psp.virtualScape.service.ReservaService;
import es.gmm.psp.virtualScape.service.SalaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
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

import java.util.*;

@RestController
@RequestMapping("api/virtual-escape/consultas")
public class ApiConsultas {

    @Autowired
    private ReservaService reservaService;

    @Autowired
    private SalaService salaService;

    // GET /reservas/dia/{numDia}
    @Operation(summary = "Obtener reservas por día", description = "Devuelve las reservas de un día específico con la información de la sala")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reservas encontradas"),
            @ApiResponse(responseCode = "204", description = "No hay reservas para ese día")
    })
    @GetMapping("/reservas/dia/{numDia}")
    public ResponseEntity<List<ConsultaEspecial>> getReservasPorDia(@PathVariable int numDia) {
        List<Reserva> reservas = reservaService.findByDia(numDia);

        if (reservas.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        List<ConsultaEspecial> consultaEspecials=new ArrayList<>();
        for (Reserva r:reservas){
            Contacto contactoEsp=new Contacto(r.getContacto().getTitular(),r.getContacto().getTelefono());
            ConsultaEspecial consultaEspecial=new ConsultaEspecial(r.getNombreSala(),r.getFecha().getDiaReserva(),r.getFecha().getHoraReserva(),contactoEsp,r.getJugadores());
           consultaEspecials.add(consultaEspecial);
        }

        return ResponseEntity.ok(consultaEspecials);
    }
    // GET /salas/tematica/{nombreTematica}
    @Operation(summary = "Consultar salas por temática", description = "Devuelve salas disponibles según la temática buscada")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Salas encontradas"),
            @ApiResponse(responseCode = "204", description = "No hay salas con esa temática")
    })
    @GetMapping("/salas/tematica/{nombreTematica}")
    public ResponseEntity<List<Sala>> getSalasPorTematica(@PathVariable String nombreTematica) {
        List<Sala> salas = salaService.findByTematica(nombreTematica);

        if (salas.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        return ResponseEntity.ok(salas);
    }

    @Operation(summary = "Listar salas con más reservas", description = "Devuelve las salas con mayor cantidad de reservas realizadas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Datos encontrados",
                    content = @Content(array = @ArraySchema(schema = @Schema(example = "[{\"id\": \"1\", \"nombre\": \"Sala A\", \"capacidad\": 5}, {\"id\": \"2\", \"nombre\": \"Sala B\", \"capacidad\": 3}]")))),
            @ApiResponse(responseCode = "204", description = "No se encontraron datos", content = @Content(schema = @Schema(example = "{\"exito\": false, \"mensaje\": \"No se encontraron datos\", \"idGenerado\": null}")))
    })
    @GetMapping("/mas-reservadas")
    public ResponseEntity<List<ConsultaMasReservas>> getSalasMasReservadas() {
        List<ConsultaMasReservas> resultado = salaService.obtenerSalasMasReservadas();
        if (resultado == null || resultado.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(resultado);
    }

}
