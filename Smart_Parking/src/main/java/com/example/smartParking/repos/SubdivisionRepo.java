package com.example.smartParking.repos;

import com.example.smartParking.model.domain.Subdivision;
import com.example.smartParking.model.domain.TypeJobPosition;
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

    @Query(
            value = "Select * FROM subdivision LEFT JOIN" +
                    " division d on subdivision.division_id = d.id " +
                    "WHERE d.name = ?1 and subdivision.type_job_position = 'PPS'",
            nativeQuery = true)
    List<Subdivision> findByNonEmpDivision(String divisionName);

    @Query(
            value = "Select * FROM subdivision LEFT JOIN" +
                    " division d on subdivision.division_id = d.id " +
                    "WHERE d.name = ?1 and type_job_position = ?2",
            nativeQuery = true)
    List<Subdivision> findByDivisionAndTypeJob(String divisionName, String typeJob);

    @Query(
            value = "SELECT DISTINCT type_job_position " +
                    "FROM subdivision",
            nativeQuery = true)
    List<TypeJobPosition> findAllTypeJobPosition();

    Optional<Subdivision> findByName(String name);

}
