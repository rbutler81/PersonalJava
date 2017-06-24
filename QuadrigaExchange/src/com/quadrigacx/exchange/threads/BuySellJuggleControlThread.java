package com.quadrigacx.exchange.threads;

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.quadrigacx.api.returnJson.helpers.OrderResult;
import com.quadrigacx.exchange.bot.Bot;
import com.quadrigacx.exchange.bot.Messages;

import helpers.Console;
import helpers.Time;
import helpers.Timer;
import helpers.econ.currency.Coin;

public class BuySellJuggleControlThread extends GenericThread implements Runnable {

	public BuySellJuggleControlThread(CommonData cd) {
		super(cd);
	}

	@Override
	public void run() {
		
		boolean done = false;
		boolean selling = true;
		boolean buying = true;
		System.out.println("How much time to spend at max bid? (minutes)");
		int timerVal = Integer.parseInt(Console.getConsole());
		System.out.println();
		Timer timer = new Timer(1000*30);
		Timer atMaxBidTimer = new Timer(1000*60*timerVal);
		
		String oldLastTrade = "";
		
		BigDecimal zero = new BigDecimal(0.0).setScale(0, RoundingMode.DOWN);
		
		System.out.println("Amount of " + cd.getRuntimeData().getMajor().getName() + " to trade total: ");
		cd.getBotParams().setAmountToTrade(Console.getConsole());
		
		System.out.println("Amount of " + cd.getRuntimeData().getMajor().getName() + " to juggle with: ");
		cd.getBotParams().setAmountToUse(Console.getConsole());
		System.out.println();
		cd.getBotParams().setAmountToUseOriginal(cd.getBotParams().getAmountToUse());
		
		cd.getUserTransactions().setMinorBalance(new Coin(cd.getRuntimeData().getMinor(), 0.0));
		
		System.out.println("Start with a balance of " + cd.getRuntimeData().getMinor().getName() + " ? (y/n)");
		
		if (Console.getConsole().equals("y")){
			
			System.out.println("Amount: ");
			cd.getUserTransactions().setMinorBalance(new Coin(cd.getRuntimeData().getMinor(), Double.parseDouble(Console.getConsole())));
			
			System.out.println("\"Don't buy past\" price: ");
			cd.getBotParams().setDontBuyPast(BigDecimal.valueOf(Double.parseDouble(Console.getConsole())).setScale(
					cd.getRuntimeData().getMinor().getDecimalPlaces(), RoundingMode.DOWN).toString());
			
			BigDecimal hundred = new BigDecimal("100").setScale(0, RoundingMode.DOWN);
			BigDecimal fee = new BigDecimal(Bot.getFee(cd.getRuntimeData().getBook())).setScale(1, RoundingMode.DOWN);
			fee = fee.add(fee).add(hundred).add(new BigDecimal(cd.getBotParams().getMinDesiredProfit()).setScale(2, RoundingMode.DOWN));
			BigDecimal d = new BigDecimal(cd.getBotParams().getDontBuyPast()).setScale(cd.getRuntimeData().getMinor().getDecimalPlaces(), RoundingMode.DOWN);
			BigDecimal a = d.multiply(fee);
			a = a.divide(hundred, cd.getRuntimeData().getMinor().getDecimalPlaces(), RoundingMode.FLOOR);
			
			BigDecimal s = cd.getUserTransactions().getMinorBalance().getValue().divide(a, cd.getRuntimeData().getMajor().getDecimalPlaces(),
					RoundingMode.FLOOR);
			
			cd.getRuntimeData().getRoundSells().addTransaction(s.toString(), a.toString());
			
		}
		
		cd.getUserTransactions().setMajorBalance(new Coin(cd.getRuntimeData().getMajor()
				, Double.parseDouble(cd.getBotParams().getAmountToUse())));
		cd.getUserTransactions().setMajorRoundBalance(new Coin(cd.getRuntimeData().getMajor()
				, Double.parseDouble(cd.getBotParams().getAmountToUse())));
		
		
		String lastMinorAvg = cd.getRuntimeData().getBuys().getAvgPriceString();
		String lastMajorAvg = cd.getRuntimeData().getTotalSells().getAvgPriceString();
		
		System.out.println();
		System.out.println("Getting Order Book... ");
		System.out.println();
		cd.getOrderBook().refreshData();

		System.out.println("Getting User Transactions... ");
		System.out.println();
		cd.getUserTransactions().refreshData();
		cd.getUserTransactions().initializeFind();
		
		String idAsk = "";
		String idBid = "";
		
		cd.getUserTransactions().resetRoundSells();		
				
		timer.start();
		
		//While not done
		while (!done){
			
			idAsk = cd.getRuntimeData().getCurrentSellOrder().getId();
			idBid = cd.getRuntimeData().getCurrentBuyOrder().getId();
			
			selling = ((cd.getRuntimeData().getTotalSells().getTotalTradedAsBigDec().compareTo(cd.getBotParams().getAmountToTradeAsBigDec()) < 0)
					&& (cd.getUserTransactions().getMajorRoundBalance().getValue().compareTo(zero) > 0));
			
			buying = (cd.getUserTransactions().getMinorBalance().getValue().compareTo(zero) > 0);
			
			done = ((cd.getRuntimeData().getTotalSells().getTotalTradedAsBigDec().compareTo(cd.getBotParams().getAmountToTradeAsBigDec()) >= 0)
					&& (cd.getUserTransactions().getMinorBalance().getValue().compareTo(zero) == 0));
			
			if (done) cd.getWebOrderBook().getTc().done();
			
									
			//Run optimize bid / ask price functions
			if (selling || buying){
				
				if (selling){
				
					cd.getWebOrderBook().getTc().lock();
					cd.getRuntimeData().setNewAskPrice(Bot.optimizeBidWebData(cd.getRuntimeData().getCurrentSellOrder()
							, cd.getRuntimeData().getRoundSells(), cd, true));
					cd.getWebOrderBook().getTc().unlock();					
				}
				else if (!selling && !cd.getRuntimeData().getCurrentSellOrder().getPrice().equals("")){
					cd.getRuntimeData().setCurrentSellOrder(null);
					cd.getRuntimeData().setCurrentSellOrder(new OrderResult());
				}
				
								
				if (buying){
					
					cd.getWebOrderBook().getTc().lock();
					cd.getRuntimeData().setNewBidPrice(Bot.optimizeBidWebData(cd.getRuntimeData().getCurrentBuyOrder()
							, cd.getRuntimeData().getRoundSells(), cd, false));
					cd.getWebOrderBook().getTc().unlock();
					
					//Check max bid timer
					if (cd.getBotParams().getDontBuyPast().equals(cd.getRuntimeData().getCurrentBuyOrder().getPrice())
							&& !atMaxBidTimer.isStarted() && !selling){
						
						atMaxBidTimer.start();
					}
					else if (!cd.getBotParams().getDontBuyPast().equals(cd.getRuntimeData().getCurrentBuyOrder().getPrice())
							&& atMaxBidTimer.isStarted()){
						
						atMaxBidTimer.stop();
					}
					
					if (selling && atMaxBidTimer.isStarted()) atMaxBidTimer.stop();
					
					
				}
				else if (!buying && !cd.getRuntimeData().getCurrentBuyOrder().getPrice().equals("")){ 
					cd.getRuntimeData().setCurrentBuyOrder(null);
					cd.getRuntimeData().setCurrentBuyOrder(new OrderResult());
				}
				
				//Stop timer when not buying anymore, if it's still running
				if (!buying && atMaxBidTimer.isStarted()) atMaxBidTimer.stop();
				
			}
			
			//If max bid timer is done 
			if (atMaxBidTimer.isDone()){
				
				if (!cd.getBotParams().isRaisedSellLimit()) cd.getBotParams().setRaisedSellLimit(true);
				atMaxBidTimer.stop();
				
				BigDecimal atu = new BigDecimal(cd.getBotParams().getAmountToUse()).setScale(cd.getRuntimeData().getMajor().getDecimalPlaces(),
						RoundingMode.DOWN);
				BigDecimal atuo = new BigDecimal(cd.getBotParams().getAmountToUseOriginal()).setScale(cd.getRuntimeData().getMajor().getDecimalPlaces(),
						RoundingMode.DOWN);
				atu = atu.add(atuo);
				cd.getBotParams().setAmountToUse(atu.toString());
				
				cd.getUserTransactions().getMajorBalance().setValue(
						cd.getUserTransactions().getMajorBalance().getValue().add(atu));
				cd.getUserTransactions().getMajorRoundBalance().setValue(
						cd.getUserTransactions().getMajorRoundBalance().getValue().add(atu));
				
				Messages.raiseAmountToUse(cd);
				System.out.println();
			}
			
			//Check if any new sales
			cd.getWebOrderBook().getTc().lock();
			if (!oldLastTrade.equals(cd.getWebOrderBook().getData().getLastTradePrice()) || timer.isDone()){
				
				System.out.println(Time.getDateTimeStamp() + " " + Thread.currentThread().getName() + ": Checking for new sales..." );
								
				cd.getUserTransactions().refreshData();
				//True if a new sell or buy has taken place
				if (cd.getUserTransactions().findNewTransactionsJuggle(cd.getRuntimeData().getTotalSells()
						,cd.getRuntimeData().getRoundSells(), cd.getRuntimeData().getBuys()
						,cd.getBotParams().getAmountToUse(), cd)){
					
					if (cd.getUserTransactions().resetRoundSells()){
						cd.getRuntimeData().getRoundSells().clearTransactions();
						cd.getBotParams().setDontBuyPast("0");
					}
					
					if (!lastMajorAvg.equals(cd.getRuntimeData().getTotalSells().getAvgPriceString())){
						Messages.newSell(cd);
						System.out.println();
						lastMajorAvg = cd.getRuntimeData().getTotalSells().getAvgPriceString();
						selling = ((cd.getRuntimeData().getTotalSells().getTotalTradedAsBigDec().compareTo(cd.getBotParams().getAmountToTradeAsBigDec()) < 0)
								&& (cd.getUserTransactions().getMajorRoundBalance().getValue().compareTo(zero) > 0));
					}
					if (!lastMinorAvg.equals(cd.getRuntimeData().getBuys().getAvgPriceString())){
						Messages.newBuy(cd);
						System.out.println();
						lastMinorAvg = cd.getRuntimeData().getBuys().getAvgPriceString();
						buying = (cd.getUserTransactions().getMinorBalance().getValue().compareTo(zero) > 0);
					}
					
				}
				Messages.avgBuySellPrices(cd);
				Messages.getHighLow(cd);
				Messages.getSpread(cd);
				Messages.myBidAsk(cd);
				Messages.wontBuyPast(cd);
				Messages.maxBidTimer(cd, atMaxBidTimer);
				Messages.getBalances(cd);
				System.out.println();
				oldLastTrade = cd.getWebOrderBook().getData().getLastTradePrice();
				
				timer.start();
			}
			cd.getWebOrderBook().getTc().unlock();
			
			if (selling){
				
				//Check to see if new ask order should be made
				if (cd.getRuntimeData().isNewAskPrice() || cd.getUserTransactions().didMajorBalanceChange()){
				
					if (!cd.getRuntimeData().getCurrentSellOrder().getId().equals("")){
						//Cancel Order
						cd.getCancelOrder().cancelOrder(idAsk);
						//Check if order was cancelled
						if (!cd.getCancelOrder().isOrderCancelled()){			//if cancel failed - order may be filled - check for new transactions
					
							Messages.cancelledOrderFailed(cd.getRuntimeData().getCurrentSellOrder());
							System.out.println();
						
							cd.getUserTransactions().refreshData();
					
							cd.getUserTransactions().findNewTransactionsJuggle(cd.getRuntimeData().getTotalSells()
									, cd.getRuntimeData().getRoundSells(), cd.getRuntimeData().getBuys(), cd.getBotParams().getAmountToUse(), cd);
					
							if (cd.getUserTransactions().resetRoundSells()){
								cd.getRuntimeData().getRoundSells().clearTransactions();
								cd.getBotParams().setDontBuyPast("0");
							}
							
							if (!lastMajorAvg.equals(cd.getRuntimeData().getTotalSells().getAvgPriceString())){
							
								Messages.newSell(cd);
								System.out.println();
								lastMajorAvg = cd.getRuntimeData().getTotalSells().getAvgPriceString();
							}
							
							cd.getRuntimeData().setCurrentSellOrder(new OrderResult());
						}
						else{
							//Order was cancelled
							Messages.cancelledOrder(cd.getRuntimeData().getCurrentSellOrder());
							System.out.println();
							cd.getRuntimeData().setCurrentSellOrder(new OrderResult());
						}
					}
					//Place new order
					BigDecimal leftToSell = Bot.leftToSell(cd);
				
					if (cd.getUserTransactions().getMajorBalance().getValue().compareTo(zero) > 0){
						
						cd.getRl().waitTurn();
						
						cd.getWebOrderBook().getTc().lock();
						cd.getRuntimeData().setNewAskPrice(Bot.optimizeBidWebData(cd.getRuntimeData().getCurrentSellOrder()
								, cd.getRuntimeData().getRoundSells(), cd, true));
						cd.getWebOrderBook().getTc().unlock();	
						
						if (cd.getSellLimit().sellNow(cd.getRuntimeData().getNewAskPrice(), leftToSell.toString(), cd.getRuntimeData().getBook())){
					
							cd.getRuntimeData().setCurrentSellOrder(cd.getSellLimit().getOrderResult());
							Messages.placedAsk(cd);
							Messages.currentAskOrderId(cd);
							Messages.getHighLow(cd);
							Messages.getBalances(cd);
							System.out.println();
						}
					}
				}
			}
			
			if (buying){
				
				//Check to see if new ask order should be made
				if (cd.getRuntimeData().isNewBidPrice() || cd.getUserTransactions().didMinorBalanceChange()){
				
					if (!cd.getRuntimeData().getCurrentBuyOrder().getId().equals("")){
						//Cancel Order
						cd.getCancelOrder().cancelOrder(idBid);
						//Check if order was cancelled
						if (!cd.getCancelOrder().isOrderCancelled()){			//if cancel failed - order may be filled - check for new transactions
					
							Messages.cancelledOrderFailed(cd.getRuntimeData().getCurrentBuyOrder());
							System.out.println();
						
							cd.getUserTransactions().refreshData();
					
							cd.getUserTransactions().findNewTransactionsJuggle(cd.getRuntimeData().getTotalSells()
									,cd.getRuntimeData().getRoundSells(), cd.getRuntimeData().getBuys()
									,cd.getBotParams().getAmountToUse(), cd);
					
							if (!lastMinorAvg.equals(cd.getRuntimeData().getBuys().getAvgPriceString())){
							
								Messages.newBuy(cd);
								System.out.println();
								lastMinorAvg = cd.getRuntimeData().getBuys().getAvgPriceString();
							}
							
							cd.getRuntimeData().setCurrentBuyOrder(new OrderResult());
						}
						else{
							//Order was cancelled
							Messages.cancelledOrder(cd.getRuntimeData().getCurrentBuyOrder());
							System.out.println();
							cd.getRuntimeData().setCurrentBuyOrder(new OrderResult());
						}
					}
				
					//Place new order
					BigDecimal leftToBuy = cd.getUserTransactions().getMinorBalance().getValue();
				
					if (leftToBuy.compareTo(zero) > 0){
						
						cd.getRl().waitTurn();
						
						cd.getWebOrderBook().getTc().lock();
						cd.getRuntimeData().setNewBidPrice(Bot.optimizeBidWebData(cd.getRuntimeData().getCurrentBuyOrder()
								, cd.getRuntimeData().getRoundSells(), cd, false));
						cd.getWebOrderBook().getTc().unlock();	
						
						if (cd.getBuyLimit().buyVolumeInMinorNow(cd.getRuntimeData().getNewBidPrice()
								, cd.getUserTransactions().getMinorBalance().getValue().toString(), cd.getRuntimeData().getBook())){
					
							cd.getRuntimeData().setCurrentBuyOrder(cd.getBuyLimit().getOrderResult());
							Messages.placedBid(cd);
							Messages.wontBuyPast(cd);
							Messages.currentBidOrderId(cd);
							Messages.getHighLow(cd);
							Messages.getBalances(cd);
							System.out.println();
						}
						
					}
				}
			}
		}
		
		cd.getCancelOrder().cancelOrder(idAsk);
		Messages.avgBuySellPrices(cd);
	}
}