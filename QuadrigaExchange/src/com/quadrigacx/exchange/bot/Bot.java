package com.quadrigacx.exchange.bot;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import com.quadrigacx.api.returnJson.helpers.GetCoinType;
import com.quadrigacx.api.returnJson.helpers.OrderResult;
import com.quadrigacx.exchange.threads.CommonData;

import helpers.BigDec;
import helpers.econ.buysell.TransactionList;
import helpers.econ.currency.CoinType;
import helpers.econ.currency.PriceAmount;


public class Bot {

	public static boolean checkAndCancelOpenBuys(CommonData cd){
		
		List<String> openBuys = cd.getOpenOrders().getOpenBuyOrders();
		if (openBuys.size() > 0){
			
			for (int i = 0; i < openBuys.size(); i++){
				
				cd.getCancelOrder().cancelOrder(openBuys.get(i));
				
				if (cd.getCancelOrder().isOrderCancelled()){
					Messages.cancelledOrphanedOrder(openBuys.get(i));
					System.out.println();
				}
				else{
					Messages.cancelledOrphanedOrderFailed(openBuys.get(i));
					System.out.println();
				}
			}
		return true;
		}
	return false;
	}
	
	public static boolean checkAndCancelOpenSells(CommonData cd){
		
		List<String> openSells = cd.getOpenOrders().getOpenSellOrders();
		if (openSells.size() > 0){
			
			for (int i = 0; i < openSells.size(); i++){
				
				cd.getCancelOrder().cancelOrder(openSells.get(i));
				if (cd.getCancelOrder().isOrderCancelled()){
					Messages.cancelledOrphanedOrder(openSells.get(i));
					System.out.println();
				}
				else{
					Messages.cancelledOrphanedOrderFailed(openSells.get(i));
					System.out.println();
				}
			}
		return true;
		}
	return false;
	}
	
	public static boolean aboveMinBalanceSell(CommonData cd){
		
		BigDecimal min = new BigDecimal(cd.getRuntimeData().getMajor().getMinTransAmount())
				.setScale(cd.getRuntimeData().getMajor().getDecimalPlaces(), RoundingMode.DOWN);
		BigDecimal bal = new BigDecimal(cd.getUserTransactions().getMajorRoundBalance().getValue().toString())
				.setScale(cd.getRuntimeData().getMajor().getDecimalPlaces(), RoundingMode.DOWN);
		
		return (bal.compareTo(min) > 0);
	}
	
	public static boolean aboveMinBalanceBuy(CommonData cd){
		
		BigDecimal min = new BigDecimal(cd.getRuntimeData().getMajor().getMinTransAmount())
				.setScale(cd.getRuntimeData().getMajor().getDecimalPlaces(), RoundingMode.DOWN);
				
		cd.getWebOrderBook().getTc().lock();
		String bidPrice = (Bot.optimizeBidWebData(cd.getRuntimeData().getCurrentBuyOrder(), cd.getRuntimeData().getRoundSells(), cd, false));
		cd.getWebOrderBook().getTc().unlock();	
		
		BigDecimal price = new BigDecimal(bidPrice).setScale(cd.getRuntimeData().getMinor().getDecimalPlaces(), RoundingMode.DOWN);
		BigDecimal minorBal = new BigDecimal(cd.getUserTransactions().getMinorBalance().getValue().toString())
				.setScale(cd.getRuntimeData().getMinor().getDecimalPlaces(), RoundingMode.DOWN);
		
		if ((price.compareTo(new BigDecimal(0).setScale(0)) == 0)){
			return false;
		}
		
		BigDecimal bal = minorBal.divide(price, cd.getRuntimeData().getMajor().getDecimalPlaces(), RoundingMode.DOWN);
						
		return (bal.compareTo(min) > 0);
	}
	
