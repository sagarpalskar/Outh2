package com.ethereum.api;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.web3j.contracts.eip20.generated.ERC20;
import org.web3j.contracts.eip20.generated.ERC20.TransferEventResponse;
import org.web3j.crypto.Bip39Wallet;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.RawTransaction;
import org.web3j.crypto.TransactionEncoder;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.EthBlock;
import org.web3j.protocol.core.methods.response.EthBlock.TransactionObject;
import org.web3j.protocol.core.methods.response.EthBlock.TransactionResult;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.protocol.core.methods.response.EthGetTransactionCount;
import org.web3j.protocol.core.methods.response.EthGetTransactionReceipt;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.gas.DefaultGasProvider;
import org.web3j.utils.Convert;
import org.web3j.utils.Convert.Unit;
import org.web3j.utils.Numeric;

import com.ethereum.model.AcccountNo;
import com.ethereum.model.ApiResponce;
import com.ethereum.model.Erc20TokenBalance;
import com.ethereum.model.RestoreAccount;
import com.ethereum.model.SeedAccount;
import com.ethereum.model.TokenBalance;
import com.ethereum.model.TokenDetails;
import com.ethereum.model.Transaction;
import com.ethereum.model.TransactionLogs;
import com.ethereum.model.TransferEther;
import com.ethereum.model.TransferToken;

import io.reactivex.Flowable;


@RestController
@ComponentScan(basePackages ="com.ethereum.model")
@RequestMapping("/api/v1")
public class EthereumController 
{
	@Autowired
	Service service;
	
	String pk = "8d335c0fac4889c0e36d814856ed882a6a436b8972219a612e4aec077f5c189b";
	
	@GetMapping("/createAccountwithSeed/{Password}")
	public ApiResponce createAccountwithSeed(@PathVariable("Password") String Password) throws CipherException, IOException
	{
		File walletDirectory = new File("/home/sagarp");
		Bip39Wallet walletname=WalletUtils.generateBip39Wallet(Password, walletDirectory);
		Credentials credentials = WalletUtils.loadBip39Credentials(Password, walletname.getMnemonic());
		
		SeedAccount seedaccount = new SeedAccount();
		seedaccount.setAddress(credentials.getAddress());
		seedaccount.setPrivateKey(credentials.getEcKeyPair().getPrivateKey());
		seedaccount.setSeedPhrase(walletname.getMnemonic());
		
		ApiResponce apiresponce = new ApiResponce(0, "successful", seedaccount);
		return apiresponce;
	}
	
	@PostMapping(path = "/restoreAccountbySeed")
	public ApiResponce restoreAccountbySeed(@RequestBody RestoreAccount restore) 
	{
		System.out.println("password "+ restore.getpassword()+"seed "+ restore.getseedPhrase());
		Credentials credentials = WalletUtils.loadBip39Credentials( restore.getpassword(), restore.getseedPhrase());
		
		SeedAccount seedaccount = new SeedAccount();
		seedaccount.setAddress(credentials.getAddress());
		seedaccount.setPrivateKey(credentials.getEcKeyPair().getPrivateKey());
		seedaccount.setSeedPhrase(restore.getseedPhrase());
		ApiResponce apiresponce = new ApiResponce(0, "successful", seedaccount);
		return apiresponce;
	}
	
	@GetMapping(path = "/getAllERC20Tokens")
	public ApiResponce getAllERC20Tokens() throws IOException 
	{
		ApiResponce apiresponce = new ApiResponce(0, "successful", service.getAllErcTokens());
		return apiresponce;
	}
	
	@PostMapping(path = "/getAccountBalance")
	public ApiResponce getAccountBalance(@RequestBody AcccountNo accountNo) throws InterruptedException, ExecutionException  
	{
		Web3j web3j =service.Coonection();
		EthGetBalance ethGetBalance = web3j.ethGetBalance(accountNo.getaccount(), DefaultBlockParameterName.LATEST).sendAsync().get();

		BigInteger wei = ethGetBalance.getBalance();
		
		String balance = Convert.fromWei(wei.toString(), Unit.ETHER).toString();
	
		ApiResponce apiresponce = new ApiResponce(0, "successful", balance);
		return apiresponce;
	}
	
