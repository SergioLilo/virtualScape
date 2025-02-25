package es.gmm.psp.virtualScape.service;

import es.gmm.psp.virtualScape.model.Reserva;
import es.gmm.psp.virtualScape.model.Sala;
import es.gmm.psp.virtualScape.repository.ReservaRepository;
import es.gmm.psp.virtualScape.repository.SalaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservaService {

    @Autowired
    private ReservaRepository reservaRepository;
    @Autowired
    private SalaRepository salaRepository;

    public void insertarReserva(Reserva reserva) {


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
                System.out.println("Reserva insertada con éxito.");
                System.out.println(reserva);
                }else {
                    System.out.println("Ya hay una reserva en esa hora");
                }
            }else {
                System.out.println("El número de jugadores debe cumplir el límite de la sala.");
            }

        }else {
            System.out.println("Sala no encontrada");
        }



    }

    public Reserva save(Reserva reserva) { return  reservaRepository.save(reserva);}


    public List<Reserva> buscarPorNombre(String nombre){

       List<Reserva> reservas= reservaRepository.findByNombreSala(nombre);
        return reservas;
    }

    public List<Reserva>  findAll(){
    return reservaRepository.findAll();
    }

    public Reserva findById(String id){
        return reservaRepository.findById(id).orElse(null);
    }

    public boolean verificarConflicto(Reserva reserva){
        Sala sala=salaRepository.findByNombre(reserva.getNombreSala());
        System.out.println(sala);
        if (sala==null) {
        return true;
        }
            List<Reserva> reservas = findAll();
            for (Reserva r : reservas) {
                if (r.getFecha().getDiaReserva() == reserva.getFecha().getDiaReserva() &&
                        r.getFecha().getHoraReserva() == reserva.getFecha().getHoraReserva()) {
                    return true;
                }
            }


        return false;
    }
    public Reserva actualizarReserva(Reserva reserva) {
        if (reserva == null || reserva.getId() == null) {
            return null;
        }

        return reservaRepository.save(reserva);
    }
    public void eliminarReserva(String id){
        reservaRepository.deleteById(id);
    }

    public List<Reserva> findByDia(int numDia) {
        return reservaRepository.findByFechaDiaReserva(numDia);
    }
}


