package com.example.demo.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.entities.User;
import com.example.demo.exception.CustomException;
import com.example.demo.repositories.UserRepo;
import com.example.demo.service.UserService;

@Service
public class UserServiceImpl implements UserService{

	
	@Autowired
	public UserRepo userRepo;
	
	@Autowired
	public PasswordEncoder passwordEncoder;
	
	public List<User> getAllUsers(){
		List<User> users= userRepo.findAll();
		return users;
	}
	
	public User getUser(String mail)
	{
		return userRepo.findByEmail(mail).orElseThrow(()-> new CustomException("User Not Found"));
	}
	
	public void storeToken(String mail, String token)
	{
		User user = getUser(mail); 
		user.setToken(token);
		userRepo.save(user);
	}
	
	public User createUser(User user){
		
		Optional<User> user1=Optional.ofNullable(user);
		
		if((user.getEmail()==null || user.getEmail().isEmpty())
				||(user.getPassword()==null  || user.getEmail().isEmpty()))
		{
			throw new CustomException(" Username Email and Password are necessary ");
		}
		
		if(userRepo.findByEmail(user.getEmail()).isPresent())
		{
			throw new CustomException(" User Name already taken");
		}
		
		user.setUserId(UUID.randomUUID().toString());
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return userRepo.save(user);
	}
	
}
