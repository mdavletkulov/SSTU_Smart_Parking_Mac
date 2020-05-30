package com.example.smartParking.repos;


import com.example.smartParking.model.domain.Person;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface PersonRepo extends CrudRepository<Person, Long> {

    public List<Person> findAll();

    public Optional<Person> findByPassNum(Integer passNum);
}
