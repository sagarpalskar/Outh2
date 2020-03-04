package com.ethereum.model;

import java.math.BigInteger;

public class TransferToken {
	

	 private String toAccount;
	 private BigInteger amount;
	 private String fromAccount;
	 private String eRC20Address;
	 private String gas;
	
	
	public String getToAccount() {
		return toAccount;
	}
	public void setToAccount(String toAccount) {
		this.toAccount = toAccount;
	}
	public BigInteger getAmount() {
		return amount;
	}
	public void setAmount(BigInteger amount) {
		this.amount = amount;
	}
	public String getFromAccount() {
		return fromAccount;
	}
	public void setFromAccount(String fromAccount) {
		this.fromAccount = fromAccount;
	}
	public String geteRC20Address() {
		return eRC20Address;
	}
	public void seteRC20Address(String eRC20Address) {
		this.eRC20Address = eRC20Address;
	}
	public String getGas() {
		return gas;
	}
	public void setGas(String gas) {
		this.gas = gas;
	}
	 
}
