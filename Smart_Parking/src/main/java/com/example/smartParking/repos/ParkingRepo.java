package com.example.smartParking.repos;

import com.example.smartParking.model.domain.Parking;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ParkingRepo extends CrudRepository<Parking, Long> {

    Optional<Parking> findByDescription(String description);

    @Query(
            value = "Select COUNT(*) FROM parking_place WHERE parking_id = ?1",
            nativeQuery = true)
    Integer countOfPlacesByParking(Long parkingId);

    @Query(
            value = "Select description from parking",
            nativeQuery = true)
    List<String> findAllDescriptions();
}
