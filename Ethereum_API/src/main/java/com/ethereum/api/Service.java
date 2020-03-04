package com.ethereum.api;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;
import org.web3j.contracts.eip20.generated.ERC20;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.Hash;
import org.web3j.crypto.Keys;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthGetTransactionCount;
import org.web3j.protocol.http.HttpService;
import org.web3j.rlp.RlpEncoder;
import org.web3j.rlp.RlpList;
import org.web3j.rlp.RlpString;
import org.web3j.tx.gas.DefaultGasProvider;
import org.web3j.utils.Numeric;

import com.ethereum.model.TokenDetails;

@Component
public class Service {
	
	public Web3j Coonection()
	{
		Web3j web3j = Web3j.build(new HttpService("HTTP://172.31.2.3:7545" ));
		return web3j;
	}

	public List<TokenDetails> getAllErcTokens() throws IOException
	{
		Web3j web3j = Coonection();
		String pk = "8d335c0fac4889c0e36d814856ed882a6a436b8972219a612e4aec077f5c189b";
		Credentials credentials = Credentials.create(pk);

		List<String> account = web3j.ethAccounts().send().getAccounts();
		TokenDetails tokenDetails[] = new TokenDetails[account.size()];
		List<TokenDetails> TokenDetails = new ArrayList<TokenDetails>();
		for (int i = 0; i <= account.size()- 1; i++)
		{
			EthGetTransactionCount ethGetTransactionCount = web3j.ethGetTransactionCount(account.get(i), 
																DefaultBlockParameterName.LATEST).send();
			BigInteger count = ethGetTransactionCount.getTransactionCount();
			int nonce = count.intValue();
		//	System.out.println("nonce " + nonce);
			while (nonce >= 0) {
				byte[] addressAsBytes = Numeric.hexStringToByteArray(account.get(i));

				byte[] calculatedAddressAsBytes = Hash.sha3(RlpEncoder.encode(new RlpList(RlpString.
															create(addressAsBytes), RlpString.create((nonce)))));
				
				calculatedAddressAsBytes = Arrays.copyOfRange(calculatedAddressAsBytes, 12,
															calculatedAddressAsBytes.length);
				
				String calculatedAddress = Numeric.toHexString(calculatedAddressAsBytes);// To change address in
				String token_address = Keys.toChecksumAddress(calculatedAddress);
				ERC20 javaToken = ERC20.load(token_address, web3j, credentials, new DefaultGasProvider()); 

				if (javaToken != null) {
					try {
						String symbol = javaToken.symbol().send();
						//System.out.println(account.get(i) + "Token Created " + javaToken.getContractAddress()+" Symbol "+ symbol);
						tokenDetails[i] = new TokenDetails();
						tokenDetails[i].setAddress(javaToken.getContractAddress());
						//System.out.println(" " + i + " Address of token  " + tokenDetails[i].getAddress());
						tokenDetails[i].setName(javaToken.name().send());
						//System.out.println(" " + i + " Name of token " + tokenDetails[i].getName());
						tokenDetails[i].setSymbol(symbol);
						//System.out.println(" " + i + " symbol of token " + tokenDetails[i].getSymbol());
						tokenDetails[i].setDecimal(javaToken.decimals().send());
						//System.out.println(" " + i + " Decimal of token " + tokenDetails[i].getDecimal().toString());
						TokenDetails.add(tokenDetails[i]);
					} catch (Exception e) {
						// System.out.println(account.get(i)+"Simple Contract Created"+
						// javaToken.getContractAddress());
					}

				} else
					System.out.println("contract not loaded");
				nonce = nonce - 1;
			}

		}
		return TokenDetails;
	}
}
