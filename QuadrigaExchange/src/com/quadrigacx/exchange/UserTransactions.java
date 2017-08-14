package com.quadrigacx.exchange;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import com.quadrigacx.api.QuadrigaMethods;
import com.quadrigacx.api.returnJson.ResponseWrapper;
import com.quadrigacx.api.returnJson.helpers.GetCoinType;
import com.quadrigacx.api.returnJson.helpers.Trade;
import com.quadrigacx.exchange.bot.Bot;
import com.quadrigacx.exchange.threads.CommonData;

import helpers.KeyValuePair;
import helpers.RateLimiter;
import helpers.econ.buySell.TransactionList;
import helpers.econ.currency.Coin;
import helpers.econ.currency.CoinType;

public class UserTransactions extends QuadrigaCall{
	
	private String book = "";
	private CoinType minor;
	private CoinType major;
	private Coin minorBalance;
	private Coin majorBalance;
	private Coin majorRoundBalance;
	private int transactionsSellByIdLast = 0;
	private int transactionsBuyByIdLast = 0;
	private String lastSellIdSearched = "";
	private String lastBuyIdSearched = "";
	private String lastTradeId = "";
	private boolean minorBalanceZeroLast = true;
	private String lastMajorBalance = "";
	private String lastMinorBalance = "";
	private boolean newSell = false;
	private boolean newBuy = false;
	
	
	public UserTransactions(){
		this.r = new ResponseWrapper(); 
	}
	
	public UserTransactions(RateLimiter rl, String book){
		super(rl);
		qm = QuadrigaMethods.UserTransactions;
		this.book = book;
		minor = GetCoinType.getMinor(book);
		major = GetCoinType.getMajor(book);
		minorBalance = new Coin(minor);
		majorBalance = new Coin(major);
		majorRoundBalance = new Coin(major);
		kv.clear();
		kv.add(new KeyValuePair("book", book));
		refreshData();
	}
	
	public boolean isNewSell(){
		boolean r = false;
		if (newSell){
			r = newSell;
			newSell = false;
		}
		return r;
	}
	
	public boolean isNewBuy(){
		boolean r = false;
		if (newBuy){
			r = newBuy;
			newBuy = false;
		}
		return r;
	}
	
	public boolean didMajorBalanceChange(){
		
		if (lastMajorBalance.equals(majorBalance.getValue().toString())){
			lastMajorBalance = majorBalance.getValue().toString();
			return false;
		}
		else{
			lastMajorBalance = majorBalance.getValue().toString();
			return true;
		}
	}
	
	public boolean didMinorBalanceChange(){
		
		if (lastMinorBalance.equals(minorBalance.getValue().toString())){
			lastMinorBalance = minorBalance.getValue().toString();
			return false;
		}
		else{
			lastMinorBalance = minorBalance.getValue().toString();
			return true;
		}
	}
	
	public boolean resetRoundSells(){
		
		BigDecimal zero = new BigDecimal("0").setScale(0, RoundingMode.DOWN);
		boolean minorBalanceZero;
		boolean rVal = false;
		
		if (minorBalance.getValue().compareTo(zero) > 0) minorBalanceZero = false;
		else minorBalanceZero = true;
		
		if (minorBalanceZero && !minorBalanceZeroLast) rVal = true;
		
		minorBalanceZeroLast = minorBalanceZero;	
		
		return rVal;
	}

	public List<Trade> getTrades(){
		return r.getUserTransactionsResponse().getTrades();
	}

	public String getBook() {
		return book;
	}

	public void setBook(String book) {
		this.book = book;
		kv.clear();
		kv.add(new KeyValuePair("book", book));
	}
	
	public boolean checkAndAddNewSells(String id, TransactionList tl){
		
		CoinType major = GetCoinType.getMajor(book);
		int newSells = numberOfNewSellTransById(id);
		
		if (newSells > 0){
			
			List<Integer> indexList = getPositionsOfId(id, newSells);
			
			for (int i = 0; i < indexList.size(); i++){
				
				int x = indexList.get(i);
				BigDecimal amount = new BigDecimal(r.getUserTransactionsResponse().getTrades().get(x).getMajor())
						.setScale(major.getDecimalPlaces(), RoundingMode.DOWN)
						.multiply(new BigDecimal("-1").setScale(0, RoundingMode.DOWN));
				
				tl.addTransaction(amount.toString(), r.getUserTransactionsResponse().getTrades().get(x).getRate());
			}
			
			return true;
			
		}
		
		return false;
		
	}
	
