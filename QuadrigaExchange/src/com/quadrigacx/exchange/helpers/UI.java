package com.quadrigacx.exchange.helpers;

public class UI {
	
	public static String getBook(int i){
		
		if (i == 1) return "eth_cad";
		else if (i == 2) return "eth_btc";
		else if (i == 3) return "btc_cad";
		else if (i == 4) return "btc_usd";
		else return null;
	}
}
