package helpers.econ.buysell;

import helpers.econ.currency.Coin;

public class CoinCoinPair {

	private Coin amount;
	private Coin price;
	
	public CoinCoinPair(Coin amount, Coin price){
		this.amount = amount;
		this.price = price;
	}

	public Coin getAmount() {
		return amount;
	}

	public void setAmount(Coin amount) {
		this.amount = amount;
	}

	public Coin getPrice() {
		return price;
	}

	public void setPrice(Coin price) {
		this.price = price;
	}
	
}
