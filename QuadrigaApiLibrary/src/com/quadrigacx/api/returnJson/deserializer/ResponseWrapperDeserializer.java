package com.quadrigacx.api.returnJson.deserializer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.quadrigacx.api.QuadrigaMethods;
import com.quadrigacx.api.returnJson.BalanceResponse;
import com.quadrigacx.api.returnJson.ErrorResponse;
import com.quadrigacx.api.returnJson.OpenOrdersResponse;
import com.quadrigacx.api.returnJson.OrderBookResponse;
import com.quadrigacx.api.returnJson.OrderLookupResponse;
import com.quadrigacx.api.returnJson.OrderResponse;
import com.quadrigacx.api.returnJson.ResponseWrapper;
import com.quadrigacx.api.returnJson.TickerResponse;
import com.quadrigacx.api.returnJson.TransactionsResponse;
import com.quadrigacx.api.returnJson.UserTransactionsResponse;
import com.quadrigacx.api.returnJson.helpers.Balance;
import com.quadrigacx.api.returnJson.helpers.BidAsk;
import com.quadrigacx.api.returnJson.helpers.DepositWithdrawal;
import com.quadrigacx.api.returnJson.helpers.Order;
import com.quadrigacx.api.returnJson.helpers.OrderResult;
import com.quadrigacx.api.returnJson.helpers.Trade;
import com.quadrigacx.api.returnJson.helpers.Transaction;

public class ResponseWrapperDeserializer extends JsonDeserializer<ResponseWrapper>{

	private static final TypeReference<Map<String, Object>> MAP_STRING_OBJECT = new TypeReference<Map<String, Object>>() {};
	private static final TypeReference<Map<String, String>> MAP_STRING_STRING = new TypeReference<Map<String, String>>() {};
	
	@SuppressWarnings("unchecked")
	@Override
	public ResponseWrapper deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
		
		int context = (int)ctxt.findInjectableValue("context", null, null);
		ResponseWrapper r = new ResponseWrapper();
				
		//Order Canceled
		if ((context == QuadrigaMethods.CancelOrder.getId()) && (jp.getCurrentToken() == JsonToken.VALUE_STRING)){
		
			String result = jp.getValueAsString();
			if (result.contentEquals("true")){
				r.setOrderCanceled(true);
				return r;
			}
		}
		