	@PostMapping(path = "/GetERC20TokenBalance")
	public ApiResponce	getERC20TokenBalance(@RequestBody TokenBalance tokenBalance) throws Exception
	{
		Web3j web3j =service.Coonection();
		
	
		Credentials credentials = Credentials.create(pk);
		ERC20 javaToken = ERC20.load(tokenBalance.geteRC20Address(), web3j, credentials, new DefaultGasProvider());
				
		ApiResponce apiresponce = new ApiResponce(0, "successful", javaToken.balanceOf(tokenBalance.getFromAccount()).send());
		return apiresponce;
	}
	
	@PostMapping(path ="GetAllERC20TokenBalance")
	public ApiResponce getAllERC20TokenBalance(@RequestBody List<TokenBalance> tokenBalance) throws Exception 
	{
		Web3j web3j =service.Coonection();
		//String pk = "8d335c0fac4889c0e36d814856ed882a6a436b8972219a612e4aec077f5c189b";
		Credentials credentials = Credentials.create(pk);
		
		Erc20TokenBalance erc20Tokenbalance[]= new Erc20TokenBalance[tokenBalance.size()];
		List<Erc20TokenBalance> Erc20TokenBalanceList=new ArrayList<Erc20TokenBalance>();

		for(int i =0;i <= tokenBalance.size()-1;i ++ ) 
		{
		ERC20 javaToken = ERC20.load(tokenBalance.get(i).geteRC20Address(), web3j, credentials, new DefaultGasProvider());
		erc20Tokenbalance[i]=new Erc20TokenBalance();
	    erc20Tokenbalance[i].setBalance(javaToken.balanceOf(tokenBalance.get(i).getFromAccount()).send());
	    erc20Tokenbalance[i].seteRC20Address(tokenBalance.get(i).geteRC20Address());
	    Erc20TokenBalanceList.add(erc20Tokenbalance[i]);
		}
		ApiResponce apiresponce = new ApiResponce(0, "successful", Erc20TokenBalanceList);
		return apiresponce;
	}
	
	@PostMapping(path ="GetERC20TokenDetails")
	public ApiResponce getERC20TokenDetails(@RequestBody TokenBalance tokenBalance) throws Exception
	{
		Web3j web3j =service.Coonection();
		//String pk = "8d335c0fac4889c0e36d814856ed882a6a436b8972219a612e4aec077f5c189b";
		Credentials credentials = Credentials.create(pk);
		ERC20 javaToken = ERC20.load(tokenBalance.geteRC20Address(), web3j, credentials, new DefaultGasProvider());
		
		TokenDetails tokenDetails= new TokenDetails();
		tokenDetails.setAddress(javaToken.getContractAddress());
		tokenDetails.setName(javaToken.name().send());
		tokenDetails.setSymbol(javaToken.symbol().send());
		tokenDetails.setDecimal(javaToken.decimals().send());
		
		ApiResponce apiresponce = new ApiResponce(0, "successful", tokenDetails);
		return apiresponce;
	}
	
	
	@PostMapping(path ="sendTransaction")
	public ApiResponce sendTransaction(@RequestBody TransferEther transferEther) throws IOException 
	{
		Web3j web3j =service.Coonection();
		Credentials credentials = Credentials.create(transferEther.getFromPrivateKey());
		
		EthGetTransactionCount ethGetTransactionCount = web3j.ethGetTransactionCount(credentials.getAddress(), 
																	DefaultBlockParameterName.LATEST).send();
		
		BigInteger nonce = ethGetTransactionCount.getTransactionCount();
		BigInteger value = Convert.toWei(transferEther.getAmount(), Unit.ETHER).toBigInteger();
		
		RawTransaction rawTransaction = RawTransaction.createEtherTransaction(nonce, 
														new DefaultGasProvider().getGasPrice(), 
														new DefaultGasProvider().getGasLimit(),
														transferEther.getToAccount(), value);
		
		byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, credentials);
		String hexValue = Numeric.toHexString(signedMessage);

		EthSendTransaction ethSendTransaction = web3j.ethSendRawTransaction(hexValue).send();
		String transactionHash = ethSendTransaction.getTransactionHash();
		
		EthGetTransactionReceipt ethGetTransactionReceiptResp = web3j.ethGetTransactionReceipt(transactionHash).send();
		Optional<TransactionReceipt> transactionReceipt = ethGetTransactionReceiptResp.getTransactionReceipt();
		
