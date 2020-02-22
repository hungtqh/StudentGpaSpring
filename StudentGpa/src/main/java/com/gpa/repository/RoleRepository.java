package com.gpa.repository;

import org.springframework.data.repository.CrudRepository;

import com.gpa.domain.security.Role;

public interface RoleRepository extends CrudRepository<Role, Long> {
	Role findByName(String name);
}
