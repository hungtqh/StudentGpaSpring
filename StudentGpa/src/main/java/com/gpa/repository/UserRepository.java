package com.gpa.repository;

import org.springframework.data.repository.CrudRepository;

import com.gpa.domain.User;

public interface UserRepository extends CrudRepository<User, Long> {
	User findByUsername(String username);
}
