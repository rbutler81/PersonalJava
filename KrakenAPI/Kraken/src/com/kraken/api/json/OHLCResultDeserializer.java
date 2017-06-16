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

public class OHLCResultDeserializer extends JsonDeserializer<OHLCResult>{

	private static final TypeReference<Map<String, Object>> TYPE_REFERENCE = new TypeReference<Map<String, Object>>() {};
	
	@SuppressWarnings("unchecked")
	@Override
	public OHLCResult deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
		final Map<String, Object> map = jp.readValueAs(TYPE_REFERENCE);
		String name = "";
		long last = 0;
		List<OHLCData> data = new ArrayList<OHLCData>();
		List<ArrayList<Object>> i = new ArrayList<ArrayList<Object>>();
		for (Map.Entry<String, Object> entry : map.entrySet()){
			if (entry.getKey() == "last"){
				last = (long)(int) entry.getValue();
			}
			else{
				i = (List<ArrayList<Object>>) entry.getValue();
				name = entry.getKey();
				for (int j = 0; j < i.size(); j++){
					OHLCData x = new OHLCData();
					x.setTime((long)(int)i.get(j).get(0));
					x.setOpen((String)i.get(j).get(1));
					x.setHigh((String)i.get(j).get(2));
					x.setLow((String)i.get(j).get(3));
					x.setClose((String)i.get(j).get(4));
					x.setVwap((String)i.get(j).get(5));
					x.setVolume((String)i.get(j).get(6));
					x.setCount((long)(int)i.get(j).get(7));
					data.add(x);
				}
			}
		}
		
		return new OHLCResult(last, data, name);
	}

}
