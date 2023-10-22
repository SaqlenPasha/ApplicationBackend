package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entities.User;
import com.example.demo.service.UserService;

@RestController
@RequestMapping("/api")
public class HomeController {
	
	@Autowired
	private UserService userService;

	@GetMapping("/hello")
	public String hello() {
		
		return "Hello";
		
	}
        
	@GetMapping("/users")
	public ResponseEntity<List<User>> getAllUsers(String username)
	{
		List<User> user=userService.getAllUsers();
		
		return new ResponseEntity<>(user,HttpStatus.OK);
	}

}