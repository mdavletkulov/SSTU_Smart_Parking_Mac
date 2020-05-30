package com.example.smartParking.repos;

import com.example.smartParking.model.domain.Place;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface PlaceRepo extends CrudRepository<Place, Long> {

    @Query(
            value = "Select * FROM parking_place WHERE parking_id = ?1",
            nativeQuery = true)
    List<Place> findByParkingId(Long parkingId);

    @Query(
            value = "Select * FROM parking_place WHERE place_number = ?1 and parking_id = ?2",
            nativeQuery = true)
    List<Place> findByParkingAndNum(Integer placeNum, Long parkingId);

    @Query(
            value = "Select * FROM parking_place WHERE place_number = ?1 and parking_id = ?2",
            nativeQuery = true)
    Optional<Place> findPlace(Integer placeNum, Long parkingId);

    @Query(
            value = "Select * FROM parking_place where parking_id = ?1 and (SELECT COUNT(*) FROM parking_event " +
                    "WHERE end_time IS NULL and place_id = parking_place.id) = 0 ORDER BY place_number",
            nativeQuery = true)
    List<Place> findFreePlacesByParking(Long parkingId);

}
