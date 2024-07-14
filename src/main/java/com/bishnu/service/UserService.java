package com.bishnu.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.bishnu.Entity.AppUser;
import com.bishnu.Repository.UserRepo;
import jakarta.transaction.Transactional;

@Service
public class UserService {
	@Autowired
	private UserRepo userRepo;
	
	@Transactional
	public AppUser validate(String email)
	{
		AppUser user = userRepo.findByEmail(email);
		return user;
	    	        
	  }	
	
	@Transactional
 	public Iterable<AppUser> searchAll()
{ 
	Iterable<AppUser> user1=  userRepo.findAll();
    return user1;
}
	
	@Transactional
	public void deleteUser(Integer id)
	{
		userRepo.deleteById(id);
	}
	
	
	@Transactional
	public void registerNewUser(AppUser user) {
		
		userRepo.save(user);
		}
	
	
	
	public Optional<AppUser> findUserById(Integer Id)
	{
		Optional<AppUser> userToEdit= userRepo.findById(Id);
		return userToEdit;
	}
	

}
