package com.quadrigacx.api.returnJson.helpers;

import helpers.econ.currency.CoinType;

public class GetCoinType {

	public static CoinType getMajor(String book){
		
		String major = book.substring(0, 3);
		if (major.equals("cad")){
			return CoinType.CAD;
		}
		else if (major.equals("eth")){
			return CoinType.ETH;
		}
		else if (major.equals("btc")){
			return CoinType.BTC;
		}
		else if (major.equals("usd")){
			return CoinType.USD;
		}
		return CoinType.CAD;
	}
	
public static CoinType getMinor(String book){
		
		String minor = book.substring(4);
		if (minor.equals("cad")){
			return CoinType.CAD;
		}
		else if (minor.equals("eth")){
			return CoinType.ETH;
		}
		else if (minor.equals("btc")){
			return CoinType.BTC;
		}
		else if (minor.equals("usd")){
			return CoinType.USD;
		}
		return CoinType.CAD;
	}
}