	public static String calcAskAmount(CommonData cd){
		
		while (!cd.getBalances().refreshData()){}
		
		String balance = cd.getBalances().getMajor(cd.getRuntimeData().getBook()).getBalance();
				
		BigDecimal b = new BigDecimal(balance).setScale(cd.getRuntimeData().getMajor().getDecimalPlaces(), RoundingMode.DOWN);
		BigDecimal f = new BigDecimal(cd.getBotParams().getFractionToSell()).setScale(10, RoundingMode.DOWN);
		
		b = b.multiply(f).setScale(cd.getRuntimeData().getMajor().getDecimalPlaces(), RoundingMode.DOWN);
		
		return b.toString();		
	}
	
	public static BigDecimal timeToWait(CommonData cd){
		
		int i = cd.getBotParams().getFractionToSell().indexOf('.');
		String scale = cd.getBotParams().getFractionToSell().substring(i);
		int s = scale.length() - 1;
		BigDecimal f = new BigDecimal(cd.getBotParams().getFractionToSell()).setScale(s, RoundingMode.DOWN);
		
		BigDecimal d = new BigDecimal(cd.getBotParams().getDaysToLast()).setScale(0, RoundingMode.DOWN);
		
		BigDecimal numerator = new BigDecimal("2880").setScale(0, RoundingMode.DOWN)
				.multiply(f)
				.multiply(d);
		BigDecimal denom = new BigDecimal("1").setScale(0, RoundingMode.DOWN)
				.subtract(f);
		
		return numerator.divide(denom, 0, RoundingMode.DOWN);
	}
	
	public static BigDecimal leftToSell(CommonData cd){
		
		BigDecimal totalToTrade = new BigDecimal(cd.getBotParams().getAmountToTrade())
				.setScale(cd.getRuntimeData().getMajor().getDecimalPlaces(), RoundingMode.DOWN);
		BigDecimal totalTraded = new BigDecimal(cd.getRuntimeData().getTotalSells().getTotalTraded())
				.setScale(cd.getRuntimeData().getMajor().getDecimalPlaces(), RoundingMode.DOWN);
		BigDecimal majorRoundBalance = cd.getUserTransactions().getMajorRoundBalance().getValue();
		BigDecimal maxToUse = new BigDecimal(cd.getBotParams().getAmountToUse())
				.setScale(cd.getRuntimeData().getMajor().getDecimalPlaces(), RoundingMode.DOWN);
		
		BigDecimal totalLeftToTrade = totalToTrade.subtract(totalTraded);
		
		if (totalLeftToTrade.compareTo(majorRoundBalance) > 0){
			
			if (majorRoundBalance.compareTo(maxToUse) > 0){
			
				return maxToUse;
			}
			
			else return majorRoundBalance;
			
		}
		else return totalLeftToTrade;
	}
	
