package com.ethereum.model;

import java.math.BigInteger;

public class TransactionLogs {
	private String transactionhash;
	private BigInteger transactionIndex;
	private String blockHash;
	private String blockNumber;
	private String from;
	private String to;
	private String gas;
	private String gasPrice;
	private BigInteger value;
	private String nonce;
	public String getTransactionhash() {
		return transactionhash;
	}
	public void setTransactionhash(String transactionhash) {
		this.transactionhash = transactionhash;
	}
	public BigInteger getTransactionIndex() {
		return transactionIndex;
	}
	public void setTransactionIndex(BigInteger transactionIndex) {
		this.transactionIndex = transactionIndex;
	}
	public String getBlockHash() {
		return blockHash;
	}
	public void setBlockHash(String blockHash) {
		this.blockHash = blockHash;
	}
	public String getBlockNumber() {
		return blockNumber;
	}
	public void setBlockNumber(String blockNumber) {
		this.blockNumber = blockNumber;
	}
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public String getGas() {
		return gas;
	}
	public void setGas(String gas) {
		this.gas = gas;
	}
	public String getGasPrice() {
		return gasPrice;
	}
	public void setGasPrice(String gasPrice) {
		this.gasPrice = gasPrice;
	}
	public BigInteger getValue() {
		return value;
	}
	public void setValue(BigInteger _value) {
		this.value = _value;
	}
	public String getNonce() {
		return nonce;
	}
	public void setNonce(String nonce) {
		this.nonce = nonce;
	}
	
	
	
}
