package com.tsoft.usersapp.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.github.javafaker.Faker;
import com.tsoft.usersapp.model.User;

@Service
public class UserService {

	@Autowired
	private Faker faker;
	
	private List<User> users = new ArrayList<User>();
	
	@PostConstruct
	public void init() {
		for(int i=0; i<100; i++) {
			users.add(new User(faker.funnyName().name(), faker.name().username(), faker.dragonBall().character()));
		}
	}

	public List<User> getUsers() {
		return users;
	}
	
	
	public User getUserByUsername(String username) {
		User usuario = null;
		for(User u : users) {
			if(u.getUsername().equals(username)) {
				usuario = u;
			}
		}
		if(usuario == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado");
		return usuario; 
	}
	
	public User createUser(User user) {
		if(users.stream().anyMatch(u-> u.getUsername().equals(user.getUsername()))) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, String.format("El usuario %s ya existe", user.getUsername()));
		}
		users.add(user);
		return user;
	}
}
