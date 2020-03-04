package com.ethereum.model;

public class RestoreAccount 
{
	private String seedPhrase;
	private String password;
	
	public String getseedPhrase() {
		return seedPhrase;
	}
	RestoreAccount(){}
	
	public String getpassword() {
		return password;
	}

	
	public RestoreAccount(String seedPhrase, String password) {
		super();
		this.seedPhrase = seedPhrase;
		this.password = password;
	}

	
	
	
}
