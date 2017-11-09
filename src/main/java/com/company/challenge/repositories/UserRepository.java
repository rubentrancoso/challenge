package com.company.challenge.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.company.challenge.entities.User;

@Repository
public interface UserRepository extends PagingAndSortingRepository<User, String> {
	public User findById(String UUID);
	public User findByEmail(String email); 
}
