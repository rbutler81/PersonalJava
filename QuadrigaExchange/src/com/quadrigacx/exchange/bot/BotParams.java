package com.quadrigacx.exchange.bot;

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.quadrigacx.api.returnJson.helpers.GetCoinType;
import com.quadrigacx.exchange.threads.ThreadControl;

import helpers.econ.currency.CoinType;

public class BotParams {

	private ThreadControl tc = new ThreadControl();
	
	private String minDesiredProfit = "";
	private String dontBuyPast = "";
	private String amountToTrade = "";
	private String amountToUse = "";
	private String amountToUseOriginal = "";
	private String book;
	private String fractionToSell = "";
	private String daysToLast = "";
	private boolean autoAmount = false;
	private CoinType major;
	private CoinType minor;
	private boolean raisedSellLimit = false;
	
	public BotParams(String book){
		this.book = book;
		major = GetCoinType.getMajor(book);
		minor = GetCoinType.getMinor(book);
	}
	
	
	public String getFractionToSell() {
		return fractionToSell;
	}


	public void setFractionToSell(String fractionToSell) {
		this.fractionToSell = fractionToSell;
	}


	public String getDaysToLast() {
		return daysToLast;
	}


	public void setDaysToLast(String daysToLast) {
		this.daysToLast = daysToLast;
	}


	public boolean isAutoAmount() {
		return autoAmount;
	}


	public void setAutoAmount(boolean autoAmount) {
		this.autoAmount = autoAmount;
	}


	public String getAmountToUseOriginal() {
		return amountToUseOriginal;
	}

	public void setAmountToUseOriginal(String amountToUseOriginal) {
		this.amountToUseOriginal = amountToUseOriginal;
	}

	public boolean isRaisedSellLimit() {
		return raisedSellLimit;
	}

	public void setRaisedSellLimit(boolean raisedSellLimit) {
		this.raisedSellLimit = raisedSellLimit;
	}

	public BigDecimal getAmountToUseAsBigDec() {
		return new BigDecimal(getAmountToUse()).setScale(major.getDecimalPlaces(), RoundingMode.DOWN);
	}
	
	public String getAmountToUse() {
		return amountToUse;
	}


	public CoinType getMajor() {
		return major;
	}

	public void setMajor(CoinType major) {
		this.major = major;
	}

	public CoinType getMinor() {
		return minor;
	}

	public void setMinor(CoinType minor) {
		this.minor = minor;
	}

	public String getBook() {
		return book;
	}

	public void setBook(String book) {
		this.book = book;
	}

	public void setAmountToUse(String amountToUse) {
		this.amountToUse = amountToUse;
	}


	public BigDecimal getAmountToTradeAsBigDec(){
		return new BigDecimal(amountToTrade).setScale(major.getDecimalPlaces(), RoundingMode.DOWN);
	}
	
	public String getAmountToTrade() {
		return amountToTrade;
	}

	public void setAmountToTrade(String amountToTrade) {
		this.amountToTrade = amountToTrade;
	}

	public String getDontBuyPast() {
		return dontBuyPast;
	}

	public void setDontBuyPast(String dontBuyPast) {
		this.dontBuyPast = dontBuyPast;
	}
	
	public BigDecimal getDontBuyPastBigDec(){
		return new BigDecimal(dontBuyPast).setScale(minor.getDecimalPlaces(), RoundingMode.DOWN);
	}

	public String getMinDesiredProfit() {
		return minDesiredProfit;
	}

	public void setMinDesiredProfit(String minDesiredProfit) {
		this.minDesiredProfit = minDesiredProfit;
	}

	public ThreadControl getTc() {
		return tc;
	}

	public void setTc(ThreadControl tc) {
		this.tc = tc;
	}
	
}
