package com.example.smartParking.repos;

import com.example.smartParking.model.domain.JobPosition;
import com.example.smartParking.model.domain.Subdivision;
import com.example.smartParking.model.domain.TypeJobPosition;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface JobPositionRepo extends CrudRepository<JobPosition, Long> {

    @Query(
            value = "SELECT DISTINCT type_job_position " +
                    "FROM job_position",
            nativeQuery = true)
    List<TypeJobPosition> findAllPositionNames();

    Optional<JobPosition> findByNamePosition(String namePosition);
}
