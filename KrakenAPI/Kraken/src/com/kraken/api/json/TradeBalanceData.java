package com.kraken.api.json;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TradeBalanceData {

	private static final String EB = "eb";
	private static final String TB = "tb";
	private static final String M = "m";
	private static final String N = "n";
	private static final String C = "c";
	private static final String V = "v";
	private static final String E = "e";
	private static final String MF = "mf";
	private static final String ML = "ml";
	
	@JsonProperty(EB) private String equivalentBalance;
	@JsonProperty(TB) private String tradeBalance;
	@JsonProperty(M) private String marginAmountOpen;
	@JsonProperty(N) private String unrealizedNetProfitLoss;
	@JsonProperty(C) private String costBasisOpen;
	@JsonProperty(V) private String valuationOfOpenPosition;
	@JsonProperty(E) private String equity;
	@JsonProperty(MF) private String freeMargin;
	@JsonProperty(ML) private String marginLevel;
	
	public TradeBalanceData() {
		this.equivalentBalance = "";
		this.tradeBalance = "";
		this.marginAmountOpen = "";
		this.unrealizedNetProfitLoss = "";
		this.costBasisOpen = "";
		this.valuationOfOpenPosition = "";
		this.equity = "";
		this.freeMargin = "";
		this.marginLevel = "";
	}
	
	public TradeBalanceData(String equivalentBalance, String tradeBalance, String marginAmountOpen,
			String unrealizedNetProfitLoss, String costBasisOpen, String valuationOfOpenPosition, String equity,
			String freeMargin, String marginLevel) {
		this.equivalentBalance = equivalentBalance;
		this.tradeBalance = tradeBalance;
		this.marginAmountOpen = marginAmountOpen;
		this.unrealizedNetProfitLoss = unrealizedNetProfitLoss;
		this.costBasisOpen = costBasisOpen;
		this.valuationOfOpenPosition = valuationOfOpenPosition;
		this.equity = equity;
		this.freeMargin = freeMargin;
		this.marginLevel = marginLevel;
	}
	
	@JsonProperty(EB)
	public String getEquivalentBalance() {
		return equivalentBalance;
	}
	@JsonProperty(EB)
	public void setEquivalentBalance(String equivalentBalance) {
		this.equivalentBalance = equivalentBalance;
	}
	
	@JsonProperty(TB)
	public String getTradeBalance() {
		return tradeBalance;
	}
	@JsonProperty(TB)
	public void setTradeBalance(String tradeBalance) {
		this.tradeBalance = tradeBalance;
	}

	@JsonProperty(M)
	public String getMarginAmountOpen() {
		return marginAmountOpen;
	}
	@JsonProperty(M)
	public void setMarginAmountOpen(String marginAmountOpen) {
		this.marginAmountOpen = marginAmountOpen;
	}
	
	@JsonProperty(N)
	public String getUnrealizedNetProfitLoss() {
		return unrealizedNetProfitLoss;
	}
	@JsonProperty(N)
	public void setUnrealizedNetProfitLoss(String unrealizedNetProfitLoss) {
		this.unrealizedNetProfitLoss = unrealizedNetProfitLoss;
	}
	
	@JsonProperty(C)
	public String getCostBasisOpen() {
		return costBasisOpen;
	}
	@JsonProperty(C)
	public void setCostBasisOpen(String costBasisOpen) {
		this.costBasisOpen = costBasisOpen;
	}
	
	@JsonProperty(V)
	public String getValuationOfOpenPosition() {
		return valuationOfOpenPosition;
	}
	@JsonProperty(V)
	public void setValuationOfOpenPosition(String valuationOfOpenPosition) {
		this.valuationOfOpenPosition = valuationOfOpenPosition;
	}

	@JsonProperty(E)
	public String getEquity() {
		return equity;
	}
	@JsonProperty(E)
	public void setEquity(String equity) {
		this.equity = equity;
	}

	@JsonProperty(MF)
	public String getFreeMargin() {
		return freeMargin;
	}
	
	@JsonProperty(MF)
	public void setFreeMargin(String freeMargin) {
		this.freeMargin = freeMargin;
	}

	@JsonProperty(ML)
	public String getMarginLevel() {
		return marginLevel;
	}
	@JsonProperty(ML)
	public void setMarginLevel(String marginLevel) {
		this.marginLevel = marginLevel;
	}
	
}
