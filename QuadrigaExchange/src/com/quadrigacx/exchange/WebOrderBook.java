package com.quadrigacx.exchange;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import com.quadrigacx.api.returnJson.helpers.GetCoinType;
import com.quadrigacx.api.returnJson.helpers.OrderResult;
import com.quadrigacx.exchange.threads.ThreadControl;

import helpers.BigDec;
import helpers.Time;
import helpers.econ.currency.CoinType;
import helpers.econ.currency.PriceAmount;

public class WebOrderBook {

	protected ThreadControl tc = new ThreadControl();
	URL u; 
	String url;
	int index = 0;
	String pageData = "";
	private List<String> askRawData = new ArrayList<String>();
	private List<String> bidRawData = new ArrayList<String>();
	private List<PriceAmount> askList = new ArrayList<PriceAmount>();
	private List<PriceAmount> bidList = new ArrayList<PriceAmount>();
	private String lastTradePrice = "";
	private String lastTradeTime = "";
	private String oldLastTradeTime = "";
	private WebOrderBookData data;
	private CoinType minor = null;
	private CoinType major = null;
	
	private static final int ORDER_BOOK_ENTRIES = 30;
	
	public WebOrderBook(String book){
		
		this.minor = GetCoinType.getMinor(book);
		this.major = GetCoinType.getMajor(book);
		url = "https://www.quadrigacx.com/trade/" + major.getSmallName() + "/" + minor.getSmallName();
		
		try {
			u = new URL(url);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
		refresh();		
	}
	
	public boolean isNewTrade(){
		boolean rVal = false;
		tc.lock();
		if (!oldLastTradeTime.equals(lastTradeTime)){
			rVal = true;
		}
		oldLastTradeTime = lastTradeTime;
		tc.unlock();
		return rVal;
	}
	
	public WebOrderBookData getData() {
		return data;
	}
	
	public ThreadControl getTc() {
		return tc;
	}

	private boolean isNumber(char c){
		if (c == '0') return true;
		else if (c == '1') return true;
		else if (c == '2') return true;
		else if (c == '3') return true;
		else if (c == '4') return true;
		else if (c == '5') return true;
		else if (c == '6') return true;
		else if (c == '7') return true;
		else if (c == '8') return true;
		else if (c == '9') return true;
		else if (c == '.') return true;
		else return false;
	}
	
	private boolean isNextNumber(int i){
		i++;
		return isNumber(pageData.charAt(i));
	}
	
	private String getNumberAsString(int i){
		
		String s = "";
		index = i;
		boolean done = false;
		
		while (!done){
			
			s = s + pageData.charAt(index);
			if (isNextNumber(index)) index++;
			else done = true;
		}
		
		return s;
	}
	
	private int findNextNumber(int i){
		
		boolean found = false;
		i++;
		
		while (!found){
			
			found = isNumber(pageData.charAt(i));
			if (!found) i++;
						
		}
		return i;
	}
	
	private String findNextValue(int i){
		index = findNextNumber(i);
		return getNumberAsString(index);
	}
	
	public String getLowAsk(){
		return data.getAsks().get(0).getPrice();
	}
	
	public BigDecimal getLowAskPriceBigDec(){
		return new BigDecimal(data.getAsks().get(0).getPrice()).setScale(minor.getDecimalPlaces(), RoundingMode.DOWN);
	}
	
	public BigDecimal getLowAskAmountBigDec(){
		return new BigDecimal(data.getAsks().get(0).getAmount()).setScale(major.getDecimalPlaces(), RoundingMode.DOWN);
	}
	
	public String getHighBid(){
		return data.getBids().get(0).getPrice();
	}
	
	public BigDecimal getHighBidPriceBigDec(){
		return new BigDecimal(data.getBids().get(0).getPrice()).setScale(minor.getDecimalPlaces(), RoundingMode.DOWN);
	}
	
	public BigDecimal getHighBidAmountBigDec(){
		return new BigDecimal(data.getBids().get(0).getAmount()).setScale(major.getDecimalPlaces(), RoundingMode.DOWN);
	}
	
	public int getMyAskRank(OrderResult or){
		
		tc.lock();
		
		boolean orderFound = false;
		int i = 0;
		while (!orderFound || (i < ORDER_BOOK_ENTRIES)){
			if (data.getAsks().get(i).getPrice().equals(or.getPrice())){
				if (data.getAsks().get(i).getAmount().equals(or.getAmount())){
					orderFound = true;
				}
				else i++;
			}
			else i++;
		}
		
		tc.unlock();
		
		if (orderFound) return i;
		else return -1;
	}
	
	public int getMyBidRank(OrderResult or){
		
		tc.lock();
		
		boolean orderFound = false;
		int i = 0;
		while (!orderFound || (i < ORDER_BOOK_ENTRIES)){
			if (data.getBids().get(i).getPrice().equals(or.getPrice())){
				if (data.getBids().get(i).getAmount().equals(or.getAmount())){
					orderFound = true;
				}
				else i++;
			}
			else i++;
		}
		
		tc.unlock();
		
		if (orderFound) return i;
		else return -1;
	}
	
	public BigDecimal findFirstAskHigherThan(BigDecimal val, BigDecimal not){
		boolean found = false;
		BigDecimal c = null;
		int i = 0;
		
		tc.lock();
		
		while (!found && (i < data.getAsks().size())){
			c = new BigDecimal(data.getAsks().get(i).getPrice()).setScale(val.scale(), RoundingMode.DOWN);
			if ((BigDec.GT(c, val)) && !BigDec.EQ(c, not)){
				found = true;
			}
			else{
				i++;
			}
		}
		
		if (i == data.getAsks().size()) c = val;
		
		tc.unlock();
		
		return c;
	}
	
	public BigDecimal findFirstBidLowerThan(BigDecimal val, BigDecimal not){
		boolean found = false;
		BigDecimal c = null;
		int i = 0;
		
		tc.lock();
		
		while (!found && (i < data.getBids().size())){
			c = new BigDecimal(data.getBids().get(i).getPrice()).setScale(val.scale(), RoundingMode.DOWN);
			if ((BigDec.LT(c, val)) && !BigDec.EQ(c, not)){
				found = true;
			}
			else{
				i++;
			}
		}
		
		if (i == data.getBids().size()) c = val;
		
		tc.unlock();
		
		return c;
	}
	
	public boolean atMaxBid(String dbp){
		BigDecimal dbpBD = new BigDecimal(dbp).setScale(minor.getDecimalPlaces(), RoundingMode.DOWN);
		tc.lock();
		BigDecimal highBid = new BigDecimal(bidList.get(0).getPrice()).setScale(dbpBD.scale(), RoundingMode.DOWN);
		tc.unlock();
		return BigDec.GE(highBid, dbpBD);
	}
	
	public String getSpread(){
		
		tc.lock();
		double bid = Double.parseDouble(data.getBids().get(0).getPrice());
		double ask = Double.parseDouble(data.getAsks().get(0).getPrice());
		tc.unlock();
		
		BigDecimal b = new BigDecimal(bid).setScale(8, RoundingMode.DOWN);
		BigDecimal a = new BigDecimal(ask).setScale(8, RoundingMode.DOWN);
		BigDecimal s = a.subtract(b);
		s = s.divide(b, 8, RoundingMode.HALF_DOWN);
		s = s.multiply(new BigDecimal("100").setScale(3, RoundingMode.DOWN));
		s = s.setScale(3, RoundingMode.HALF_DOWN);
		
		return s.toString();
	}
	
	private void getAskData(){
		
		index = pageData.indexOf("table-orders asks");
		index = pageData.indexOf("<td>", index);
		askRawData.clear();
		
		while (askRawData.size() < ORDER_BOOK_ENTRIES){
			askRawData.add(findNextValue(index));
			index = pageData.indexOf("<td>", index);
		}
	}
	
	private void getLastTradePrice(){
		
		index = pageData.indexOf("Last trade");
		index = pageData.indexOf("<strong>", index);
		lastTradePrice = findNextValue(index);
	}
	
private void getLastTradeTime(){
		
		index = pageData.indexOf("<tr data-date=");
		lastTradeTime = findNextValue(index);
	}
	
	private void parseRawData(){
		
		int i = 0;
				
		while (askList.size() < (ORDER_BOOK_ENTRIES / 3)){
		
			askList.add(new PriceAmount(askRawData.get(i), askRawData.get(i + 1)));
			bidList.add(new PriceAmount(bidRawData.get(i), bidRawData.get(i + 1)));
			i = i + 3;
		}
		
	}
	
	private void getBidData(){
		
		index = pageData.indexOf("table-orders bids");
		index = pageData.indexOf("<td>", index);
		bidRawData.clear();
		
		while (bidRawData.size() < ORDER_BOOK_ENTRIES){
			bidRawData.add(findNextValue(index));
			index = pageData.indexOf("<td>", index);
		}
	}

	public void refresh(){
		
		pageData = null;
		pageData = "";
		
		connectAndGet();
		
		if (pageData.length() > 0){
			
			tc.lock();
			
			askList = null;
			askList = new ArrayList<PriceAmount>();
			bidList = null;
			bidList = new ArrayList<PriceAmount>();
			askRawData = null;
			askRawData = new ArrayList<String>();
			bidRawData = null;
			bidRawData = new ArrayList<String>();
			data = null;
			lastTradePrice = null;
			lastTradePrice = "";
		
			getAskData();
			getBidData();
			getLastTradeTime();
			parseRawData();
			data = new WebOrderBookData(askList, bidList, lastTradePrice, lastTradeTime);
			
			tc.unlock();
		}
		
	}
	
	private void connectAndGet(){
		
		HttpsURLConnection c = null;
		
		try {
			
			c = (HttpsURLConnection)u.openConnection();
	        
			c.setConnectTimeout(10000);
	        c.setReadTimeout(15000);
	        
	        c.setRequestMethod("GET");
	      	c.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
	      	
	      	BufferedReader br = null;
	        	        
	        br = new BufferedReader(new InputStreamReader((c.getInputStream())));
	        String line;
	        
	        while ((line = br.readLine()) != null)
	            pageData += line;
	        
	    } catch (Exception x) {
	    	System.out.println(Time.getDateTimeStamp() + " " + Thread.currentThread().getName() + ": Read timeout getting WebOrderBook");
	    	System.out.println();
	    } finally {
	    	c.disconnect();
	    }
	}
}
