package com.kraken.api.json;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

public class OpenOrderArrayDeserializer extends JsonDeserializer<OpenOrderArray> {

private static final TypeReference<Map<String, Map<String, OpenOrder>>> TYPE_REFERENCE = new TypeReference<Map<String, Map<String, OpenOrder>>>() {};
	
	@Override
	public OpenOrderArray deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
		
		Map<String, Map<String, OpenOrder>> map = jp.readValueAs(TYPE_REFERENCE);
		Map<String, OpenOrder> returnData = new HashMap<String, OpenOrder>();
				
		for (Entry<String, Map<String, OpenOrder>> entry : map.entrySet()){
				
			Map<String, OpenOrder> m = entry.getValue();
			
			if (entry.getKey() == "open"){
				
				for (Entry<String, OpenOrder> entry2 : m.entrySet()){
					
					returnData.put(entry2.getKey(), entry2.getValue());
				}
			}
		}
		
		return new OpenOrderArray(returnData);
		
	}

}
