package helpers.econ.currency;

public enum CoinType {
	
	BTC(8, "0.00000001", "BTC", "btc"), ETH(8, "0.00000001", "ETH", "eth"), CAD(2, "0.01", "CAD", "cad"), USD(2, "0.01", "USD", "usd");
	
	private int decimalPlaces;
	private String minDenom;
	private String name;
	private String smallName;
	
	private CoinType(int i, String j, String k, String l){
		decimalPlaces = i;
		minDenom = j;
		name = k;
		smallName = l;
	}
	
	public String getSmallName() {
		return smallName;
	}

	public String getName() {
		return name;
	}

	public int getDecimalPlaces(){
		return decimalPlaces;
	}
	
	public String getMinDenom(){
		return minDenom;
	}

}
