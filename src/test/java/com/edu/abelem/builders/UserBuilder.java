package com.edu.abelem.builders;

import com.edu.abelem.entity.User;

public class UserBuilder {
	
	private User user;
	
	private UserBuilder() {} 
	
	public static UserBuilder oneUser() {
		UserBuilder builder = new UserBuilder();
		builder.user = new User();
		builder.user.setNome("User 1");
		return builder;
	}
	
	public UserBuilder withName(String name) {
		user.setNome(name);
		return this;
	}
	
	public User now() {
		return user;
	}
}