	public static String optimizeBidWebDataNew(OrderResult or, CommonData cd, boolean sell){
	
		//Get fee for trading pair and coin types
		String feeString = getFee(cd.getOrderBook().getBook());
		CoinType minor = GetCoinType.getMinor(cd.getOrderBook().getBook());
		
		// *** Setup variables needed in calculations
		
		//My current buy or sell price
		if (or.getPrice().equals("")){
			if (sell){
				or.setPrice("999999999");
			}
			else if (!sell){
				or.setPrice(minor.getMinDenom());
			}
		}
		BigDecimal myBid = new BigDecimal(or.getPrice()).setScale(minor.getDecimalPlaces(), RoundingMode.DOWN);
		//Determine fee + desired spread
		BigDecimal fee = new BigDecimal(feeString).setScale(2, RoundingMode.DOWN).add(new BigDecimal(feeString).setScale(2, RoundingMode.DOWN));
		BigDecimal desiredSpread = fee.add(new BigDecimal(cd.getBotParams().getMinDesiredProfit()).setScale(2, RoundingMode.DOWN));
		
		if (sell){					//Ask Order

			//Determine the current "don't sell less than" price
			BigDecimal dontSellLess = new BigDecimal(cd.getWebOrderBook().getData().getBids().get(0).getPrice())
					.setScale(minor.getDecimalPlaces(), RoundingMode.DOWN);
			BigDecimal num = desiredSpread.divide(new BigDecimal("100").setScale(4, RoundingMode.DOWN), 4, RoundingMode.DOWN);
			num = num.add(new BigDecimal("1").setScale(0, RoundingMode.DOWN));
			dontSellLess = dontSellLess.multiply(num).setScale(minor.getDecimalPlaces(), RoundingMode.UP);
						
			//Check to see if I'm the lowest ask
			BigDecimal lowAsk = new BigDecimal(cd.getWebOrderBook().getData().getAsks().get(0).getPrice())
					.setScale(minor.getDecimalPlaces(), RoundingMode.DOWN);
			boolean amountsMatch = cd.getWebOrderBook().getData().getAsks().get(0).getAmount().equals(or.getAmount());
			
			//Evaluate conditions to make a decision
			boolean A = anyAsk_LE_DSL(dontSellLess, cd.getWebOrderBook().getData().getAsks());
			boolean B = myAsk_EQ_DSL(dontSellLess, or.getPrice());
			boolean C = myAsk_GT_DSL(dontSellLess, or.getPrice());
			boolean D = myAsk_EQ_LowAsk(or.getPrice(), cd.getWebOrderBook().getData().getAsks(), amountsMatch, minor);
			
			//Decide which possible bid direction to make
			boolean bidLower = (!B && C && !D);
			boolean bidHigherOrSame = (A && B && !C) || (!A && !B && C && D) || (A && !B && C && !D);
			
			//Look into cases to see which bid direction to make
			if (bidLower && !bidHigherOrSame){
				return bidOneLess(minor, lowAsk).toString();				//out bid the lowest ask
			}
			else if (!bidLower && bidHigherOrSame){
				BigDecimal i = cd.getWebOrderBook().findFirstAskHigherThan(dontSellLess, myBid);
				return bidOneLess(minor, i).toString();
			}
			else if (bidLower && bidHigherOrSame){
				BigDecimal i = cd.getWebOrderBook().findFirstAskHigherThan(dontSellLess, myBid);
				return bidOneLess(minor, i).toString();
			}
			else return "99999";
		}
		else{												//Bid Order
			
			//My current volume weighted average sell price
			BigDecimal avgSellPrice = new BigDecimal(cd.getRuntimeData().getRoundSells().getAvgPriceString())
					.setScale(minor.getDecimalPlaces(), RoundingMode.DOWN);
			//Determine the current "don't buy higher than" price
			BigDecimal dontBuyPast = avgSellPrice.multiply(new BigDecimal("100").setScale(0, RoundingMode.DOWN));
			BigDecimal dontBuyPastDenom = desiredSpread.add(new BigDecimal("100").setScale(0, RoundingMode.DOWN));
			dontBuyPast = dontBuyPast.divide(dontBuyPastDenom, minor.getDecimalPlaces(), RoundingMode.DOWN);
			
			cd.getBotParams().getTc().lock();
			cd.getBotParams().setDontBuyPast(dontBuyPast.toString());
			cd.getBotParams().getTc().unlock();
			
			//Check to see if I'm the highest bid
			BigDecimal highBid = new BigDecimal(cd.getWebOrderBook().getData().getBids().get(0).getPrice())
					.setScale(minor.getDecimalPlaces(), RoundingMode.DOWN);
			boolean amountsMatch = cd.getWebOrderBook().getData().getBids().get(0).getAmount().equals(or.getAmount());
			
			//Evaluate conditions to make a decision
			boolean A = anyBid_GE_DBP(dontBuyPast, cd.getWebOrderBook().getData().getBids());
			boolean B = myBid_EQ_DBP(dontBuyPast, or.getPrice());
			boolean C = myBid_LT_DBP(dontBuyPast, or.getPrice());
			boolean D = myBid_EQ_HighBid(or.getPrice(), cd.getWebOrderBook().getData().getBids(), amountsMatch, minor);
			
			//Decide which possible bid direction to make
			boolean bidHigher = (!B && C && !D);
			boolean bidLowerOrSame = (A && B && !C) || (!A && !B && C && D) || (A && !B && C && !D);
			
			//Look into cases to see which bid direction to make
			if (bidHigher && !bidLowerOrSame){
				return bidOneMore(minor, highBid).toString();				//out bid the lowest ask
			}
			else if (!bidHigher && bidLowerOrSame){
				BigDecimal i = cd.getWebOrderBook().findFirstBidLowerThan(dontBuyPast, myBid);
				return bidOneMore(minor, i).toString();
			}
			else if (bidHigher && bidLowerOrSame){
				BigDecimal i = cd.getWebOrderBook().findFirstBidLowerThan(dontBuyPast, myBid);
				return bidOneMore(minor, i).toString();
			}
			else return minor.getMinDenom();
		}
	}
	
