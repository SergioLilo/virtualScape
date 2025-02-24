package es.gmm.psp.virtualScape.service;

import es.gmm.psp.virtualScape.model.Reserva;
import es.gmm.psp.virtualScape.model.Sala;
import es.gmm.psp.virtualScape.repository.SalaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SalaService {

    @Autowired
    private SalaRepository salaRepository;

    public void insertarSala(Sala s){

        if (s.getCapacidadMax()<30){
        salaRepository.save(s);
        System.out.println("Sala insertada correctamente");
        }else {
            System.out.println("El sistema tiene un aforo limitado a 30 personas.");
        }
    }

    public Sala buscaPorNombre(String nombre){

        Sala sala=salaRepository.findByNombre(nombre);
        return sala;
    }

    public List<Sala> findAll(){
        return salaRepository.findAll();
    }
    public Sala save(Sala sala) { return  salaRepository.save(sala);}


}
