package com.quadrigacx.api.returnJson.helpers;

public class DepositWithdrawal {
	
	private String asset;
	private String amount;
	private String method;
	private String fee;
	private String date;
	
	public DepositWithdrawal() {
		this.asset = "";
		this.amount = "";
		this.method = "";
		this.fee = "";
		this.date = "";
	}

	public String getAsset() {
		return asset;
	}

	public void setAsset(String asset) {
		this.asset = asset;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getFee() {
		return fee;
	}

	public void setFee(String fee) {
		this.fee = fee;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
	
	
	
	
}
