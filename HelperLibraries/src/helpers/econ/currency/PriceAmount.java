package helpers.econ.currency;

public class PriceAmount {

	String price = "";
	String amount = "";
	
	public PriceAmount(String price, String amount){
		
		this.price = price;
		this.amount = amount;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}
	
	
}
