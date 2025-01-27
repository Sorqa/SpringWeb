package com.sku.web;

public class User {
	private String username;
	private String password;
	
	public User() {}
	public User(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}

	@Override
	public String toString() {
		String str = String.format("%s\t %s \n", username, password);
		return str;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
}
