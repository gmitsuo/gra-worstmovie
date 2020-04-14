package com.gra.worstmovieapp.repositories;

import com.gra.worstmovieapp.entities.Producer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProducerRepository extends CrudRepository<Producer, Long> {

    Optional<Producer> findByName(String name);

    /**
     * @return Optional list of all producers with more than 1 award winning
     * movie and its' movies ordered by ascendant year
     */
    @Query("select distinct p from Producer p " +
            "join fetch p.movies m where m.winner = true " +
            "and (select count(1) from Movie m1 join m1.producers p1 where p1.id = p.id and m1.winner = true ) > 1 " +
            "order by m.year asc")
    Optional<List<Producer>> findProducersWithMultipleAwardWinningMovies();

}