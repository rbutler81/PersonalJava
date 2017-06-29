package helpers.econ.currency;

public enum CoinType {
	
	BTC(8, "0.00000001", "BTC", "btc", "0.0005"), ETH(8, "0.00000001", "ETH", "eth", "0.00000100"), 
	CAD(2, "0.01", "CAD", "cad", "0.01"), USD(2, "0.01", "USD", "usd", "0.01");
	
	private int decimalPlaces;
	private String minDenom;
	private String name;
	private String smallName;
	private String minTransAmount;
	
	private CoinType(int i, String j, String k, String l, String m){
		decimalPlaces = i;
		minDenom = j;
		name = k;
		smallName = l;
		minTransAmount = m;
	}
	
	public String getSmallName() {
		return smallName;
	}

		
	public String getMinTransAmount() {
		return minTransAmount;
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
