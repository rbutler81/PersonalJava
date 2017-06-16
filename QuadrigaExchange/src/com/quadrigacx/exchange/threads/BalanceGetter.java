package com.quadrigacx.exchange.threads;

public class BalanceGetter extends GenericThread implements Runnable {

	public BalanceGetter(CommonData cd) {
		super(cd);
	}

	@Override
	public void run() {
		
		while (!cd.getBalances().getTc().isDone()){
			while (!cd.getBalances().getTc().isPaused()){
				
				cd.getBalances().refreshData();
				
				
			}
		}
		
	}

}
