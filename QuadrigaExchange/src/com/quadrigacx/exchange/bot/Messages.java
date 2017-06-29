package com.quadrigacx.exchange.bot;

import com.quadrigacx.api.returnJson.helpers.OrderResult;
import com.quadrigacx.exchange.threads.CommonData;

import helpers.Time;
import helpers.Timer;

public class Messages {

	public static void getActualBalances(CommonData cd){
		
		while (!cd.getBalances().refreshData()){}
		System.out.println(Time.getDateTimeStamp() + " " + Thread.currentThread().getName() + ": Quadriga " + cd.getRuntimeData().getMajor().getName()
				+ ": " + cd.getBalances().getMajor(cd.getRuntimeData().getBook()).getBalance() + " Quadriga " + cd.getRuntimeData().getMinor().getName()
				+ ": " + cd.getBalances().getMinor(cd.getRuntimeData().getBook()).getBalance());
	}
	
	public static void cancelFailed(CommonData cd, boolean sell){
		
		System.out.print(Time.getDateTimeStamp() + " " + Thread.currentThread().getName() + ": Cancel order failed: ");
		if (sell) System.out.println(cd.getRuntimeData().getCurrentSellOrder().getId());
		else System.out.println(cd.getRuntimeData().getCurrentBuyOrder().getId());
	}
	
	public static void maxBidTimer(CommonData cd, Timer t){
		
		if (t.isStarted()){
			System.out.println(Time.getDateTimeStamp() + " " + Thread.currentThread().getName() + ": At max bid for " 
					+ t.getAccumTime() + " of " + Time.convertMilliToHMS(t.getInterval()));
		}	
	}
	
	public static void raiseAmountToUse(CommonData cd){
		
		System.out.println(Time.getDateTimeStamp() + " " + Thread.currentThread().getName() + ": Raising amount to sell to " +
				cd.getBotParams().getAmountToUse() + " " + cd.getRuntimeData().getMajor().getName());
	}
	
	public static void wontBuyPast(CommonData cd){
		
		System.out.println(Time.getDateTimeStamp() + " " + Thread.currentThread().getName() + ": Won't buy past: " + cd.getBotParams().getDontBuyPast()
				+ " " + cd.getRuntimeData().getMinor().getName());
	}
	
	public static void getBalances(CommonData cd){
		System.out.println(Time.getDateTimeStamp() + " " + Thread.currentThread().getName() + ": " + cd.getRuntimeData().getMajor().getName()
				+ " : " + cd.getUserTransactions().getMajorBalance().getValue().toString() + " Round: " 
				+ cd.getUserTransactions().getMajorRoundBalance().getValue().toString() + "   " + cd.getRuntimeData().getMinor().getName()
				+ " : " + cd.getUserTransactions().getMinorBalance().getValue().toString());
	}
	
	public static void getHighLow(CommonData cd){
		cd.getWebOrderBook().getTc().lock();
		System.out.println(Time.getDateTimeStamp() + " " + Thread.currentThread().getName() + ": High bid: " 
				+ cd.getWebOrderBook().getHighBid() + " Low ask: " + cd.getWebOrderBook().getLowAsk());
		cd.getWebOrderBook().getTc().unlock();
	}
	
	public static void myBidAsk(CommonData cd){
		System.out.println(Time.getDateTimeStamp() + " " + Thread.currentThread().getName() + ": My bid: " 
				+ cd.getRuntimeData().getCurrentBuyOrder().getPrice() + " My ask: " + cd.getRuntimeData().getCurrentSellOrder().getPrice());
	}
	
	public static void placedAsk(CommonData cd){
		
		System.out.println(Time.getDateTimeStamp() + " " + Thread.currentThread().getName() + ": Placed an ask of " 
				+ cd.getSellLimit().getOrderResult().getAmount() + " " + cd.getRuntimeData().getMajor().getName() 
				+ " @ " + cd.getSellLimit().getOrderResult().getPrice() + " " + cd.getRuntimeData().getMinor().getName() 
				+ "/" + cd.getRuntimeData().getMajor().getName() + " - Current spread: " + cd.getWebOrderBook().getSpread() + "%");
	}
	