	public boolean checkAndAddNewBuys(String id, TransactionList tl){
		
		CoinType major = GetCoinType.getMajor(book);
		int newBuys = numberOfNewBuyTransById(id);
		
		if (newBuys > 0){
			
			List<Integer> indexList = getPositionsOfId(id, newBuys);
			
			for (int i = 0; i < indexList.size(); i++){
				
				int x = indexList.get(i);
				BigDecimal amount = new BigDecimal(r.getUserTransactionsResponse().getTrades().get(x).getMajor())
						.setScale(major.getDecimalPlaces(), RoundingMode.DOWN);
				
				tl.addTransaction(amount.toString(), r.getUserTransactionsResponse().getTrades().get(x).getRate());
			}
			
			return true;
			
		}
		
		return false;
		
	}
	
	public boolean initializeFind(){
		
		if (r.getUserTransactionsResponse().getTrades().size() > 0){
			lastTradeId = r.getUserTransactionsResponse().getTrades().get(0).getId();
			return true;
		}
		else return false;
	}
	
	public boolean findNewTransactionsJuggle(TransactionList totalSells, TransactionList roundSells, TransactionList buys, String amountToUseMax,
			CommonData cd){
		
		String firstTradeIdFound = "";
		
		try{
		
			if (r.getUserTransactionsResponse().getTrades().size() > 0){
			
				int i = 0;
				BigDecimal zero = new BigDecimal("0").setScale(0, RoundingMode.DOWN);
				boolean found = false;
			
				while (!r.getUserTransactionsResponse().getTrades().get(i).getId().equals(lastTradeId)){
			
					if (i < r.getUserTransactionsResponse().getTrades().size()){
				
						BigDecimal transMajor = new BigDecimal(r.getUserTransactionsResponse().getTrades().get(i).getMajor())
								.setScale(major.getDecimalPlaces(), RoundingMode.DOWN);
						BigDecimal transMinor = new BigDecimal(r.getUserTransactionsResponse().getTrades().get(i).getMinor())
								.setScale(minor.getDecimalPlaces(), RoundingMode.DOWN);
					
						if (!found){
							firstTradeIdFound = r.getUserTransactionsResponse().getTrades().get(i).getId();
							found = true;
						}
						
						if (transMajor.compareTo(zero) < 0){					//Found a sell transaction
						
							newSell = true;
							
							transMajor = transMajor.multiply(new BigDecimal("-1").setScale(0, RoundingMode.DOWN));
							totalSells.addTransaction(transMajor.toString(), r.getUserTransactionsResponse().getTrades().get(i).getRate());
							roundSells.addTransaction(transMajor.toString(), r.getUserTransactionsResponse().getTrades().get(i).getRate());
						
							while (!cd.getBalances().refreshData()){}
							
							majorBalance.setValue(new BigDecimal(cd.getBalances().getMajor(cd.getRuntimeData().getBook()).getBalance())
									.setScale(cd.getRuntimeData().getMajor().getDecimalPlaces(), RoundingMode.DOWN));
							majorRoundBalance.setValue(majorRoundBalance.getValue().subtract(transMajor));
							minorBalance.setValue(minorBalance.getValue().add(transMinor));
							
							//Check if sell max to use limit is raised
							if (cd.getBotParams().isRaisedSellLimit()){
								
								BigDecimal atu = new BigDecimal(cd.getBotParams().getAmountToUse()).setScale(major.getDecimalPlaces(), RoundingMode.DOWN);
								BigDecimal atuo = new BigDecimal(cd.getBotParams().getAmountToUseOriginal()).setScale(major.getDecimalPlaces()
										, RoundingMode.DOWN);
								
								atu = atu.subtract(transMajor);
								
								if (atu.compareTo(atuo) > 0){
									cd.getBotParams().setAmountToUse(atu.toString());
								}
								else if (atu.compareTo(atuo) <= 0){
									cd.getBotParams().setAmountToUse(atuo.toString());
									cd.getBotParams().setRaisedSellLimit(false);
								}
							}
						
						}
						else if (transMajor.compareTo(zero) > 0){								//Found a buy transaction
						
							newBuy = true;
							
							transMinor = transMinor.multiply(new BigDecimal("-1").setScale(0, RoundingMode.DOWN));
							buys.addTransaction(transMajor.toString(), r.getUserTransactionsResponse().getTrades().get(i).getRate());
						
							BigDecimal amountToUse = new BigDecimal(amountToUseMax).setScale(major.getDecimalPlaces(), RoundingMode.DOWN);
							
							while (!cd.getBalances().refreshData()){}
							
							minorBalance.setValue(minorBalance.getValue().subtract(transMinor));
							majorBalance.setValue(new BigDecimal(cd.getBalances().getMajor(cd.getRuntimeData().getBook()).getBalance())
									.setScale(cd.getRuntimeData().getMajor().getDecimalPlaces(), RoundingMode.DOWN));
							majorRoundBalance.setValue(majorRoundBalance.getValue().add(transMajor));
							
							BigDecimal minMinorBal = new BigDecimal(minorBalance.getType().getMinTransAmount())
									.setScale(minorBalance.getType().getDecimalPlaces(), RoundingMode.DOWN);
							
							if (minorBalance.getValue().compareTo(minMinorBal) < 0){
								
								if (cd.getBotParams().isAutoAmount()){
									
									if (cd.getBotParams().isFirstRoundSellDiff()) cd.getBotParams().setFirstRoundSellDiff(false);
									amountToUse = new BigDecimal(Bot.calcAskAmount(cd)).setScale(major.getDecimalPlaces(), 
											RoundingMode.DOWN);
									cd.getBotParams().setAmountToUse(amountToUse.toString());
									cd.getBotParams().setAmountToUseOriginal(cd.getBotParams().getAmountToUse());
								}
								
								cd.getRuntimeData().getRoundSells().clearTransactions();
								cd.getBotParams().setDontBuyPast("0");
							}
							
							if (majorRoundBalance.getValue().compareTo(amountToUse) > 0){
								majorRoundBalance.setValue(amountToUse);
							}
						}
					}
							
					if (i < (r.getUserTransactionsResponse().getTrades().size() - 1)){
						i++;
					}
					else{
						lastTradeId = r.getUserTransactionsResponse().getTrades().get(i).getId();
					}
				
				}
			
				if (i > 0){
					lastTradeId = firstTradeIdFound;
					return true;
				}
				else return false;
			
			}
			else return false;
		
		}catch (Exception e){
			return false;
		}
	}
	