	private static boolean anyBid_GE_DBP(BigDecimal dbp, List<PriceAmount> bids){
		BigDecimal highBid = new BigDecimal(bids.get(0).getPrice()).setScale(dbp.scale(), RoundingMode.DOWN);
		return BigDec.GE(highBid, dbp);
	}
	
	private static boolean myBid_EQ_DBP(BigDecimal dbp, String myBid){
		BigDecimal myBidBD = new BigDecimal(myBid).setScale(dbp.scale(), RoundingMode.DOWN);
		return BigDec.EQ(myBidBD, dbp);
	}
	
	private static boolean myBid_LT_DBP(BigDecimal dbp, String myBid){
		BigDecimal myBidBD = new BigDecimal(myBid).setScale(dbp.scale(), RoundingMode.DOWN);
		return BigDec.LT(myBidBD, dbp);
	}
	
	private static boolean myBid_EQ_HighBid(String myBid, List<PriceAmount> bids, boolean amountsMatch, CoinType minor){
		BigDecimal myBidBD = new BigDecimal(myBid).setScale(minor.getDecimalPlaces(), RoundingMode.DOWN);
		BigDecimal highBid = new BigDecimal(bids.get(0).getPrice()).setScale(myBidBD.scale(), RoundingMode.DOWN);
		return (BigDec.EQ(myBidBD, highBid) && amountsMatch);
	}
	
	private static boolean anyAsk_LE_DSL(BigDecimal dsl, List<PriceAmount> asks){
		BigDecimal lowAsk = new BigDecimal(asks.get(0).getPrice()).setScale(dsl.scale(), RoundingMode.DOWN);
		return BigDec.LE(lowAsk, dsl);
	}
	
	private static boolean myAsk_EQ_DSL(BigDecimal dsl, String myAsk){
		BigDecimal myAskBD = new BigDecimal(myAsk).setScale(dsl.scale(), RoundingMode.DOWN);
		return BigDec.EQ(myAskBD, dsl);
	}
	
	private static boolean myAsk_GT_DSL(BigDecimal dsl, String myAsk){
		BigDecimal myAskBD = new BigDecimal(myAsk).setScale(dsl.scale(), RoundingMode.DOWN);
		return BigDec.GT(myAskBD, dsl);
	}
	
	private static boolean myAsk_EQ_LowAsk(String myAsk, List<PriceAmount> asks, boolean amountsMatch, CoinType minor){
		BigDecimal myAskBD = new BigDecimal(myAsk).setScale(minor.getDecimalPlaces(), RoundingMode.DOWN);
		BigDecimal lowAsk = new BigDecimal(asks.get(0).getPrice()).setScale(myAskBD.scale(), RoundingMode.DOWN);
		return (BigDec.EQ(myAskBD, lowAsk) && amountsMatch);
	}
	
