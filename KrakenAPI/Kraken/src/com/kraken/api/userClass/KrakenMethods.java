package com.kraken.api.userClass;

public enum KrakenMethods {

	//Public Calls
	ServerTime("public", "Time"), AssetInfo("public", "Assets"), AssetPairs("public", "AssetPairs"), TickerInfo("public", "Ticker"), 
	OHLC("public", "OHLC"), OrderBook("public", "Depth"), RecentTrades("public", "Trades"), Spread("public", "Spread"), 
	
	//Private Calls
	Balance("private", "Balance"), TradeBalance("private", "TradeBalance"), OpenOrders("private", "OpenOrders"), ClosedOrders("private", "ClosedOrders"), 
	QueryOrders("private", "QueryOrders"), TradeHistory("private", "TradesHistory"), QueryTrades("private", "QueryTrades"), 
	OpenPositions("private", "OpenPositions"), LedgersInfo("private", "Ledgers"), QueryLedgers("private", "QueryLedgers"), 
	TradeVolume("private", "TradeVolume"), AddOrder("private", "AddOrder"), CancelOrder("private", "CancelOrder"), 
	DepositAddresses("private", "DepositAddresses"), DepositMethods("private", "DepositMethods");
	
	private String type;
	private String path;
	
	private KrakenMethods(String i, String j){
		type = i;
		path = j;
	}
	
	public String getType(){
		return type;
	}
	
	public String getPath(){
		return path;
	}
}
