package es.gmm.psp.virtualScape.service;

import es.gmm.psp.virtualScape.model.Reserva;
import es.gmm.psp.virtualScape.model.Sala;
import es.gmm.psp.virtualScape.repository.ReservaRepository;
import es.gmm.psp.virtualScape.repository.SalaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservaService {

    private static final Logger log = LoggerFactory.getLogger(ReservaService.class);
    @Autowired
    private ReservaRepository reservaRepository;
    @Autowired
    private SalaRepository salaRepository;

    public void insertarReserva(Reserva reserva) {

        log.info("Insertando Reserva...");
        boolean disponible=true;
        Sala sala=salaRepository.findByNombre(reserva.getNombreSala());

        if (sala!=null){
            if (reserva.getJugadores() >= sala.getCapacidadMin()
                    && reserva.getJugadores() <= sala.getCapacidadMax()){

                List<Reserva> reservas=buscarPorNombre(reserva.getNombreSala());

                for (Reserva r:reservas){
                    if (r.getFecha().getDiaReserva()==reserva.getFecha().getDiaReserva() &&
                            r.getFecha().getHoraReserva()==reserva.getFecha().getHoraReserva()){
                    disponible=false;
                    }
                }

                if (disponible){
                reserva.setJugadores(sala.getCapacidadMax());
                reservaRepository.save(reserva);
                log.info("Reserva insertada con éxito. "+reserva);
                ;

                }else {
                    log.error("Ya hay una reserva en esa hora");
                    ;
                }
            }else {
                log.error("El número de jugadores debe cumplir el límite de la sala.");

            }

        }else {
            log.error("la sala no existe");
            System.out.println("Sala no encontrada");
        }



    }

    public Reserva save(Reserva reserva) { return  reservaRepository.save(reserva);}


    public List<Reserva> buscarPorNombre(String nombre){

        log.info("Buscando sala por nombre");
       List<Reserva> reservas= reservaRepository.findByNombreSala(nombre);
        return reservas;
    }

    public List<Reserva>  findAll(){
        log.info("Devolviendo todas la salas");
        return reservaRepository.findAll();
    }

    public Reserva findById(String id){
        log.info("Buscando por ID");
        return reservaRepository.findById(id).orElse(null);
    }

    public boolean verificarConflicto(Reserva reserva){
        log.info("Verificando conflictos" );
        Sala sala=salaRepository.findByNombre(reserva.getNombreSala());
        System.out.println(sala);
        if (sala==null) {
            log.error("La sala no existe");
        return true;
        }
            List<Reserva> reservas = findAll();
            for (Reserva r : reservas) {
                if (r.getFecha().getDiaReserva() == reserva.getFecha().getDiaReserva() &&
                        r.getFecha().getHoraReserva() == reserva.getFecha().getHoraReserva()) {
                    log.error("La sala tiene conflicto horario con otra");
                    return true;
                }
            }


        return false;
    }
    public Reserva actualizarReserva(Reserva reserva) {
        if (reserva == null || reserva.getId() == null) {
            log.error("La sala no existe");
            return null;
        }
        log.info("Reserva Actualizada");
        return reservaRepository.save(reserva);
    }
    public void eliminarReserva(String id){
        log.info("Eliminando Reserva");
        reservaRepository.deleteById(id);
    }

    public List<Reserva> findByDia(int numDia) {
        return reservaRepository.findByFechaDiaReserva(numDia);
    }
}


