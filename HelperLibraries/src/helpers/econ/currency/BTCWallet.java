package helpers.econ.currency;

import java.math.BigDecimal;

public class BTCWallet extends Wallet {

	public BTCWallet(){
		super(CoinType.BTC);
	}
	
	public BTCWallet(double initialAmount){
		super(CoinType.BTC, initialAmount);
	}
	
	public BTCWallet(BigDecimal initialAmount){
		super(CoinType.BTC, initialAmount);
	}
	
	public BTCWallet(Coin initialCoin){
		super(initialCoin);
	}
	
}
