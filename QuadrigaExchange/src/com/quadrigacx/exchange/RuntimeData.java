package com.quadrigacx.exchange;

import com.quadrigacx.api.returnJson.helpers.GetCoinType;
import com.quadrigacx.api.returnJson.helpers.OrderResult;
import com.quadrigacx.exchange.threads.ThreadControl;

import helpers.econ.buysell.TransactionList;
import helpers.econ.currency.CoinType;

public class RuntimeData {

	private TransactionList buys;
	private TransactionList sells;
	private TransactionList totalSells;
	private OrderResult currentBuyOrder;
	private OrderResult currentSellOrder;
	private String book;
	private ThreadControl tc = new ThreadControl();
	private CoinType minor;
	private CoinType major;
	String newAskPrice = "";
	String newBidPrice = "";
	
	public RuntimeData(String book){
		this.book = book;
		this.minor = GetCoinType.getMinor(book);
		this.major = GetCoinType.getMajor(book);
		buys = new TransactionList(major, minor);
		sells = new TransactionList(major, minor);
		totalSells = new TransactionList(major, minor);
		currentBuyOrder = new OrderResult();
		currentSellOrder = new OrderResult();
	}
	
	public boolean isNewAskPrice(){
		
		if (!newAskPrice.equals(currentSellOrder.getPrice())){
			if (newAskPrice.equals("ERROR")){
				return false;
			}
			else return true;
		}
		else return false;
	}
	
	public TransactionList getTotalSells() {
		return totalSells;
	}

	public void setTotalSells(TransactionList totalSells) {
		this.totalSells = totalSells;
	}

	public boolean isNewBidPrice(){
		
		if (!newBidPrice.equals(currentBuyOrder.getPrice())){
			return true;
		}
		else return false;
	}
	
	public String getNewAskPrice() {
		return newAskPrice;
	}

	public void setNewAskPrice(String newAskPrice) {
		this.newAskPrice = newAskPrice;
	}

	public String getNewBidPrice() {
		return newBidPrice;
	}

	public void setNewBidPrice(String newBidPrice) {
		this.newBidPrice = newBidPrice;
	}

	public CoinType getMinor() {
		return minor;
	}

	public void setMinor(CoinType minor) {
		this.minor = minor;
	}

	public CoinType getMajor() {
		return major;
	}

	public void setMajor(CoinType major) {
		this.major = major;
	}

	public String getBook() {
		return book;
	}

	public void setBook(String book) {
		this.book = book;
	}

	public void setBuys(TransactionList buys) {
		this.buys = buys;
	}

	public void setRoundSells(TransactionList sells) {
		this.sells = sells;
	}

	public OrderResult getCurrentBuyOrder() {
		return currentBuyOrder;
	}

	public void setCurrentBuyOrder(OrderResult currentBuyOrder) {
		this.currentBuyOrder = currentBuyOrder;
	}

	public OrderResult getCurrentSellOrder() {
		return currentSellOrder;
	}

	public void setCurrentSellOrder(OrderResult currentSellOrder) {
		this.currentSellOrder = currentSellOrder;
	}

	public TransactionList getBuys() {
		return buys;
	}

	public TransactionList getRoundSells() {
		return sells;
	}
	
	public ThreadControl getTc() {
		return tc;
	}

	public void setTc(ThreadControl tc) {
		this.tc = tc;
	}
	
	public boolean checkIfAtDontBuyPast(OrderResult currentBuyPrice, String dontBuyPast){
		
		if (currentBuyPrice.getPrice().equals(dontBuyPast)) return true;
		else return false;
	}
	
	
}
