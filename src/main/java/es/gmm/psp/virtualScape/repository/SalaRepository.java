package es.gmm.psp.virtualScape.repository;


import es.gmm.psp.virtualScape.model.Sala;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface SalaRepository extends MongoRepository<Sala, String> {

    Sala findByNombre(String nombre);
    List<Sala> findByTematicas(String nombreTematica);

    @Aggregation(pipeline = {
            "{ $group: { _id: '$nombreSala', totalReservas: { $sum: 1 } } }",
            "{ $sort: { totalReservas: -1 } }",
            "{ $limit: 2 }"
    })    List<Sala> findTop2ByOrderByReservaAsc();

}
