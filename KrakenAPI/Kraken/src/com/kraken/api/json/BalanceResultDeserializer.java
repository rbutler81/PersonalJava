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

public class BalanceResultDeserializer extends JsonDeserializer<BalanceResult> {

	private static final TypeReference<Map<String, String>> TYPE_REFERENCE = new TypeReference<Map<String, String>>() {};

	@Override
	public BalanceResult deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
		final Map<String, String> map = jp.readValueAs(TYPE_REFERENCE);
		List<AssetBalance> data = new ArrayList<AssetBalance>();
		for (Map.Entry<String, String> entry : map.entrySet()){
			AssetBalance x = new AssetBalance();
			x.setAsset(entry.getKey());
			x.setBalance(entry.getValue());
			data.add(x);
			}
		return new BalanceResult(data);
	}
	
	
}
