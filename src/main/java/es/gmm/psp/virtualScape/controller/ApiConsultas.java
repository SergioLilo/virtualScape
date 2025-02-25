package es.gmm.psp.virtualScape.controller;

import es.gmm.psp.virtualScape.model.ConsultaEspecial;
import es.gmm.psp.virtualScape.model.Contacto;
import es.gmm.psp.virtualScape.model.Reserva;
import es.gmm.psp.virtualScape.service.ReservaService;
import es.gmm.psp.virtualScape.service.SalaService;
import io.swagger.v3.oas.annotations.Operation;
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

}
