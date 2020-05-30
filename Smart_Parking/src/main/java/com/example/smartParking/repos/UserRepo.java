package com.example.smartParking.repos;


import com.example.smartParking.model.domain.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepo extends CrudRepository<User, Long> {

    User findByUsername(String username);

    User findByActivationCode(String code);
}
