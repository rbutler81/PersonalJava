package com.quadrigacx.api.returnJson;

public class OrderLookupResponse {

	private String amount;
	private String created;
	private String book;
	private String price;
	private String status;
	private String id;
	private String type;
	private String updated;
	
	public OrderLookupResponse() {
		this.amount = "";
		this.created = "";
		this.book = "";
		this.price = "";
		this.status = "";
		this.id = "";
		this.type = "";
		this.updated = "";
	}

	public String getUpdated() {
		return updated;
	}

	public void setUpdated(String updated) {
		this.updated = updated;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getCreated() {
		return created;
	}

	public void setCreated(String created) {
		this.created = created;
	}

	public String getBook() {
		return book;
	}

	public void setBook(String book) {
		this.book = book;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	
	
	
}
