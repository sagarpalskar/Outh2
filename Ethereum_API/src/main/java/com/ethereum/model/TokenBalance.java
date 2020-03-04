package com.ethereum.model;

public class TokenBalance {
	private String fromAccount;
	private String eRC20Address;
	
	public String geteRC20Address() {
		return eRC20Address;
	}
	public String getFromAccount() {
		return fromAccount;
	}
	public void setFromAccount(String fromAccount) {
		this.fromAccount = fromAccount;
	}
	public void seteRC20Address(String eRC20Address) {
		this.eRC20Address = eRC20Address;
	}
	
	public TokenBalance(){}
	
	public TokenBalance(String fromAccount, String eRC20Address) {
		super();
		this.fromAccount = fromAccount;
		this.eRC20Address = eRC20Address;
	}
	

}
