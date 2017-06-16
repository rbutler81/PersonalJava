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

public class RecentTradeResultDeserializer extends JsonDeserializer<RecentTradeResult>{

	private static final TypeReference<Map<String, Object>> TYPE_REFERENCE = new TypeReference<Map<String, Object>>() {};
	
	@SuppressWarnings("unchecked")
	@Override
	public RecentTradeResult deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
		final Map<String, Object> map = jp.readValueAs(TYPE_REFERENCE);
		String pair = "";
		String last = "";
		List<RecentTradeData> data = new ArrayList<RecentTradeData>();
		List<ArrayList<Object>> i = new ArrayList<ArrayList<Object>>();
		for (Map.Entry<String, Object> entry : map.entrySet()){
			if (entry.getKey() == "last"){
				last = (String) entry.getValue();
			}
			else{
				i = (List<ArrayList<Object>>) entry.getValue();
				pair = entry.getKey();
				for (int j = 0; j < i.size(); j++){
					RecentTradeData x = new RecentTradeData();
					x.setPrice((String)i.get(j).get(0));
					x.setVolume((String)i.get(j).get(1));
					x.setTime((double)i.get(j).get(2));
					x.setBuySell((String)i.get(j).get(3));
					x.setMarketLimit((String)i.get(j).get(4));
					x.setMisc((String)i.get(j).get(5));
					data.add(x);
				}
			}
		}
		
		return new RecentTradeResult(last, data, pair);
	}

}
