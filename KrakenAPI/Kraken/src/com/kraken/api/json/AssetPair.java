package com.kraken.api.json;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AssetPair {
	
	private static final String ALTNAME = "altname";
	private static final String ACLASS_BASE = "aclass_base";
	private static final String BASE = "base";
	private static final String ACLASS_QUOTE = "aclass_quote";
	private static final String QUOTE = "quote";
	private static final String LOT = "lot";
	private static final String PAIR_DECIMALS = "pair_decimals";
	private static final String LOT_DECIMALS = "lot_decimals";
	private static final String LOT_MULTIPLIER = "lot_multiplier";
	private static final String LEVERAGE_BUY = "leverage_buy";
	private static final String LEVERAGE_SELL = "leverage_sell";
	private static final String FEES = "fees";
	private static final String FEES_MAKER = "fees_maker";
	private static final String FEE_VOLUME_CURRENCY = "fee_volume_currency";
	private static final String MARGIN_CALL = "margin_call";
	private static final String MARGIN_STOP = "margin_stop";
	
	private String name;
	
	@JsonProperty(ALTNAME)
	private String altname;
	
	@JsonProperty(ACLASS_BASE)
	private String aclass_base;
	
	@JsonProperty(BASE)
	private String base;
	
	@JsonProperty(ACLASS_QUOTE)
	private String aclass_quote;
	
	@JsonProperty(QUOTE)
	private String quote;
	
	@JsonProperty(LOT)
	private String lot;
	
	@JsonProperty(PAIR_DECIMALS)
	private int pair_decimals;
	
	@JsonProperty(LOT_DECIMALS)
	private int lot_decimals;
	
	@JsonProperty(LOT_MULTIPLIER)
	private int lot_multiplier;
	
	@JsonProperty(LEVERAGE_BUY)
	private List<Long> leverage_buy;
	
	@JsonProperty(LEVERAGE_SELL)
	private List<Long> leverage_sell;
	
	@JsonProperty(FEES)
	private List<List<Double>> fees;
	
	@JsonProperty(FEES_MAKER)
	private List<List<Double>> fees_maker;
	
	@JsonProperty(FEE_VOLUME_CURRENCY)
	private String fee_volume_currency;
	
	@JsonProperty(MARGIN_CALL)
	private int margin_call;
	
	@JsonProperty(MARGIN_STOP)
	private int margin_stop;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@JsonProperty(ALTNAME)
	public String getAltname() {
		return altname;
	}
	@JsonProperty(ALTNAME)
	public void setAltname(String altname) {
		this.altname = altname;
	}
	@JsonProperty(ACLASS_BASE)
	public String getAclass_base() {
		return aclass_base;
	}
	@JsonProperty(ACLASS_BASE)
	public void setAclass_base(String aclass_base) {
		this.aclass_base = aclass_base;
	}
	@JsonProperty(BASE)
	public String getBase() {
		return base;
	}
	@JsonProperty(BASE)
	public void setBase(String base) {
		this.base = base;
	}
	@JsonProperty(ACLASS_QUOTE)
	public String getAclass_quote() {
		return aclass_quote;
	}
	@JsonProperty(ACLASS_QUOTE)
	public void setAclass_quote(String aclass_quote) {
		this.aclass_quote = aclass_quote;
	}
	@JsonProperty(QUOTE)
	public String getQuote() {
		return quote;
	}
	@JsonProperty(QUOTE)
	public void setQuote(String quote) {
		this.quote = quote;
	}
	@JsonProperty(LOT)
	public String getLot() {
		return lot;
	}
	@JsonProperty(LOT)
	public void setLot(String lot) {
		this.lot = lot;
	}
	@JsonProperty(PAIR_DECIMALS)
	public int getPair_decimals() {
		return pair_decimals;
	}
	@JsonProperty(PAIR_DECIMALS)
	public void setPair_decimals(int pair_decimals) {
		this.pair_decimals = pair_decimals;
	}
	@JsonProperty(LOT_DECIMALS)
	public int getLot_decimals() {
		return lot_decimals;
	}
	@JsonProperty(LOT_DECIMALS)
	public void setLot_decimals(int lot_decimals) {
		this.lot_decimals = lot_decimals;
	}
	@JsonProperty(LOT_MULTIPLIER)
	public int getLot_multiplier() {
		return lot_multiplier;
	}
	@JsonProperty(LOT_MULTIPLIER)
	public void setLot_multiplier(int lot_multiplier) {
		this.lot_multiplier = lot_multiplier;
	}
	@JsonProperty(LEVERAGE_BUY)
	public List<Long> getLeverage_buy() {
		return leverage_buy;
	}
	@JsonProperty(LEVERAGE_BUY)
	public void setLeverage_buy(List<Long> leverage_buy) {
		this.leverage_buy = leverage_buy;
	}
	@JsonProperty(LEVERAGE_SELL)
	public List<Long> getLeverage_sell() {
		return leverage_sell;
	}
	@JsonProperty(LEVERAGE_SELL)
	public void setLeverage_sell(List<Long> leverage_sell) {
		this.leverage_sell = leverage_sell;
	}
	@JsonProperty(FEES)
	public List<List<Double>> getFees() {
		return fees;
	}
	@JsonProperty(FEES)
	public void setFees(List<List<Double>> fees) {
		this.fees = fees;
	}
	@JsonProperty(FEES_MAKER)
	public List<List<Double>> getFees_maker() {
		return fees_maker;
	}
	@JsonProperty(FEES_MAKER)
	public void setFees_maker(List<List<Double>> fees_maker) {
		this.fees_maker = fees_maker;
	}
	@JsonProperty(FEE_VOLUME_CURRENCY)
	public String getFee_volume_currency() {
		return fee_volume_currency;
	}
	@JsonProperty(FEE_VOLUME_CURRENCY)
	public void setFee_volume_currency(String fee_volume_currency) {
		this.fee_volume_currency = fee_volume_currency;
	}
	@JsonProperty(MARGIN_CALL)
	public int getMargin_call() {
		return margin_call;
	}
	@JsonProperty(MARGIN_CALL)
	public void setMargin_call(int margin_call) {
		this.margin_call = margin_call;
	}
	@JsonProperty(MARGIN_STOP)
	public int getMargin_stop() {
		return margin_stop;
	}
	@JsonProperty(MARGIN_STOP)
	public void setMargin_stop(int margin_stop) {
		this.margin_stop = margin_stop;
	}

	
}
