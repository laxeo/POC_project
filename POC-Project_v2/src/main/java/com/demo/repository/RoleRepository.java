package com.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.entity.Role;


public interface RoleRepository extends JpaRepository<Role, Long> {
	
	Role findByName(String name);

}
