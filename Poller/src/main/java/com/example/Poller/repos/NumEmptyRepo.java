package com.example.Poller.repos;

import com.example.Poller.domain.Automobile;
import com.example.Poller.domain.NumEmpty;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface NumEmptyRepo extends CrudRepository<NumEmpty, Long> {

    @Query(
            value = "SELECT * FROM num_empty where place_id = ?1",
            nativeQuery = true)
    Optional<NumEmpty> findByPlace(Long placeId);

}