		Transaction transaction=new Transaction();
		transaction.setTransactionHash(ethSendTransaction.getTransactionHash());
		transaction.setTransactionIndex(transactionReceipt.get().getTransactionIndex());
		transaction.setBlockHash(transactionReceipt.get().getBlockHash());
		transaction.setBlockNumber(transactionReceipt.get().getBlockNumber());
		transaction.setCumulativeGasUsed(transactionReceipt.get().getCumulativeGasUsedRaw());
		transaction.setGasUsed(transactionReceipt.get().getGasUsedRaw());
		transaction.setContractAddress(transactionReceipt.get().getContractAddress());
		transaction.setStatus(transactionReceipt.get().getStatus());
		transaction.setLogs(transactionReceipt.get().getLogs());
		
		ApiResponce apiresponce = new ApiResponce(0, "successful", transaction);
		return apiresponce;
	}
	
	@PostMapping(path ="sendERC20TokenAmount")
	public ApiResponce sendERC20TokenAmount(@RequestBody TransferToken transferToken) throws Exception
	{
		Web3j web3j =service.Coonection();
		Credentials credentials = Credentials.create(pk);
		
		ERC20 javaToken = ERC20.load(transferToken.geteRC20Address(), web3j, credentials, new DefaultGasProvider());
	    TransactionReceipt receipt = javaToken.transfer(transferToken.getToAccount(), transferToken.getAmount()).send();
		
		Transaction transaction=new Transaction();
		transaction.setTransactionHash(receipt.getTransactionHash());
		transaction.setTransactionIndex(receipt.getTransactionIndex());
		transaction.setBlockHash(receipt.getBlockHash());
		transaction.setBlockNumber(receipt.getBlockNumber());
		transaction.setCumulativeGasUsed(receipt.getCumulativeGasUsedRaw());
		transaction.setGasUsed(receipt.getGasUsedRaw());
		transaction.setContractAddress(receipt.getContractAddress());
		transaction.setStatus(receipt.getStatus());
		transaction.setLogs(receipt.getLogs());
	    
	    ApiResponce apiresponce = new ApiResponce(0, "successful", transaction);
		return apiresponce;
	}
	
	@PostMapping(path ="transactionLogsToken")
	public ApiResponce transactionLogsToken(@RequestBody AcccountNo acccountNo) throws Exception
	{		//transaction.setTransactionIndex(receipt.getTransactionIndex());

		Web3j web3j =service.Coonection();
		Credentials credentials = Credentials.create(pk);
		String TokenAddress= "		transaction.setTransactionIndex(receipt.getTransactionIndex());\n" + 
				"0xA480Fbde68A62Df15D433C50210975FaFF6D82FD"; 
		
		ERC20 javaToken = ERC20.load(TokenAddress, web3j, credentials, new DefaultGasProvider());
		
		Flowable<TransferEventResponse>transevent =javaToken.transferEventFlowable(DefaultBlockParameterName.EARLIEST,
				                                                                  DefaultBlockParameterName.LATEST);
	
		List<TransactionLogs> TransactionLogsList=new ArrayList<TransactionLogs>();
		transevent.forEach((temp) -> {
			String from = temp._from;
			String accounts =acccountNo.getaccount();
			if (accounts.toLowerCase().equals(from.toLowerCase()) || accounts.toLowerCase().equals((temp._to).toLowerCase()) )
			{
				  TransactionLogs trrnsactionlog= new TransactionLogs();
				  trrnsactionlog.setBlockHash(temp.log.getBlockHash());
				  trrnsactionlog.setTransactionhash(temp.log.getTransactionHash());
				  trrnsactionlog.setTransactionIndex(temp.log.getTransactionIndex());
				  trrnsactionlog.setBlockNumber(temp.log.getBlockNumberRaw());
				  trrnsactionlog.setFrom(temp._from); trrnsactionlog.setTo(temp._to);
				  trrnsactionlog.setValue(temp._value);
				  TransactionLogsList.add(trrnsactionlog); 
			}
		});
		ApiResponce apiresponce = new ApiResponce(0, "successful", TransactionLogsList);
		return apiresponce;
	}
	
