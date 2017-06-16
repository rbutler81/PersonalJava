package com.quadrigacx.exchange.threads;

public class WebOrderBookGetter extends GenericThread implements Runnable{

	public WebOrderBookGetter(CommonData cd) {
		super(cd);
	}

	@Override
	public void run() {
		
		while (!cd.getWebOrderBook().getTc().isDone()){
			
			cd.getWebOrderBook().getTc().lock();				
			try{
				cd.getWebOrderBook().refresh();
			} catch (Exception e){
				e.printStackTrace();
			}
			cd.getWebOrderBook().getTc().unlock();
			
			
			
		}
		
	}

}
