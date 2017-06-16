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

public class AssetPairsDeserializer extends JsonDeserializer<AssetPairs> {
	private static final TypeReference<Map<String, AssetPair>> TYPE_REFERENCE = new TypeReference<Map<String, AssetPair>>() {};


	@Override
	public AssetPairs deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
		final Map<String, AssetPair> map = jp.readValueAs(TYPE_REFERENCE);
		List<AssetPair> assetPairs = new ArrayList<>();
		for(Map.Entry<String, AssetPair> entry : map.entrySet()) {
            AssetPair assetPair = entry.getValue();
            assetPair.setName(entry.getKey());
            assetPairs.add(assetPair);
        }
		return new AssetPairs(assetPairs);
	}
}