package com.aitsl.borrower.borrowerservice;

public class UserDetail {

	private long id;
	private String firstName;
	private String lastName;
	private String email;
	private String port;

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
