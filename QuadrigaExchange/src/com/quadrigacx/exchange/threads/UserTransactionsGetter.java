package com.quadrigacx.exchange.threads;

public class UserTransactionsGetter extends GenericThread implements Runnable{

	public UserTransactionsGetter(CommonData cd) {
		super(cd);
	}

	@Override
	public void run() {
	
		while (!cd.getUserTransactions().getTc().isDone()){
			while (!cd.getUserTransactions().getTc().isPaused()){
				
				cd.getUserTransactions().refreshData();
				
			}
		}
	}
}
