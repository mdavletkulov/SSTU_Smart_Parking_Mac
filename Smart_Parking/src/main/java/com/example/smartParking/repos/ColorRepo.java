package com.example.smartParking.repos;

import com.example.smartParking.model.domain.Color;
import org.springframework.data.repository.CrudRepository;

public interface ColorRepo extends CrudRepository<Color, Long> {

    Color findByName(String name);
}
