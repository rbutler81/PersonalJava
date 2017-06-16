package com.quadrigacx.api.returnJson;

import java.util.ArrayList;
import java.util.List;

import com.quadrigacx.api.returnJson.helpers.Transaction;

public class TransactionsResponse {

	private List<Transaction> transactions;

	public TransactionsResponse() {
		this.transactions = new ArrayList<Transaction>();
	}

	public TransactionsResponse(List<Transaction> transactions) {
		this.transactions = transactions;
	}

	public List<Transaction> getTransactions() {
		return transactions;
	}

	public void setTransactions(List<Transaction> transactions) {
		this.transactions = transactions;
	}
	
}
