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

public class AssetsDeserializer extends JsonDeserializer<Assets> {
	private static final TypeReference<Map<String, Asset>> TYPE_REFERENCE = new TypeReference<Map<String, Asset>>() {};

	@Override
	public Assets deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
		final Map<String, Asset> map = jp.readValueAs(TYPE_REFERENCE);
		List<Asset> assets = new ArrayList<>();
		for(Map.Entry<String, Asset> entry : map.entrySet()) {
            Asset asset = entry.getValue();
            asset.setName(entry.getKey());
            assets.add(asset);
        }
		return new Assets(assets);
	}

}
