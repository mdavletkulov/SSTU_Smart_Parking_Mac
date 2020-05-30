package com.example.Poller.repos;

import com.example.Poller.domain.Automobile;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface AutomobileRepo extends CrudRepository<Automobile, Long> {

    List<Automobile> findAll();

    @Query(
            value = "SELECT * FROM automobile LEFT JOIN color c on automobile.color_id = c.id  where c.name = ?1",
            nativeQuery = true)
    List<Automobile> findAllByColor(String colorName);

    @Query(
            value = "SELECT * FROM automobile LEFT JOIN person p on automobile.person_id = p.id  where p.id = ?1",
            nativeQuery = true)
    List<Automobile> findByPersonId(Long personId);

    Optional<Automobile> findByNumber(String number);

    @Query(
            value = "SELECT * FROM automobile where number = ?1",
            nativeQuery = true)
    List<Automobile> findAutosByNumber(String number);
}
