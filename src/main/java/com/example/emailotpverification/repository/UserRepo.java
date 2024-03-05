package com.example.emailotpverification.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.emailotpverification.model.User;

@Repository
public interface UserRepo extends JpaRepository<User, Long>{
	
	public User findByEmail(String email);

}
