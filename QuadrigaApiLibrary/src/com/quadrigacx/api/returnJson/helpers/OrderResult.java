package com.quadrigacx.api.returnJson.helpers;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class OrderResult {

	private String id;
	private String date;
	private String type;
	private String price;
	private String amount;
	private String book;
	private String status;
	
	public OrderResult() {
		this.id = "";
		this.date = "";
		this.type = "";
		this.price = "";
		this.amount = "";
		this.book = "";
		this.status = "";
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getBook() {
		return book;
	}

	public void setBook(String book) {
		this.book = book;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPrice() {
		return price;
	}
	
	public BigDecimal getPriceBigDec(int scale){
		return new BigDecimal(price).setScale(scale, RoundingMode.DOWN);
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