	public static void placedBid(CommonData cd){
		
		System.out.println(Time.getDateTimeStamp() + " " + Thread.currentThread().getName() + ": Placed a bid of " 
				+ cd.getBuyLimit().getOrderResult().getAmount() + " " + cd.getRuntimeData().getMajor().getName() 
				+ " @ " + cd.getBuyLimit().getOrderResult().getPrice() + " " + cd.getRuntimeData().getMinor().getName() 
				+ "/" + cd.getRuntimeData().getMajor().getName() + " - Current spread: " + cd.getWebOrderBook().getSpread() + "%");
	}
	
	public static void getSpread(CommonData cd){
		System.out.println(Time.getDateTimeStamp() + " " + Thread.currentThread().getName() + ": Spread: " + cd.getWebOrderBook().getSpread() + "%");
	}
	
	public static void currentAskOrderId(CommonData cd){
		
		System.out.println(Time.getDateTimeStamp() + " " + Thread.currentThread().getName() + ": Ask Order Id: " 
				+ cd.getRuntimeData().getCurrentSellOrder().getId());
	}
	
	public static void currentBidOrderId(CommonData cd){
		
		System.out.println(Time.getDateTimeStamp() + " " + Thread.currentThread().getName() + ": Bid Order Id: " 
				+ cd.getRuntimeData().getCurrentBuyOrder().getId());
	}
	
	public static void newSell(CommonData cd){
		
		System.out.println(Time.getDateTimeStamp() + " " + Thread.currentThread().getName() + ": New Sell! Total Sold: " 
				+ cd.getRuntimeData().getRoundSells().getTotalTraded() + " " + cd.getRuntimeData().getMajor().getName() 
				+ " @ Average Price: " + cd.getRuntimeData().getRoundSells().getAvgPriceString() + " " + cd.getRuntimeData().getMinor().getName());
	}
	
	public static void chooseBook(){
		
		System.out.println("****************************************");
		System.out.println("Choose book to trade");
		System.out.println("1. ETH/CAD");
		System.out.println("2. ETH/BTC");
		System.out.println("3. BTC/CAD");
		System.out.println("4. BTC/USD");
	}
	
	public static void setProfit(){
		
		System.out.println("Minimum profit (%): ");
	}
	
	public static void newBuy(CommonData cd){
		
		System.out.println(Time.getDateTimeStamp() + " " + Thread.currentThread().getName() + ": New Buy! Total Bought: " 
				+ cd.getRuntimeData().getBuys().getTotalTraded() + " " + cd.getRuntimeData().getMajor().getName() 
				+ " @ Average Price: " + cd.getRuntimeData().getBuys().getAvgPriceString() + " " + cd.getRuntimeData().getMinor().getName());
	
	}
	
	public static void cancelledOrder(OrderResult or){
		System.out.println(Time.getDateTimeStamp() + " " + Thread.currentThread().getName() + ": Order cancelled: " + or.getId());
	}
	
	public static void avgBuySellPrices(CommonData cd){
		System.out.println(Time.getDateTimeStamp() + " " + Thread.currentThread().getName() + ": Sold " + cd.getRuntimeData().getTotalSells().getTotalTraded()
				+  " " + cd.getRuntimeData().getMajor().getName() + " @ " + cd.getRuntimeData().getTotalSells().getAvgPriceString()
				+ " " + cd.getRuntimeData().getMinor().getName() + " Bought " + cd.getRuntimeData().getBuys().getTotalTraded() 
				+  " " + cd.getRuntimeData().getMajor().getName() + " @ " + cd.getRuntimeData().getBuys().getAvgPriceString() + " " 
				+ cd.getRuntimeData().getMinor().getName()); 
	}
	
	public static void cancelledOrderFailed(OrderResult or){
		System.out.println(Time.getDateTimeStamp() + " " + Thread.currentThread().getName() + ": Could not cancel order #" 
				+ or.getId() + " is no longer active");
	}
}