	public boolean findNewTransactions(TransactionList sells, TransactionList buys){
	
		String firstTradeIdFound = "";
		
		try{
		
			if (r.getUserTransactionsResponse().getTrades().size() > 0){
			
				int i = 0;
				BigDecimal zero = new BigDecimal("0").setScale(0, RoundingMode.DOWN);
				boolean found = false;
			
				while (!r.getUserTransactionsResponse().getTrades().get(i).getId().equals(lastTradeId)){
			
					if (i < r.getUserTransactionsResponse().getTrades().size()){
				
						BigDecimal transMajor = new BigDecimal(r.getUserTransactionsResponse().getTrades().get(i).getMajor())
								.setScale(major.getDecimalPlaces(), RoundingMode.DOWN);
						BigDecimal transMinor = new BigDecimal(r.getUserTransactionsResponse().getTrades().get(i).getMinor())
								.setScale(minor.getDecimalPlaces(), RoundingMode.DOWN);
					
						if (!found){
							firstTradeIdFound = r.getUserTransactionsResponse().getTrades().get(i).getId();
							found = true;
						}
						
						if (transMajor.compareTo(zero) < 0){					//Found a sell transaction
						
							transMajor = transMajor.multiply(new BigDecimal("-1").setScale(0, RoundingMode.DOWN));
							sells.addTransaction(transMajor.toString(), r.getUserTransactionsResponse().getTrades().get(i).getRate());
						
							majorBalance.setValue(majorBalance.getValue().subtract(transMajor));
							majorRoundBalance.setValue(majorRoundBalance.getValue().subtract(transMajor));
							minorBalance.setValue(minorBalance.getValue().add(transMinor));
						
						}
						else if (transMajor.compareTo(zero) > 0){				//Found a buy transaction
						
							transMinor = transMinor.multiply(new BigDecimal("-1").setScale(0, RoundingMode.DOWN));
							buys.addTransaction(transMinor.toString(), r.getUserTransactionsResponse().getTrades().get(i).getRate());
						
							majorBalance.setValue(majorBalance.getValue().add(transMajor));
							majorRoundBalance.setValue(majorRoundBalance.getValue().add(transMajor));
							minorBalance.setValue(minorBalance.getValue().subtract(transMinor));
						}
					
					
					}
							
					if (i < (r.getUserTransactionsResponse().getTrades().size() - 1)){
						i++;
					}
					else{
						lastTradeId = r.getUserTransactionsResponse().getTrades().get(i).getId();
					}
				
				}
			
				if (i > 0){
					lastTradeId = firstTradeIdFound;
					return true;
				}
				else return false;
			
			}
			else return false;
		
		}catch (Exception e){
			return false;
		}
	}

