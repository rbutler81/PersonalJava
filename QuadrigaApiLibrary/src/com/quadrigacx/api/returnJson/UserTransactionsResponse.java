package com.quadrigacx.api.returnJson;

import java.util.ArrayList;
import java.util.List;

import com.quadrigacx.api.returnJson.helpers.DepositWithdrawal;
import com.quadrigacx.api.returnJson.helpers.Trade;

public class UserTransactionsResponse {

	private List<Trade> trades;
	private List<DepositWithdrawal> deposits;
	private List<DepositWithdrawal> withdrawals;

	public UserTransactionsResponse() {
		this.trades = new ArrayList<Trade>();
		this.deposits = new ArrayList<DepositWithdrawal>();
		this.withdrawals = new ArrayList<DepositWithdrawal>();
	}

	public List<DepositWithdrawal> getWithdrawals() {
		return withdrawals;
	}

	public void setWithdrawals(List<DepositWithdrawal> withdrawals) {
		this.withdrawals = withdrawals;
	}

	public List<DepositWithdrawal> getDeposits() {
		return deposits;
	}

	public void setDeposits(List<DepositWithdrawal> deposits) {
		this.deposits = deposits;
	}

	public List<Trade> getTrades() {
		return trades;
	}

	public void setTrades(List<Trade> trades) {
		this.trades = trades;
	}
	
}
