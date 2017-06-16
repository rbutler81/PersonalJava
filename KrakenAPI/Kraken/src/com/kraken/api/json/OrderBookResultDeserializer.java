package com.kraken.api.json;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

public class OrderBookResultDeserializer extends JsonDeserializer<OrderBookResult> {
	
	private static final TypeReference<Map<String, AskBidArray>> TYPE_REFERENCE = new TypeReference<Map<String, AskBidArray>>() {};
	
	@Override
	public OrderBookResult deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
		final Map<String, AskBidArray> map = jp.readValueAs(TYPE_REFERENCE);
		String pair = "";
		List<AskBidEntry> asks = new ArrayList<AskBidEntry>();
		List<AskBidEntry> bids = new ArrayList<AskBidEntry>();
		for (Map.Entry<String, AskBidArray> entry : map.entrySet()){
			pair = entry.getKey();
			AskBidArray ab = entry.getValue();
			List<ArrayList<Object>> askArray = ab.getAsks();
			List<ArrayList<Object>> bidArray = ab.getBids();
			
			for (int i = 0; i < askArray.size(); i++){
				AskBidEntry x = new AskBidEntry();
				x.setPrice((String) askArray.get(i).get(0));
				x.setVolume((String) askArray.get(i).get(1));
				x.setTime((long)(int) askArray.get(i).get(2));
				asks.add(x);
			}
			
			for (int i = 0; i < bidArray.size(); i++){
				AskBidEntry x = new AskBidEntry();
				x.setPrice((String) bidArray.get(i).get(0));
				x.setVolume((String) bidArray.get(i).get(1));
				x.setTime((long)(int) bidArray.get(i).get(2));
				bids.add(x);
			}
			
			
		}
		
		return new OrderBookResult(pair, asks, bids);
	}

}
