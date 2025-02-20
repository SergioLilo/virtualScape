package es.gmm.psp.virtualScape.repository;

import es.gmm.psp.virtualScape.Model.Reserva;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ReservaRepository extends MongoRepository<Reserva, String> {

    List<Reserva> findByNombreSala(String nombre);
    Reserva getById(String id);
}
