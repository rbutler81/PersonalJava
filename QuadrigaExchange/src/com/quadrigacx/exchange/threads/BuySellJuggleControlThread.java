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
		Timer timer = new Timer(1000*30);
		BigDecimal zero = new BigDecimal(0.0).setScale(0, RoundingMode.DOWN);
		Timer atMaxBidTimer;
		
		Messages.getActualBalances(cd);
		System.out.println();
		
		System.out.println("Amount of " + cd.getRuntimeData().getMajor().getName() + " to trade total: ");
		cd.getBotParams().setAmountToTrade(Console.getConsole());
		System.out.println();
		
		System.out.println("Automate the max bid timer / sell amount? (y/n)");
		String automate = Console.getConsole();
		System.out.println();
		
		if (automate.equals("n")){
			
			cd.getBotParams().setAutoAmount(false);
		
			System.out.println("How much time to spend at max bid? (minutes)");
			int timerVal = Integer.parseInt(Console.getConsole());
			System.out.println();
			
			atMaxBidTimer = new Timer(1000*60*timerVal);
						
			System.out.println("Amount of " + cd.getRuntimeData().getMajor().getName() + " to juggle with: ");
			cd.getBotParams().setAmountToUse(Console.getConsole());
			System.out.println();
			cd.getBotParams().setAmountToUseOriginal(cd.getBotParams().getAmountToUse());
			
			cd.getUserTransactions().setMinorBalance(new Coin(cd.getRuntimeData().getMinor(), 0.0));
		}
		else {
			
			cd.getBotParams().setAutoAmount(true);
			
			System.out.println("Fraction of " + cd.getRuntimeData().getMajor().getName() + " balance to use for initial sell:");
			cd.getBotParams().setFractionToSell(Console.getConsole()); 
			System.out.println();
			
			System.out.println("Amount of days to last if price gets too high:");
			cd.getBotParams().setDaysToLast(Console.getConsole()); 
			System.out.println();
			
			atMaxBidTimer = new Timer(1000*60*Integer.parseInt(Bot.timeToWait(cd).toString()));
			System.out.println("Will wait " + Time.convertMilliToHMS(atMaxBidTimer.getInterval()) + " before selling more");
			System.out.println();
			
			cd.getBotParams().setAmountToUse(Bot.calcAskAmount(cd));
			cd.getBotParams().setAmountToUseOriginal(cd.getBotParams().getAmountToUse());
		}
		
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
		
		while (!cd.getBalances().refreshData()){}
		
		cd.getUserTransactions().setMajorBalance(new Coin(cd.getRuntimeData().getMajor()
				, Double.parseDouble(cd.getBalances().getMajor(cd.getRuntimeData().getBook()).getBalance())));
		cd.getUserTransactions().setMajorRoundBalance(new Coin(cd.getRuntimeData().getMajor()
				, Double.parseDouble(cd.getBotParams().getAmountToUse())));
		
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
		
		timer.start();
		
		//While not done
		while (!done){
			
			idAsk = cd.getRuntimeData().getCurrentSellOrder().getId();
			idBid = cd.getRuntimeData().getCurrentBuyOrder().getId();
			
			selling = ((cd.getRuntimeData().getTotalSells().getTotalTradedAsBigDec().compareTo(cd.getBotParams().getAmountToTradeAsBigDec()) < 0)
					&& (Bot.aboveMinBalanceSell(cd)));
			
			buying = Bot.aboveMinBalanceBuy(cd);
			
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
				
				BigDecimal remainingBalance = new BigDecimal("0").setScale(cd.getRuntimeData().getMajor().getDecimalPlaces(), 
						RoundingMode.DOWN).add(cd.getUserTransactions().getMajorRoundBalance().getValue());
				BigDecimal atu = new BigDecimal(cd.getBotParams().getAmountToUse()).setScale(cd.getRuntimeData().getMajor()
						.getDecimalPlaces(), RoundingMode.DOWN);
				BigDecimal atuo = new BigDecimal(cd.getBotParams().getAmountToUseOriginal()).setScale(cd.getRuntimeData()
						.getMajor().getDecimalPlaces(), RoundingMode.DOWN);
				
				atu = atu.add(atuo);
				cd.getBotParams().setAmountToUse(remainingBalance.add(atu).toString());
				
				cd.getUserTransactions().getMajorRoundBalance().setValue(
						cd.getUserTransactions().getMajorRoundBalance().getValue().add(atu));
				
				Messages.raiseAmountToUse(cd);
				System.out.println();
			}
			
			//Check if any new sales
			if (cd.getWebOrderBook().isNewTrade() || timer.isDone()){
				
				System.out.println(Time.getDateTimeStamp() + " " + Thread.currentThread().getName() + ": Checking for new sales..." );
								
				cd.getUserTransactions().refreshData();
				//True if a new sell or buy has taken place
				if (cd.getUserTransactions().findNewTransactionsJuggle(cd.getRuntimeData().getTotalSells()
						,cd.getRuntimeData().getRoundSells(), cd.getRuntimeData().getBuys()
						,cd.getBotParams().getAmountToUse(), cd)){
					
					if (cd.getUserTransactions().isNewSell()){
						Messages.newSell(cd);
						Messages.getActualBalances(cd);
						System.out.println();
						selling = ((cd.getRuntimeData().getTotalSells().getTotalTradedAsBigDec().compareTo(cd.getBotParams()
								.getAmountToTradeAsBigDec()) < 0) && (Bot.aboveMinBalanceSell(cd)));
						if (!selling){
							Bot.checkAndCancelOpenSells(cd);
							System.out.println();
							cd.getRuntimeData().setCurrentSellOrder(new OrderResult());
						}
					}
					if (cd.getUserTransactions().isNewBuy()){
						Messages.newBuy(cd);
						Messages.getActualBalances(cd);
						System.out.println();
						buying = Bot.aboveMinBalanceBuy(cd);
						if (!buying){
							Bot.checkAndCancelOpenBuys(cd);
							System.out.println();
							cd.getRuntimeData().setCurrentBuyOrder(new OrderResult());
						}
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
				
				
				timer.start();
			}
			
			
			if (selling){
				
				//Check to see if new ask order should be made
				if (cd.getRuntimeData().isNewAskPrice() || cd.getUserTransactions().didMajorBalanceChange()){
				
					if (!cd.getRuntimeData().getCurrentSellOrder().getId().equals("")){
						//Cancel Order
						cd.getCancelOrder().cancelOrder(idAsk);
						boolean doneCancel = false;
						
						while (!doneCancel){
						//Check if order was cancelled
							if (!cd.getCancelOrder().isOrderCancelled()){			//if cancel failed - order may be filled - look closer
						
								if (cd.getCancelOrder().isOrderNotFound()){			//cancel order error message is 106 - probably got filled
									
									Messages.cancelledOrderFailed(cd.getRuntimeData().getCurrentSellOrder());
									System.out.println();
								
									cd.getUserTransactions().refreshData();
							
									cd.getUserTransactions().findNewTransactionsJuggle(cd.getRuntimeData().getTotalSells()
											, cd.getRuntimeData().getRoundSells(), cd.getRuntimeData().getBuys(), cd.getBotParams().getAmountToUse(), cd);
							
									if (cd.getUserTransactions().isNewSell()){
									
										Messages.newSell(cd);
										Messages.getActualBalances(cd);
										System.out.println();
									}
									cd.getRuntimeData().setCurrentSellOrder(new OrderResult());
									doneCancel = true;
								}
								else{												//cancel order response came back with invalid data
									
									cd.getCancelOrder().cancelOrder(idAsk);
									Messages.cancelFailed(cd, true);
									System.out.println();
								}
							}
							else{
								//Order was cancelled
								Messages.cancelledOrder(cd.getRuntimeData().getCurrentSellOrder());
								System.out.println();
								cd.getRuntimeData().setCurrentSellOrder(new OrderResult());
								doneCancel = true;
							}
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
						else{									//if order returns error, check and cancel any open sell orders 
							Bot.checkAndCancelOpenSells(cd);
							cd.getRuntimeData().setCurrentSellOrder(new OrderResult());
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
						boolean doneCancel = false;
						
						while (!doneCancel){
							
							//Check if order was cancelled
							if (!cd.getCancelOrder().isOrderCancelled()){			//if cancel failed - order may be filled - look closer
						
								if (cd.getCancelOrder().isOrderNotFound()){			//if cancel order err code is 106 - order probably filled
									
									Messages.cancelledOrderFailed(cd.getRuntimeData().getCurrentBuyOrder());
									System.out.println();
								
									cd.getUserTransactions().refreshData();
							
									cd.getUserTransactions().findNewTransactionsJuggle(cd.getRuntimeData().getTotalSells()
											,cd.getRuntimeData().getRoundSells(), cd.getRuntimeData().getBuys()
											,cd.getBotParams().getAmountToUse(), cd);
							
									if (cd.getUserTransactions().isNewBuy()){
									
										Messages.newBuy(cd);
										Messages.getActualBalances(cd);
										System.out.println();
									}
									
									cd.getRuntimeData().setCurrentBuyOrder(new OrderResult());
									doneCancel = true;
								}
								else{												//cancel order failed - bad data
									
									cd.getCancelOrder().cancelOrder(idBid);
									Messages.cancelFailed(cd, false);
								}
							}
							else{
								//Order was cancelled
								Messages.cancelledOrder(cd.getRuntimeData().getCurrentBuyOrder());
								System.out.println();
								cd.getRuntimeData().setCurrentBuyOrder(new OrderResult());
								doneCancel = true;
							}
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
						else if ((cd.getBuyLimit().getErrCode() == 21) || (cd.getBuyLimit().getErrCode() == 999) ){	//Insufficient funds to place buy order
							
							//Check if any new sales
							System.out.println(Time.getDateTimeStamp() + " " + Thread.currentThread().getName() + ": Checking for new sales..." );
												
							cd.getUserTransactions().refreshData();
							//True if a new sell or buy has taken place
							if (cd.getUserTransactions().findNewTransactionsJuggle(cd.getRuntimeData().getTotalSells()
									,cd.getRuntimeData().getRoundSells(), cd.getRuntimeData().getBuys()
									,cd.getBotParams().getAmountToUse(), cd)){
								
								if (cd.getUserTransactions().isNewSell()){
									Messages.newSell(cd);
									Messages.getActualBalances(cd);
									System.out.println();
									selling = ((cd.getRuntimeData().getTotalSells().getTotalTradedAsBigDec().compareTo(cd.getBotParams()
											.getAmountToTradeAsBigDec()) < 0) && (Bot.aboveMinBalanceSell(cd)));
									if (!selling){
										cd.getRuntimeData().setCurrentSellOrder(new OrderResult());
									}
								}
								if (cd.getUserTransactions().isNewBuy()){
									Messages.newBuy(cd);
									Messages.getActualBalances(cd);
									System.out.println();
									buying = Bot.aboveMinBalanceBuy(cd);
									if (!buying){
										cd.getRuntimeData().setCurrentBuyOrder(new OrderResult());
									}
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
							
							Bot.checkAndCancelOpenBuys(cd);
							cd.getRuntimeData().setCurrentBuyOrder(new OrderResult());
							while (!cd.getBalances().refreshData()){}
							cd.getUserTransactions().getMinorBalance().setValue(cd.getBalances().getMinor(cd.getRuntimeData().getBook()).getAvailable());
							
							timer.start();
						}
						
					}
				}
			}
		}
		
		cd.getCancelOrder().cancelOrder(idAsk);
		Messages.avgBuySellPrices(cd);
	}
}
