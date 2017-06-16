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

public class SpreadDataResultDeserializer extends JsonDeserializer<SpreadDataResult> {

	private static final TypeReference<Map<String, Object>> TYPE_REFERENCE = new TypeReference<Map<String, Object>>() {};
	
	@SuppressWarnings("unchecked")
	@Override
	public SpreadDataResult deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
		final Map<String, Object> map = jp.readValueAs(TYPE_REFERENCE);
		String pair = "";
		long last = 0;
		List<SpreadData> data = new ArrayList<SpreadData>();
		List<ArrayList<Object>> i = new ArrayList<ArrayList<Object>>();
		for (Map.Entry<String, Object> entry : map.entrySet()){
			if (entry.getKey() == "last"){
				last = (long)(int) entry.getValue();
			}
			else{
				i = (List<ArrayList<Object>>) entry.getValue();
				pair = entry.getKey();
				for (int j = 0; j < i.size(); j++){
					SpreadData x = new SpreadData();
					x.setTime((long)(int)i.get(j).get(0));
					x.setBid((String)i.get(j).get(1));
					x.setAsk((String)i.get(j).get(2));
					data.add(x);
				}
			}
		}
		
		return new SpreadDataResult(last, data, pair);
	}

}
