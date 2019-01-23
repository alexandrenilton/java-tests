package br.ce.wcaquino.builders;

import com.edu.abelem.entity.User;

public class UsuarioBuilder {
	
	private User user;
	
	private UsuarioBuilder() {} 
	
	public static UsuarioBuilder oneUser() {
		UsuarioBuilder builder = new UsuarioBuilder();
		builder.user = new User();
		builder.user.setNome("Usuario 1");
		return builder;
	}
	
	public User now() {
		return user;
	}
}
