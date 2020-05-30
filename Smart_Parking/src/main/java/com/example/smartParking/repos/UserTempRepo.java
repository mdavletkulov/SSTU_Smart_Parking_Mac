package com.example.smartParking.repos;

import com.example.smartParking.model.domain.UserTemp;
import org.springframework.data.repository.CrudRepository;

public interface UserTempRepo extends CrudRepository<UserTemp, Long> {

    UserTemp findByActivationCode(String code);
}
