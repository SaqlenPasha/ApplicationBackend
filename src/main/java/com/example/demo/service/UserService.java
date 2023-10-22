package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import com.example.demo.entities.User;

public interface UserService {

	public List<User> getAllUsers();
	
	public User createUser(User user);
	
	public void storeToken(String mail, String token);
	
	public User getUser(String mail);
}
