package com.quadrigacx.api;

public enum QuadrigaMethods {

	//Public Calls
	Ticker("public", "ticker", 0), OrderBook("public", "order_book", 1), Transactions("public", "transactions", 2),  
	
	//Private Calls
	Balance("private", "balance", 3), UserTransactions("private", "user_transactions", 4), OpenOrders("private", "open_orders", 5),  
	LookupOrder("private", "lookup_order", 6), CancelOrder("private", "cancel_order", 7), BuyLimitOrder("private", "buy", 8), 
	BuyMarketOrder("private", "buy", 9), SellLimitOrder("private", "sell", 10), SellMarketOrder("private", "sell", 11), 
	BTCDeposit("private", "bitcoin_deposit_address", 12), BTCWithdrawal("private", "bitcoin_withdrawal", 13), 
	ETHDeposit("private", "ether_deposit_address", 14), ETHWithdrawal("private", "ether_withdrawal", 15);  
		
	private String type;
	private String path;
	private int id;
	
	private QuadrigaMethods(String i, String j, int k){
		type = i;
		path = j;
		id = k;
	}
	
	public int getId() {
		return id;
	}

	public String getType(){
		return type;
	}
	
	public String getPath(){
		return path;
	}
}
