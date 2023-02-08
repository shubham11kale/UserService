package com.microservices.user.service.repositories;

import com.microservices.user.service.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
    //Here you can implement custom methods to get set data to database
}