	public static String optimizeBidWebData(OrderResult or, TransactionList sellTl, CommonData cd, boolean sell){
	
		//Get fee for trading pair and coin types
		String feeString = getFee(cd.getOrderBook().getBook());
		CoinType minor = GetCoinType.getMinor(cd.getOrderBook().getBook());
		
		// *** Setup variables needed in calculations
		//Zero
		BigDecimal zero = new BigDecimal("0.0").setScale(0, RoundingMode.DOWN);		
		//My current buy or sell price
		if (or.getPrice().equals("")){
			if (sell){
				
				or.setPrice("99999999");
			}
			else if (!sell){
			
				or.setPrice(minor.getMinDenom());
			}
		}
		BigDecimal myBid = new BigDecimal(or.getPrice()).setScale(minor.getDecimalPlaces(), RoundingMode.DOWN);
		//Determine fee + desired spread
		BigDecimal fee = new BigDecimal(feeString).setScale(2, RoundingMode.DOWN).add(new BigDecimal(feeString).setScale(2, RoundingMode.DOWN));
		BigDecimal desiredSpread = fee.add(new BigDecimal(cd.getBotParams().getMinDesiredProfit()).setScale(2, RoundingMode.DOWN));
		
				
		if (sell){					//Ask Order

			//Determine the current "don't sell less than" price
			BigDecimal dontSellLess = new BigDecimal(cd.getWebOrderBook().getData().getBids().get(0).getPrice())
					.setScale(minor.getDecimalPlaces(), RoundingMode.DOWN);
			BigDecimal num = desiredSpread.divide(new BigDecimal("100").setScale(4, RoundingMode.DOWN), 4, RoundingMode.DOWN);
			num = num.add(new BigDecimal("1").setScale(0, RoundingMode.DOWN));
			dontSellLess = dontSellLess.multiply(num).setScale(minor.getDecimalPlaces(), RoundingMode.UP);
						
			//Check to see if I'm the lowest ask
			BigDecimal lowAsk = new BigDecimal(cd.getWebOrderBook().getData().getAsks().get(0).getPrice())
					.setScale(minor.getDecimalPlaces(), RoundingMode.DOWN);
			boolean amountsMatch = cd.getWebOrderBook().getData().getAsks().get(0).getAmount().equals(or.getAmount());
			
			if (myBid.compareTo(lowAsk) > 0){									//My ask is higher than the lowest ask
			
				if (bidOneLess(minor, lowAsk).compareTo(dontSellLess) > 0){		//Lowest Ask - 1 is > than my "don't sell less than" price
					return bidOneLess(minor, lowAsk).toString();				//Place a bid lower than current bid price
				}
				else return dontSellLess.toString();							//Place bid at my low limit price
			}
			
			else if (myBid.compareTo(lowAsk) == 0){								//My ask is equal to the lowest ask
			
				if (amountsMatch){												//Probably my bid
					
					BigDecimal nextHighest = new BigDecimal(cd.getWebOrderBook().getData().getAsks().get(1).getPrice())
							.setScale(minor.getDecimalPlaces(), RoundingMode.DOWN);			//Get next highest ask
					BigDecimal bidDifference = nextHighest.subtract(lowAsk);
					BigDecimal minIncrement = new BigDecimal(minor.getMinDenom().toString())
							.setScale(minor.getDecimalPlaces(), RoundingMode.DOWN);
					
					if (myBid.compareTo(dontSellLess) < 0){							//Check if I'm above my lowest sell price
						return dontSellLess.toString();								//Place my lowest bid
					}
					
					else if (bidDifference.compareTo(minIncrement) == 0){			//My bid is as high as can be
						return lowAsk.toString();									//Same bid
					}
					
					else if (bidDifference.compareTo(zero) == 0) {					//Same bid
						return lowAsk.toString();	
					}
					
					else{
						return nextHighest.subtract(minIncrement).toString();		//Place bid lower than current next lowest bid
					}
				}
				
				else {																//This bid is someone else's, try to out bid
				
					if (bidOneLess(minor, lowAsk).compareTo(dontSellLess) > 0){		//Lowest Ask - 1 is > than my "don't sell less than" price
						return bidOneLess(minor, lowAsk).toString();				//Place a bid lower than current bid price
					}
					else return dontSellLess.toString();							//Place bid at my low limit price
				}
			}
			
			else return "ERROR";
		}
		
		else{																		//Bid Order
		
			//My current volume weighted average sell price
			BigDecimal avgSellPrice = new BigDecimal(sellTl.getAvgPriceString()).setScale(minor.getDecimalPlaces(), RoundingMode.DOWN);
			//Determine the current "don't buy higher than" price
			BigDecimal dontBuyPast = avgSellPrice.multiply(new BigDecimal("100").setScale(0, RoundingMode.DOWN));
			BigDecimal dontBuyPastDenom = desiredSpread.add(new BigDecimal("100").setScale(0, RoundingMode.DOWN));
			dontBuyPast = dontBuyPast.divide(dontBuyPastDenom, minor.getDecimalPlaces(), RoundingMode.DOWN);
			
			cd.getBotParams().getTc().lock();
			cd.getBotParams().setDontBuyPast(dontBuyPast.toString());
			cd.getBotParams().getTc().unlock();
			
			//Check to see if I'm the highest bid
			BigDecimal highBid = new BigDecimal(cd.getWebOrderBook().getData().getBids().get(0).getPrice())
					.setScale(minor.getDecimalPlaces(), RoundingMode.DOWN);
			boolean amountsMatch = cd.getWebOrderBook().getData().getBids().get(0).getAmount().equals(or.getAmount());
			
			if (myBid.compareTo(highBid) < 0){									//My bid is less than the highest bid
			
				if (bidOneMore(minor, highBid).compareTo(dontBuyPast) < 0){		//My (bid + 1) is less than my don't buy past price 
				
					return bidOneMore(minor, highBid).toString();
				}
				else return dontBuyPast.toString();
			}
			
			else if (myBid.compareTo(highBid) == 0){										//My bid is equal to the highest bid
			
				if (amountsMatch){															//The bid is probably mine
				
					BigDecimal nextLowest = new BigDecimal(cd.getWebOrderBook().getData().getBids().get(1).getPrice())	//Get next lowest bid
							.setScale(minor.getDecimalPlaces(), RoundingMode.DOWN);
					BigDecimal bidDifference = highBid.subtract(nextLowest);
					BigDecimal minIncrement = new BigDecimal(minor.getMinDenom().toString())
							.setScale(minor.getDecimalPlaces(), RoundingMode.DOWN);
					
					if (bidDifference.compareTo(minIncrement) == 0){							//My bid is as low as it can be
						return highBid.toString();
					}
					
					else if (bidDifference.compareTo(zero) == 0){								//Same bid
						return highBid.toString();
					}
					
					else {																		//My bid has room to go lower
						return nextLowest.add(minIncrement).toString();
					}
					
				}
				
				else {																			//The bid is someone else's, try to out bid
					
					if (bidOneMore(minor, highBid).compareTo(dontBuyPast) < 0){					//My (bid + 1) is less than my average sell price 
						
						return bidOneMore(minor, highBid).toString();
					}
					else return dontBuyPast.toString();
				}
			
			}
			
			else return new BigDecimal(minor.getMinDenom().toString()).setScale(minor.getDecimalPlaces(), RoundingMode.DOWN).toString();
			
		}
		
				
	}
	
