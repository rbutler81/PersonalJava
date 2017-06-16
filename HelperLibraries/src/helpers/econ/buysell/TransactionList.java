package helpers.econ.buysell;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import helpers.econ.currency.Coin;
import helpers.econ.currency.CoinType;

public class TransactionList {

	private List<CoinCoinPair> list;
	private int pricePrecision;
	private int amountPrecision;
	CoinType price;
	CoinType amount;
	
	public TransactionList(CoinType amount, CoinType price){
		this.list = new ArrayList<CoinCoinPair>();
		this.pricePrecision = price.getDecimalPlaces();
		this.amountPrecision = amount.getDecimalPlaces();
		this.price = price;
		this.amount = amount;
	}
	
	public BigDecimal getTotalTradedAsBigDec(){
		return new BigDecimal(getTotalTraded()).setScale(amount.getDecimalPlaces(), RoundingMode.DOWN);
	}
	
	public String getTotalTraded(){
		
		BigDecimal rVal = new BigDecimal("0").setScale(amount.getDecimalPlaces(), RoundingMode.DOWN);
		for (int i = 0; i < list.size(); i++){
			
			rVal = rVal.add(list.get(i).getAmount().getValue());
		}
		
		return rVal.toString();
	}
	
	public void addTransaction(String amount, String price){
		
		Coin newPrice = new Coin(this.price, Double.parseDouble(price));
		Coin newAmount = new Coin(this.amount, Double.parseDouble(amount));
		
		CoinCoinPair newEntry = new CoinCoinPair(newAmount, newPrice);
		list.add(newEntry);
	}
	
	public void clearTransactions(){
		
		list.clear();
	}
	
	public String getAvgPriceString(){
		
		return calcAvgPrice().toString();
	}
	
	public double getAvgPriceDouble(){
		
		return Double.parseDouble(calcAvgPrice().toString());
	}
	
	private BigDecimal calcAvgPrice(){
		
		BigDecimal productTotal = new BigDecimal(0.0);
		productTotal = productTotal.setScale(pricePrecision, RoundingMode.DOWN);
		
		BigDecimal totalAmount = new BigDecimal(0.0);
		totalAmount = totalAmount.setScale(amountPrecision, RoundingMode.DOWN);
		
		for (int i = 0; i < list.size(); i++){
			BigDecimal product = list.get(i).getAmount().getValue().multiply(list.get(i).getPrice().getValue());
			productTotal = productTotal.add(product);
			totalAmount = totalAmount.add(list.get(i).getAmount().getValue());
		}
		
		BigDecimal result = new BigDecimal("0").setScale(0);
		try{
			result = productTotal.divide(totalAmount, pricePrecision, RoundingMode.DOWN);
		}
		catch (Exception e){
			
		}
		return result;
	}

}
