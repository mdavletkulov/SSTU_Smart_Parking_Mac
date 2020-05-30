package com.example.smartParking.repos;

import com.example.smartParking.model.domain.Division;
import com.example.smartParking.model.domain.Subdivision;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface DivisionRepo extends CrudRepository<Division, Long> {

    List<Division> findAll();

    @Query(
            value = "Select * FROM division LEFT JOIN subdivision s on division.id = s.division_id" +
                    " LEFT JOIN person p on s.id = p.subdivision " +
                    "LEFT JOIN job_position jp on p.job_position = jp.id " +
                    "where type_job_position = ?1",
            nativeQuery = true)
    List<Division> findByTypeJob(String typeJobPosition);

    Optional<Division> findByName(String name);
}
