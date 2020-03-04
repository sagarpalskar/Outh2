package com.ethereum.model;

import java.math.BigInteger;

public class Erc20TokenBalance 
{
	private String eRC20Address;
	private BigInteger balance;
	
	public String geteRC20Address() {
		return eRC20Address;
	}
	public void seteRC20Address(String eRC20Address) {
		this.eRC20Address = eRC20Address;
	}
	public BigInteger getBalance() {
		return balance;
	}
	public void setBalance(BigInteger balance) {
		this.balance = balance;
	}
	
//	public Erc20TokenBalance(String eRC20Address, String balance) {
//		super();
//		this.eRC20Address = eRC20Address;
//		this.balance = balance;
//	}
	
	
	
}