/*	@GetMapping("/test")
	public List<Transaction> test() throws InterruptedException, ExecutionException, IOException {
		String address = "0xeedc155ae507fb0a9becae6315286183a23229b0";
		List<Transaction> transactions = new ArrayList<Transaction>();
		Web3j web3j =service.Coonection();
		System.out.println("test Running.......");
		BigInteger block = web3j.ethBlockNumber().send().getBlockNumber();
		System.out.println("blockno:" + block.intValue());
		int block_no = block.intValue();

		
	Flowable<org.web3j.protocol.core.methods.response.Transaction> replyBlocks	= web3j.replayPastTransactionsFlowable(DefaultBlockParameterName.EARLIEST, DefaultBlockParameterName.LATEST);
	replyBlocks.forEach(temp->{
		System.out.println("into replyBlocks");
		System.out.println(temp.getBlockNumber());
			transactions.addAll((Collection<? extends Transaction>) temp); //It's an collection object,and value is not adding into it 
	})	;
	
		return transactions;
	}*/
	
	@PostMapping(path ="transactionLogs")
	public ApiResponce transactionLogs(@RequestBody AcccountNo acccountNo) throws Exception
	{
		String address = "0xeedc155ae507fb0a9becae6315286183a23229b0";
		Web3j web3j =service.Coonection();
		BigInteger  blockNo=web3j.ethBlockNumber().send().getBlockNumber();
		int block=blockNo.intValue();
		List<TransactionResult> tansactions = new ArrayList<EthBlock.TransactionResult>();
	
		while (block >= 0) 
		{
		List<EthBlock.TransactionResult> txs = web3j.ethGetBlockByNumber(DefaultBlockParameter.valueOf(blockNo), true)
														.send().getBlock().getTransactions();

			txs.forEach(tx -> {
				EthBlock.TransactionObject transaction =  (TransactionObject) tx.get();
			
				if ((transaction.getFrom().toLowerCase()).equals(acccountNo.getaccount().toLowerCase())) 
				{
					tansactions.add(tx);
				}
			});
			block--;
		}
		ApiResponce apiresponce = new ApiResponce(0, "successful", tansactions);
		return apiresponce;
	}
	
	@GetMapping("/test")
	public List<TransactionLogs> test() throws InterruptedException, ExecutionException, IOException {
		String address = "0xeedc155ae507fb0a9becae6315286183a23229b0";
		//List<Transaction> transactions = new ArrayList<Transaction>();
		List<TransactionLogs> transactions=new ArrayList<TransactionLogs>();
		Web3j web3j = service.Coonection();
		System.out.println("test Running.......");
		BigInteger block = web3j.ethBlockNumber().send().getBlockNumber();
		System.out.println("blockno:" + block.intValue());
		int block_no = block.intValue();
		
	
		
		Flowable<org.web3j.protocol.core.methods.response.Transaction> replyBlocks = web3j
				.replayPastTransactionsFlowable(DefaultBlockParameterName.EARLIEST, DefaultBlockParameterName.LATEST)
				.serialize();
		replyBlocks.forEach(temp -> {
			
			
			System.out.println("into replyBlocks");
			System.out.println(temp.getBlockNumber());
			System.out.println(temp.getBlockHash());
			System.out.println(temp.getTo());
			System.out.println(temp.getFrom());
		//	transaction.setTransactionIndex(receipt.getTransactionIndex());

			
			TransactionLogs logs=new TransactionLogs();
			logs.setBlockHash(temp.getBlockHash());
			logs.setBlockNumber(temp.getBlockNumber().toString());
			logs.setFrom(temp.getFrom());
			logs.setTo(temp.getTo());
			logs.setTransactionIndex(temp.getTransactionIndex());
			logs.setNonce(temp.getNonce().toString());
			logs.setGas(temp.getGas().toString());
			logs.setGasPrice(temp.getGasPrice().toString());
			
			System.out.println("Logs.toString "+logs.toString());

			transactions.add(logs); // It's an collection object,and value is not adding into it
			//System.out.println("Transa Object"+transactions.get(1).getBlockNumber());

		});
		//System.out.println("Transa Object"+transactions.get(1).getBlockNumber());
		//System.out.println(transactions.get(2).toString());
		return transactions;
	}
}
