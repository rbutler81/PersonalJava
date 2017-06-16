package helpers.econ.currency;

public class BTC extends Coin {

	public BTC(){
		super(CoinType.BTC);
	}
	
	public BTC(double initialBalance){
		super(CoinType.BTC, initialBalance);
	}
	
		
}
