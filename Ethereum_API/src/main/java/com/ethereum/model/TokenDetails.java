package com.ethereum.model;

import java.math.BigInteger;

public class TokenDetails {
	
	private String address;
	private String name;
	private String symbol;
	private BigInteger decimal;
	
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSymbol() {
		return symbol;
	}
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	public BigInteger getDecimal() {
		return decimal;
	}
	public void setDecimal(BigInteger remoteCall) {
		this.decimal = remoteCall;
	}
	
	public TokenDetails(){}
	public TokenDetails(String address,String name,String Symbol,BigInteger decimal) {
		super();
		this.address = address;
		this.name = name;
		this.symbol = Symbol;
		this.decimal = decimal;
	}
	

}
