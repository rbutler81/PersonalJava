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
import com.quadrigacx.exchange.threads.ThreadControl;

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
	private WebOrderBookData data;
	
	public WebOrderBookData getData() {
		return data;
	}

	public WebOrderBook(String book){
		
		CoinType minor = GetCoinType.getMinor(book);
		CoinType major = GetCoinType.getMajor(book);
		url = "https://www.quadrigacx.com/trade/" + major.getSmallName() + "/" + minor.getSmallName();
		
		try {
			u = new URL(url);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
		refresh();		
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
	
	public String getHighBid(){
		return data.getBids().get(0).getPrice();
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
		
		while (askRawData.size() < 30){
			askRawData.add(findNextValue(index));
			index = pageData.indexOf("<td>", index);
		}
	}
	
	private void getLastTradePrice(){
		
		index = pageData.indexOf("Last trade");
		index = pageData.indexOf("<strong>", index);
		lastTradePrice = findNextValue(index);
	}
	
	private void parseRawData(){
		
		int i = 0;
				
		while (askList.size() < 10){
		
			askList.add(new PriceAmount(askRawData.get(i), askRawData.get(i + 1)));
			bidList.add(new PriceAmount(bidRawData.get(i), bidRawData.get(i + 1)));
			i = i + 3;
		}
		
	}
	
	private void getBidData(){
		
		index = pageData.indexOf("table-orders bids");
		index = pageData.indexOf("<td>", index);
		bidRawData.clear();
		
		while (bidRawData.size() < 30){
			bidRawData.add(findNextValue(index));
			index = pageData.indexOf("<td>", index);
		}
	}

	public void refresh(){
		
		pageData = null;
		pageData = "";
		
		connectAndGet();
		
		if (pageData.length() > 0){
			
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
			getLastTradePrice();
			parseRawData();
			data = new WebOrderBookData(askList, bidList, lastTradePrice);
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
