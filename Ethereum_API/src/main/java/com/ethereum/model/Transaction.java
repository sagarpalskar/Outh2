package com.ethereum.model;

import java.math.BigInteger;
import java.util.List;

import org.web3j.protocol.core.methods.response.Log;

public class Transaction 
{
	private String transactionHash;
	private BigInteger transactionIndex;
	private String blockHash;
	private BigInteger blockNumber;
	private String cumulativeGasUsed;
	private String gasUsed;
	private String contractAddress;
	private String status;
	private List<Log> logs;
	
	public String getTransactionHash() {
		return transactionHash;
	}
	public void setTransactionHash(String transactionHash) {
		this.transactionHash = transactionHash;
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
	public BigInteger getBlockNumber() {
		return blockNumber;
	}
	public void setBlockNumber(BigInteger blockNumber) {
		this.blockNumber = blockNumber;
	}
	public String getCumulativeGasUsed() {
		return cumulativeGasUsed;
	}
	public void setCumulativeGasUsed(String cumulativeGasUsed) {
		this.cumulativeGasUsed = cumulativeGasUsed;
	}
	public String getGasUsed() {
		return gasUsed;
	}
	public void setGasUsed(String gasUsed) {
		this.gasUsed = gasUsed;
	}
	public String getContractAddress() {
		return contractAddress;
	}
	public void setContractAddress(String contractAddress) {
		this.contractAddress = contractAddress;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public List<Log> getLogs() {
		return logs;
	}
	public void setLogs(List<Log> Logs) {
		this.logs = Logs;
	}
	
}
