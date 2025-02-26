package es.gmm.psp.virtualScape.service;

import es.gmm.psp.virtualScape.model.ConsultaMasReservas;
import es.gmm.psp.virtualScape.model.Reserva;
import es.gmm.psp.virtualScape.model.Sala;
import es.gmm.psp.virtualScape.repository.ReservaRepository;
import es.gmm.psp.virtualScape.repository.SalaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SalaService {

    private static final Logger log = LoggerFactory.getLogger(SalaService.class);
    @Autowired
    private SalaRepository salaRepository;
    @Autowired
    private ReservaRepository reservaRepository;
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
        log.info("Actualizando salas");
        if (sala == null || sala.getId() == null) {
            log.error("Sala invalida");
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
    public List<ConsultaMasReservas> obtenerSalasMasReservadas() {
        log.info("Buscando las 2 salas mas reservadas");
        List<Reserva> reservas = reservaRepository.findAll();
        Map<String, Integer> conteoReservas = new HashMap<>();

        for (Reserva reserva : reservas) {
            String nombreSala = reserva.getNombreSala();
            conteoReservas.put(nombreSala, conteoReservas.getOrDefault(nombreSala, 0) + 1);
        }
        List<Map.Entry<String, Integer>> listaOrdenada = new ArrayList<>(conteoReservas.entrySet());
        listaOrdenada.sort((a, b) -> Integer.compare(b.getValue(), a.getValue()));

        List<ConsultaMasReservas> resultado = new ArrayList<>();
        int limite = Math.min(listaOrdenada.size(), 2);

        for (int i = 0; i < limite; i++) {
            Map.Entry<String, Integer> entrada = listaOrdenada.get(i);
            resultado.add(new ConsultaMasReservas(entrada.getKey(), entrada.getValue()));
        }

        return resultado;
    }
}
