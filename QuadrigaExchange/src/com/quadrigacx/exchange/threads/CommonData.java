package com.quadrigacx.exchange.threads;

import com.quadrigacx.exchange.Balances;
import com.quadrigacx.exchange.BuyLimit;
import com.quadrigacx.exchange.CancelOrder;
import com.quadrigacx.exchange.LookupOrder;
import com.quadrigacx.exchange.OrderBook;
import com.quadrigacx.exchange.RuntimeData;
import com.quadrigacx.exchange.SellLimit;
import com.quadrigacx.exchange.UserTransactions;
import com.quadrigacx.exchange.WebOrderBook;
import com.quadrigacx.exchange.bot.BotParams;

import helpers.RateLimiter;

public class CommonData {

	private OrderBook orderBook; 
	private Balances balances;
	private UserTransactions userTransactions;
	private LookupOrder lookupOrder;
	private CancelOrder cancelOrder;
	private BuyLimit buyLimit;
	private SellLimit sellLimit;
	private BotParams botParams;
	private RuntimeData runtimeData;
	private WebOrderBook webOrderBook;
	private RateLimiter rl;
	
	public CommonData(String book, RateLimiter rl){
		this.rl = rl;
		orderBook = new OrderBook(rl, book);
		balances = new Balances(rl);
		userTransactions = new UserTransactions(rl, book);
		lookupOrder = new LookupOrder(rl);
		cancelOrder = new CancelOrder(rl);
		buyLimit = new BuyLimit(rl);
		sellLimit = new SellLimit(rl);
		botParams = new BotParams(book);
		runtimeData = new RuntimeData(book);
		webOrderBook = new WebOrderBook(book);
	}

	public WebOrderBook getWebOrderBook() {
		return webOrderBook;
	}

	public void setWebOrderBook(WebOrderBook webOrderBook) {
		this.webOrderBook = webOrderBook;
	}

	public RuntimeData getRuntimeData() {
		return runtimeData;
	}

	public void setRuntimeData(RuntimeData runtimeData) {
		this.runtimeData = runtimeData;
	}

	public RateLimiter getRl() {
		return rl;
	}

	public BotParams getBotParams() {
		return botParams;
	}

	public void setBotParams(BotParams botParams) {
		this.botParams = botParams;
	}

	public SellLimit getSellLimit() {
		return sellLimit;
	}

	public void setSellLimit(SellLimit sellLimit) {
		this.sellLimit = sellLimit;
	}

	public BuyLimit getBuyLimit() {
		return buyLimit;
	}

	public void setBuyLimit(BuyLimit buyLimit) {
		this.buyLimit = buyLimit;
	}

	public CancelOrder getCancelOrder() {
		return cancelOrder;
	}

	public void setCancelOrder(CancelOrder cancelOrder) {
		this.cancelOrder = cancelOrder;
	}

	public LookupOrder getLookupOrder() {
		return lookupOrder;
	}

	public void setLookupOrder(LookupOrder lookupOrder) {
		this.lookupOrder = lookupOrder;
	}

	public UserTransactions getUserTransactions() {
		return userTransactions;
	}

	public void setUserTransactions(UserTransactions userTransactions) {
		this.userTransactions = userTransactions;
	}

	public Balances getBalances() {
		return balances;
	}

	public void setBalances(Balances balances) {
		this.balances = balances;
	}

	public OrderBook getOrderBook() {
		return orderBook;
	}

	public void setOrderBook(OrderBook orderBook) {
		this.orderBook = orderBook;
	}
	
	

}
