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

public class TickersDeserializer extends JsonDeserializer<Tickers> {
	private static final TypeReference<Map<String, Ticker>> TYPE_REFERENCE = new TypeReference<Map<String, Ticker>>() {};
	
	@Override
	public Tickers deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
		final Map<String, Ticker> map = jp.readValueAs(TYPE_REFERENCE);
		List<Ticker> tickers = new ArrayList<>();
		for(Map.Entry<String, Ticker> entry : map.entrySet()) {
            Ticker ticker = entry.getValue();
            ticker.setName(entry.getKey());
            tickers.add(ticker);
        }
		return new Tickers(tickers);
	}

}
