package com.quadrigacx.api.returnJson.helpers;

public class Balance {

	private String available;
	private String reserved;
	private String balance;
	
	public Balance(){
		this.available = "";
		this.reserved = "";
		this.balance = "";
	}

	public Balance(String available, String reserved, String balance) {
		this.available = available;
		this.reserved = reserved;
		this.balance = balance;
	}

	public String getAvailable() {
		return available;
	}

	public void setAvailable(String available) {
		this.available = available;
	}

	public String getReserved() {
		return reserved;
	}

	public void setReserved(String reserved) {
		this.reserved = reserved;
	}

	public String getBalance() {
		return balance;
	}

	public void setBalance(String balance) {
		this.balance = balance;
	}
	
}
