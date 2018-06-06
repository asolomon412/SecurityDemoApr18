package com.securityex.SecurityDemo.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.securityex.SecurityDemo.entity.Users;

// if we create an interface that extends the JpaRepository we don't need to write our own queries for the basic CRUD operations
// they will be available by default
public interface UsersRepository extends JpaRepository<Users, Integer> {
	// made this optional in case a user does not exist
	// this is a query we created to find the username
	Optional<Users> findByName(String username);
	// added this as a test to demonstrate the power of the JpaRepository!!!
	// check out the testing method in the HomeController to see how it works 
	Optional<Users> findByLastName(String lastname);

}
