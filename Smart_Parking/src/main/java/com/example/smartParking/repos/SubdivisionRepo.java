package com.example.smartParking.repos;

import com.example.smartParking.model.domain.Subdivision;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface SubdivisionRepo extends CrudRepository<Subdivision, Long> {

    List<Subdivision> findAll();

    @Query(
            value = "Select * FROM subdivision LEFT JOIN" +
                    " division d on subdivision.division_id = d.id " +
                    "WHERE d.name = ?1",
            nativeQuery = true)
    List<Subdivision> findByDivision(String divisionName);

    Optional<Subdivision> findByName(String name);

}