		if (jp.getCurrentToken() == JsonToken.START_OBJECT){
			
			if (jp.nextToken() == JsonToken.FIELD_NAME){
				
				//Error Response
				if (jp.getCurrentName() == "error"){    		
					
					final Map<String, Object> map = jp.readValueAs(MAP_STRING_OBJECT);
					ErrorResponse e = new ErrorResponse();
					
					Map<String, Object> errMap = (HashMap<String, Object>) map.get("error");
					e.setErrCode((int) errMap.get("code"));
					e.setErrMessage((String) errMap.get("message"));
					r.setErrorResponse(e);
					
					return r;
				}
				
				//Ticker Response
				else if (context == QuadrigaMethods.Ticker.getId()){
				
					final Map<String, Object> map = jp.readValueAs(MAP_STRING_OBJECT);
					TickerResponse t = new TickerResponse();
					
					t.setHigh((String) map.get("high"));
					t.setLow((String) map.get("low"));
					t.setVolume((String) map.get("volume"));
					t.setVwap((String) map.get("vwap"));
					t.setAsk((String) map.get("ask"));
					t.setBid((String) map.get("bid"));
					t.setLast((String) map.get("last"));
					t.setTimeStamp((String) map.get("timestamp"));
					r.setTickerResponse(t);
					
					return r;
				}
				
				//Order Book Response
				else if (context == QuadrigaMethods.OrderBook.getId()){
					
					final Map<String, Object> map = jp.readValueAs(MAP_STRING_OBJECT);
					OrderBookResponse o = new OrderBookResponse();
					
					o.setTimeStamp((String)map.get("timestamp"));
					
					List<BidAsk> bids = new ArrayList<BidAsk>();
					List<BidAsk> asks = new ArrayList<BidAsk>();
					
					List<List<String>> b = (List<List<String>>) map.get("bids");
					List<List<String>> a = (List<List<String>>) map.get("asks");
					
					for (int i=0; i < b.size(); i++){
						
						BidAsk x = new BidAsk();
						x.setPrice(b.get(i).get(0));
						x.setAmount(b.get(i).get(1));
						bids.add(x);
					}
					o.setBids(bids);
					
					for (int i=0; i < a.size(); i++){
						
						BidAsk x = new BidAsk();
						x.setPrice(a.get(i).get(0));
						x.setAmount(a.get(i).get(1));
						asks.add(x);
					}
					o.setAsks(asks);
					
					r.setOrderBookResponse(o);
					
					return r;
				}
			
				//Balance Response
				else if (context == QuadrigaMethods.Balance.getId()){
				
					final Map<String, Object> map = jp.readValueAs(MAP_STRING_OBJECT);
					
					BalanceResponse br = new BalanceResponse();
					Balance btc = new Balance();
					Balance eth = new Balance();
					Balance cad = new Balance();
					Balance usd = new Balance();
					
					btc.setAvailable((String) map.get("btc_available"));
					btc.setReserved((String) map.get("btc_reserved"));
					btc.setBalance((String) map.get("btc_balance"));
					eth.setAvailable((String) map.get("eth_available"));
					eth.setReserved((String) map.get("eth_reserved"));
					eth.setBalance((String) map.get("eth_balance"));
					cad.setAvailable((String) map.get("cad_available"));
					cad.setReserved((String) map.get("cad_reserved"));
					cad.setBalance((String) map.get("cad_balance"));
					usd.setAvailable((String) map.get("usd_available"));
					usd.setReserved((String) map.get("usd_reserved"));
					usd.setBalance((String) map.get("usd_balance"));
					
					br.setBtc(btc);
					br.setCad(cad);
					br.setEth(eth);
					br.setUsd(usd);
					
					r.setBalanceResponse(br);
					
					return r;
				}
			
				//LimitOrder Response
				else if ((context == QuadrigaMethods.SellLimitOrder.getId()) || (context == QuadrigaMethods.BuyLimitOrder.getId()) ||
						(context == QuadrigaMethods.SellMarketOrder.getId()) || (context == QuadrigaMethods.BuyMarketOrder.getId())){
					
					final Map<String, String> map = jp.readValueAs(MAP_STRING_STRING);
					OrderResponse lor = new OrderResponse();
					OrderResult lo = new OrderResult();
					
					lo.setAmount(map.get("amount"));
					lo.setPrice(map.get("price"));
					lo.setBook(map.get("book"));
					lo.setDate(map.get("datetime"));
					lo.setId(map.get("id"));
					lo.setStatus(map.get("status"));
					lo.setType(map.get("type"));
					
					if ((context == QuadrigaMethods.SellLimitOrder.getId()) || (context == QuadrigaMethods.SellMarketOrder.getId())) lor.setSell(lo);
					else if ((context == QuadrigaMethods.BuyLimitOrder.getId()) || (context == QuadrigaMethods.BuyMarketOrder.getId())) lor.setBuy(lo);
					
					r.setOrderResponse(lor);
					return r;
				}
				
			}
		}
		else if (jp.getCurrentToken() == JsonToken.START_ARRAY){
		
			if (jp.nextToken() == JsonToken.START_OBJECT){
				
				if (jp.nextToken() == JsonToken.FIELD_NAME){
					
					//Transactions Response
					if (context == QuadrigaMethods.Transactions.getId()){
						
						TransactionsResponse tr = new TransactionsResponse();
						Transaction t = new Transaction();
						List<Transaction> lt = new ArrayList<Transaction>();
						
						while (jp.getCurrentToken() != JsonToken.END_ARRAY){
							
							if (jp.getCurrentToken() == JsonToken.FIELD_NAME){
							
								if (jp.getCurrentName() == "amount"){
									jp.nextToken();
									t.setAmount(jp.getValueAsString());
								}
								else if (jp.getCurrentName() == "date"){
									jp.nextToken();
									t.setDate(jp.getValueAsString());
								}
								else if (jp.getCurrentName() == "price"){
									jp.nextToken();
									t.setPrice(jp.getValueAsString());
								}
								else if (jp.getCurrentName() == "tid"){
									jp.nextToken();
									t.settId(jp.getValueAsInt());
								}
								else if (jp.getCurrentName() == "side"){
									jp.nextToken();
									t.setSide(jp.getValueAsString());
								}
							}
							else if (jp.getCurrentToken() == JsonToken.START_OBJECT){
								t = new Transaction();
							}
							else if (jp.getCurrentToken() == JsonToken.END_OBJECT){
								lt.add(t);
							}
							
							jp.nextToken();	
						}
						
						tr.setTransactions(lt);
						r.setTransactionsResponse(tr);
						
						return r;
						
					}
				
					//UserTransactions Response
					else if (context == QuadrigaMethods.UserTransactions.getId()){
					
						UserTransactionsResponse ut = new UserTransactionsResponse();
						List<Trade> lt = new ArrayList<Trade>();
						List<DepositWithdrawal> ld = new ArrayList<DepositWithdrawal>();
						List<DepositWithdrawal> lw = new ArrayList<DepositWithdrawal>();
						Map<String, Object> m = new HashMap<String, Object>();
						
						while (jp.getCurrentToken() != JsonToken.END_ARRAY){
							
							m.clear();
							while (jp.getCurrentToken() != JsonToken.END_OBJECT){
								
								if (jp.getCurrentToken() == JsonToken.FIELD_NAME){
									
									if (jp.getCurrentName() == "type"){
										jp.nextToken();
										m.put("type", jp.getValueAsInt());
									}
									else {
										String fieldName = jp.getCurrentName();
										jp.nextToken();
										m.put(fieldName, jp.getValueAsString());
									}
								}
								
								else {
									jp.nextToken();
								}
							}
							
							if ((int)m.get("type") == 2){
								
								Trade t = new Trade();
								String majorMinor = "";
								String minor = "";
								String major = "";
								for (Map.Entry<String, Object> entry : m.entrySet()){
									
									if ((entry.getKey().indexOf('_') > 0) && (entry.getKey().length() == 7)){
										majorMinor = entry.getKey();
										major = majorMinor.substring(0, 3);
										minor = majorMinor.substring(4);
									}
								}
								
								t.setMajorMinor((String)m.get(majorMinor));
								t.setMinor((String)m.get(minor));
								t.setMajor((String)m.get(major));
								t.setDate((String)m.get("datetime"));
								t.setOrderId((String)m.get("order_id"));
								t.setFee((String)m.get("fee"));
								t.setRate((String)m.get("rate"));
								t.setId((String)m.get("id"));
								lt.add(t);
							}
							
							else if ((int)m.get("type") == 0){
							
								DepositWithdrawal d = new DepositWithdrawal();
								String asset = "";
								
								for (Map.Entry<String, Object> entry : m.entrySet()){
									
									if ((entry.getKey().length() == 3) && (entry.getKey() != "fee")){
										asset = entry.getKey();
									}
								}
								
								d.setAsset(asset);
								d.setAmount((String)m.get(asset));
								d.setDate((String)m.get("datetime"));
								d.setMethod((String)m.get("method"));
								d.setFee((String)m.get("fee"));
								ld.add(d);
							}
							
							else if ((int)m.get("type") == 1){
							
								DepositWithdrawal w = new DepositWithdrawal();
								String asset = "";
								
								for (Map.Entry<String, Object> entry : m.entrySet()){
									
									if ((entry.getKey().length() == 3) && (entry.getKey() != "fee")){
										asset = entry.getKey();
									}
								}
								
								w.setAsset(asset);
								w.setAmount((String)m.get(asset));
								w.setDate((String)m.get("datetime"));
								w.setMethod((String)m.get("method"));
								w.setFee((String)m.get("fee"));
								lw.add(w);
								
							}
							
							jp.nextToken();
						}
						
						ut.setTrades(lt);
						ut.setDeposits(ld);
						ut.setWithdrawals(lw);
						r.setUserTransactionsResponse(ut);
						
						return r;
					}
				
					//OpenOrders Response
					else if (context == QuadrigaMethods.OpenOrders.getId()){
					
						OpenOrdersResponse oo = new OpenOrdersResponse();
						List<Order> lo = new ArrayList<Order>();
						
						while (jp.getCurrentToken() != JsonToken.END_ARRAY){
						
							Order o = new Order();
							while (jp.getCurrentToken() != JsonToken.END_OBJECT){
								
								if (jp.getCurrentToken() == JsonToken.FIELD_NAME){
									
									if (jp.getCurrentName() == "amount"){
										jp.nextToken();
										o.setAmount(jp.getValueAsString());
									}
									else if (jp.getCurrentName() == "datetime"){
										jp.nextToken();
										o.setDate(jp.getValueAsString());
									}
									else if (jp.getCurrentName() == "id"){
										jp.nextToken();
										o.setId(jp.getValueAsString());
									}
									else if (jp.getCurrentName() == "price"){
										jp.nextToken();
										o.setPrice(jp.getValueAsString());
									}
									else if (jp.getCurrentName() == "status"){
										jp.nextToken();
										o.setStatus(jp.getValueAsString());
									}
									else if (jp.getCurrentName() == "type"){
										jp.nextToken();
										o.setType(jp.getValueAsString());
									}
								}
								else {
									jp.nextToken();
								}
							}
							
							lo.add(o);
							jp.nextToken();
						}
						
						oo.setOpenOrders(lo);
						r.setOpenOrdersResponse(oo);
						
						return r;
					}
				
					//LookupOrders Response
					else if (context == QuadrigaMethods.LookupOrder.getId()){
						
						OrderLookupResponse ol = new OrderLookupResponse();
												
						while (jp.getCurrentToken() != JsonToken.END_ARRAY){
						
							while (jp.getCurrentToken() != JsonToken.END_OBJECT){
								
								if (jp.getCurrentToken() == JsonToken.FIELD_NAME){
									
									if (jp.getCurrentName() == "amount"){
										jp.nextToken();
										ol.setAmount(jp.getValueAsString());
									}
									else if (jp.getCurrentName() == "created"){
										jp.nextToken();
										ol.setCreated(jp.getValueAsString());
									}
									else if (jp.getCurrentName() == "id"){
										jp.nextToken();
										ol.setId(jp.getValueAsString());
									}
									else if (jp.getCurrentName() == "price"){
										jp.nextToken();
										ol.setPrice(jp.getValueAsString());
									}
									else if (jp.getCurrentName() == "status"){
										jp.nextToken();
										ol.setStatus(jp.getValueAsString());
									}
									else if (jp.getCurrentName() == "type"){
										jp.nextToken();
										ol.setType(jp.getValueAsString());
									}
									else if (jp.getCurrentName() == "book"){
										jp.nextToken();
										ol.setBook(jp.getValueAsString());
									}
									else if (jp.getCurrentName() == "updated"){
										jp.nextToken();
										ol.setUpdated(jp.getValueAsString());
									}
								}
								else {
									jp.nextToken();
								}
							}
							
							jp.nextToken();
						}
						
						r.setOrderLookupResponse(ol);
												
						return r;
					}
				}
			}
		}
		
		return r;
	}

	
}