	public Coin getMinorBalance() {
		return minorBalance;
	}

	public void setMinorBalance(Coin minorBalance) {
		this.minorBalance = minorBalance;
	}

	public Coin getMajorRoundBalance() {
		return majorRoundBalance;
	}

	public void setMajorRoundBalance(Coin majorRoundBalance) {
		this.majorRoundBalance = majorRoundBalance;
	}

	public Coin getMajorBalance() {
		return majorBalance;
	}

	public void setMajorBalance(Coin majorBalance) {
		this.majorBalance = majorBalance;
	}

	private List<Integer> getPositionsOfId(String id, int firstFound){
		
		List<Integer> rVal = new ArrayList<Integer>();
		
		int found = 0;
		for(int i = 0; i < r.getUserTransactionsResponse().getTrades().size(); i++){
		
			if (found < firstFound){
				
				if (r.getUserTransactionsResponse().getTrades().get(i).getOrderId().equals(id)){
					
					rVal.add(i);
					found++;
				}
			}
			
		}
		
		return rVal;
	}
	
	private int numberOfNewSellTransById(String id){
		
		if (!id.equals(lastSellIdSearched)){
			transactionsSellByIdLast = 0;
		}
		
		int rVal = 0;
		
		for(int i = 0; i < r.getUserTransactionsResponse().getTrades().size(); i++){
			
			if (r.getUserTransactionsResponse().getTrades().get(i).getOrderId().equals(id)){
				rVal++;
			}
		}
		
		if (rVal >= transactionsSellByIdLast){
			rVal = rVal - transactionsSellByIdLast;
		}
		
		transactionsSellByIdLast = rVal;
		lastSellIdSearched = id;
		
		return rVal;
	}
	
	private int numberOfNewBuyTransById(String id){
		
		if (!id.equals(lastBuyIdSearched)){
			transactionsBuyByIdLast = 0;
		}
		
		int rVal = 0;
		
		for(int i = 0; i < r.getUserTransactionsResponse().getTrades().size(); i++){
			
			if (r.getUserTransactionsResponse().getTrades().get(i).getOrderId().equals(id)){
				rVal++;
			}
		}
		
		if (rVal >= transactionsBuyByIdLast){
			rVal = rVal - transactionsBuyByIdLast;
		}
		
		transactionsBuyByIdLast = rVal;
		lastBuyIdSearched = id;
		
		return rVal;
	}
	
	public String addMajor(){
		
		BigDecimal a = new BigDecimal(0.0).setScale(major.getDecimalPlaces());
		for (int i = 0; i < r.getUserTransactionsResponse().getTrades().size(); i++){
			
			a = a.add(new BigDecimal(r.getUserTransactionsResponse().getTrades().get(i).getMajor())
					.setScale(major.getDecimalPlaces(), RoundingMode.DOWN));
		}
		
		return a.toString();
	}
	
	public String addMinor(){
		
		BigDecimal a = new BigDecimal(0.0).setScale(minor.getDecimalPlaces());
		for (int i = 0; i < r.getUserTransactionsResponse().getTrades().size(); i++){
			
			a = a.add(new BigDecimal(r.getUserTransactionsResponse().getTrades().get(i).getMinor())
					.setScale(minor.getDecimalPlaces(), RoundingMode.DOWN));
		}
		
		return a.toString();
	}
	
}
