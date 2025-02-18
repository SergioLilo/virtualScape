package es.gmm.psp.virtualScape.repository;


import es.gmm.psp.virtualScape.Model.Sala;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SalaRepository extends MongoRepository<Sala, String> {

    Sala findByNombre(String nombre);
}
