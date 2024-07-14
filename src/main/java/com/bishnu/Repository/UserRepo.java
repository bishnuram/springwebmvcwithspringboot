package com.bishnu.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.bishnu.Entity.AppUser;

public interface UserRepo extends JpaRepository<AppUser, Integer> {
	public AppUser findByEmail(String email);
	
	
	
}