	public static String optimizeBid(OrderResult or, TransactionList sellTl, CommonData cd){
		
		//Get fee for trading pair and coin types
		String feeString = getFee(cd.getOrderBook().getBook());
		CoinType minor = GetCoinType.getMinor(cd.getOrderBook().getBook());
		
		// *** Setup variables needed in calculations
		//Zero
		BigDecimal zero = new BigDecimal("0.0").setScale(0, RoundingMode.DOWN);		
		//My current buy or sell price
		BigDecimal myBid = new BigDecimal(or.getPrice()).setScale(minor.getDecimalPlaces(), RoundingMode.DOWN);
		//Determine fee + desired spread
		BigDecimal fee = new BigDecimal(feeString).setScale(2, RoundingMode.DOWN).add(new BigDecimal(feeString).setScale(2, RoundingMode.DOWN));
		BigDecimal desiredSpread = fee.add(new BigDecimal(cd.getBotParams().getMinDesiredProfit()).setScale(2, RoundingMode.DOWN));
		
				
		if (or.getType().equals("1")){					//Ask Order

			//Determine the current "don't sell less than" price
			BigDecimal dontSellLess = new BigDecimal(cd.getOrderBook().getTopBid().getPrice())
					.setScale(minor.getDecimalPlaces(), RoundingMode.DOWN);
			BigDecimal num = desiredSpread.divide(new BigDecimal("100").setScale(4, RoundingMode.DOWN), 4, RoundingMode.DOWN);
			num = num.add(new BigDecimal("1").setScale(0, RoundingMode.DOWN));
			dontSellLess = dontSellLess.multiply(num).setScale(minor.getDecimalPlaces(), RoundingMode.UP);
						
			//Check to see if I'm the lowest ask
			BigDecimal lowAsk = new BigDecimal(cd.getOrderBook().getTopAsk().getPrice())
					.setScale(minor.getDecimalPlaces(), RoundingMode.DOWN);
			boolean amountsMatch = cd.getOrderBook().getTopAsk().getAmount().equals(or.getAmount());
			
			if (myBid.compareTo(lowAsk) > 0){									//My ask is higher than the lowest ask
			
				if (bidOneLess(minor, lowAsk).compareTo(dontSellLess) > 0){		//Lowest Ask - 1 is > than my "don't sell less than" price
					return bidOneLess(minor, lowAsk).toString();				//Place a bid lower than current bid price
				}
				else return dontSellLess.toString();							//Place bid at my low limit price
			}
			
			else if (myBid.compareTo(lowAsk) == 0){								//My ask is equal to the lowest ask
			
				if (amountsMatch){												//Probably my bid
					
					BigDecimal nextHighest = new BigDecimal(cd.getOrderBook().getAllAsks().get(1).getPrice())
							.setScale(minor.getDecimalPlaces(), RoundingMode.DOWN);			//Get next highest ask
					BigDecimal bidDifference = nextHighest.subtract(lowAsk);
					BigDecimal minIncrement = new BigDecimal(minor.getMinDenom().toString())
							.setScale(minor.getDecimalPlaces(), RoundingMode.DOWN);
					
					if (myBid.compareTo(dontSellLess) < 0){						//Check if I'm above my lowest sell price
						return dontSellLess.toString()			;				//Place my lowest bid
					}
					
					else if (bidDifference.compareTo(minIncrement) == 0){		//My bid is as high as can be
						return lowAsk.toString();								//Same bid
					}
					
					else if (bidDifference.compareTo(zero) == 0) {				//Same bid
						return lowAsk.toString();	
					}
					
					else{
						return nextHighest.subtract(minIncrement).toString();			//Place bid lower than current next lowest bid
					}
				}
				
				else {																//This bid is someone else's, try to out bid
				
					if (bidOneLess(minor, lowAsk).compareTo(dontSellLess) > 0){		//Lowest Ask - 1 is > than my "don't sell less than" price
						return bidOneLess(minor, lowAsk).toString();				//Place a bid lower than current bid price
					}
					else return dontSellLess.toString();							//Place bid at my low limit price
				}
			}
			
			else return "ERROR";
		}
		
		else if (or.getType().equals("0")){					//Bid Order
		
			//My current volume weighted average sell price
			BigDecimal avgSellPrice = new BigDecimal(sellTl.getAvgPriceString()).setScale(minor.getDecimalPlaces(), RoundingMode.DOWN);
			//Determine the current "don't buy higher than" price
			BigDecimal dontBuyPast = avgSellPrice.multiply(new BigDecimal("100").setScale(0, RoundingMode.DOWN));
			BigDecimal dontBuyPastDenom = desiredSpread.add(new BigDecimal("100").setScale(0, RoundingMode.DOWN));
			dontBuyPast = dontBuyPast.divide(dontBuyPastDenom, minor.getDecimalPlaces(), RoundingMode.DOWN);
			
			cd.getBotParams().getTc().lock();
			cd.getBotParams().setDontBuyPast(dontBuyPast.toString());
			cd.getBotParams().getTc().unlock();
			
			//Check to see if I'm the highest bid
			BigDecimal highBid = new BigDecimal(cd.getOrderBook().getTopBid().getPrice())
					.setScale(minor.getDecimalPlaces(), RoundingMode.DOWN);
			boolean amountsMatch = cd.getOrderBook().getTopBid().getAmount().equals(or.getAmount());
			
			if (myBid.compareTo(highBid) < 0){									//My bid is less than the highest bid
			
				if (bidOneMore(minor, highBid).compareTo(dontBuyPast) < 0){		//My (bid + 1) is less than my don't buy past price 
				
					return bidOneMore(minor, highBid).toString();
				}
				else return dontBuyPast.toString();
			}
			
			else if (myBid.compareTo(highBid) == 0){										//My bid is equal to the highest bid
			
				if (amountsMatch){															//The bid is probably mine
				
					BigDecimal nextLowest = new BigDecimal(cd.getOrderBook().getAllBids().get(1).getPrice())	//Get next lowest bid
							.setScale(minor.getDecimalPlaces(), RoundingMode.DOWN);
					BigDecimal bidDifference = highBid.subtract(nextLowest);
					BigDecimal minIncrement = new BigDecimal(minor.getMinDenom().toString())
							.setScale(minor.getDecimalPlaces(), RoundingMode.DOWN);
					
					if (myBid.compareTo(dontBuyPast) > 0){						//Check if I'm above my highest sell price
						return dontBuyPast.toString();							//Place my highest bid
					}
					
					else if (bidDifference.compareTo(minIncrement) == 0){							//My bid is as low as it can be
						return highBid.toString();
					}
					
					else if (bidDifference.compareTo(zero) == 0){								//Same bid
						return highBid.toString();
					}
					else {																		//My bid has room to go lower
						return nextLowest.add(minIncrement).toString();
					}
				}
				
				else {																//The bid is someone else's, try to out bid
					
					if (bidOneMore(minor, highBid).compareTo(dontBuyPast) < 0){		//My (bid + 1) is less than my average sell price 
						
						return bidOneMore(minor, highBid).toString();
					}
					else return dontBuyPast.toString();
				}
			
			}
			
			else return "ERROR";
			
		}
		
		return "ERROR";
		
	}
	
	public static String getFee(String book){
		
		if (book.equals("eth_btc")) return "0.2";
		else return "0.5";
	}
	
	private static BigDecimal bidOneMore(CoinType c, BigDecimal toOutBid){
		BigDecimal increment = new BigDecimal(c.getMinDenom()).setScale(c.getDecimalPlaces(), RoundingMode.DOWN);
		return increment.add(toOutBid);
	}
	
	private static BigDecimal bidOneLess(CoinType c, BigDecimal toOutBid){
		BigDecimal increment = new BigDecimal(c.getMinDenom()).setScale(c.getDecimalPlaces(), RoundingMode.DOWN);
		return toOutBid.subtract(increment);
	}
}
