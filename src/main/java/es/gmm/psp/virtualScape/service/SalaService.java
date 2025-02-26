package es.gmm.psp.virtualScape.service;

import es.gmm.psp.virtualScape.model.Sala;
import es.gmm.psp.virtualScape.repository.SalaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SalaService {

    private static final Logger log = LoggerFactory.getLogger(SalaService.class);
    @Autowired
    private SalaRepository salaRepository;

    public void insertarSala(Sala s){
        log.info("insertando sala...");
        if (s.getCapacidadMin()>= 1 && s.getCapacidadMax() <=8) {
            int totalJugadores = getTotalJugadores();
                if (totalJugadores + s.getCapacidadMax() <= 30){
                    Sala salaExistente = buscaPorNombre(s.getNombre());
                        if (salaExistente == null){
                            System.out.println("Sala insertada correctamente");
                            salaRepository.save(s);
                        }
                        else {
                            log.error("Ya existe una sala con ese nombre");
                        }
                     }else {
                    log.error("Capacidad maxima Alcanzada (30 jugadores)");
                }

        }else {
            log.error("Minimo 1 Jugador, Maximo 8 Jugadores");
        }
    }

    public Sala buscaPorNombre(String nombre){

        log.info("Buscando por nombre...");
        Sala sala=salaRepository.findByNombre(nombre);
        return sala;
    }

    public int getTotalJugadores() {
        log.info("Consiguiendo el total de jugadores");
        List<Sala> salas = salaRepository.findAll();
        int totalJugadores = 0;
        for (Sala sala : salas) {
            totalJugadores += sala.getCapacidadMax();
        }
        return totalJugadores;
    }
    public List<Sala> findAll(){
        log.info("Consiguiendo todas las salas");
        return salaRepository.findAll();
    }
    public Sala save(Sala sala) { return  salaRepository.save(sala);}
    public Sala findById(String id){
        log.info("Buscando por id");
        return salaRepository.findById(id).orElse(null);
    }

    public Sala actualizarSala(Sala sala) {
        if (sala == null || sala.getId() == null) {
            throw new IllegalArgumentException("Sala inválida");
        }
        return salaRepository.save(sala);
    }

    public List<Sala> findByTematica(String nombreTematica) {
        log.info("Buscando por tematica");
        return salaRepository.findByTematicas(nombreTematica);
    }
    public List<Sala> top2SalasConMasReservas() {
        return salaRepository.findTop2ByOrderByReservaAsc();
    }
}
