package com.sparrow.test;

import com.sparrow.test.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {

    @Query("select u from User u where u.email=:email")
    Optional<User>findUserByEmail(@Param("email")String email);


}
