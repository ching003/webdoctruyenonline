package com.loginandregister.login_register.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.loginandregister.login_register.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{

}
