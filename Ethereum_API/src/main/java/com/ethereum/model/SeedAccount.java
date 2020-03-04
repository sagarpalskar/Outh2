package com.ethereum.model;

import java.math.BigInteger;



public class SeedAccount 
{
	private String Address;
	private BigInteger PrivateKey;
	private String SeedPhrase;
	
	public String getAddress() {
		return Address;
	}
	public void setAddress(String address) {
		Address = address;
	}
	public BigInteger getPrivateKey() {
		return PrivateKey;
	}
	public void setPrivateKey(BigInteger bigInteger) {
		PrivateKey = bigInteger;
	}
	public String getSeedPhrase() {
		return SeedPhrase;
	}
	public void setSeedPhrase(String seedPhrase) {
		SeedPhrase = seedPhrase;
	}
	
	
	

}
