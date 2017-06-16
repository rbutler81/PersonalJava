package com.kraken.api.json;

import java.io.IOException;
import java.util.Map;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

public class OpenOrderDeserializer extends JsonDeserializer<OpenOrder>{

	private static final TypeReference<Map<String, Object>> TYPE_REFERENCE = new TypeReference<Map<String, Object>>() {};
	
	@Override
	public OpenOrder deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
		
		Map<String, Object> map = jp.readValueAs(TYPE_REFERENCE);
		OpenOrder r = new OpenOrder();
		
		r.setRefId((String) map.get("refid"));		
		r.setUserRef((String) map.get("userref"));
		r.setStatus((String) map.get("status"));
		r.setOpenTm((double) map.get("opentm"));
		r.setStartTm((double)(int) map.get("starttm"));
		r.setExpireTm((double)(int) map.get("expiretm"));
		r.setVol((String) map.get("vol"));
		r.setVol_exec((String) map.get("vol_exec"));
		r.setCost((String) map.get("cost"));
		r.setFee((String) map.get("fee"));
		r.setPrice((String) map.get("price"));
		
		@SuppressWarnings("unchecked")
		Map<String, String> d = (Map<String, String>) map.get("descr");
		OpenOrderDescription ood = new OpenOrderDescription();
		ood.setPair(d.get("pair"));
		ood.setType(d.get("type"));
		ood.setOrderType(d.get("ordertype"));
		ood.setPrice(d.get("price"));
		ood.setPrice2(d.get("price2"));
		ood.setLeverage(d.get("leverage"));
		ood.setOrder(d.get("order"));
		r.setDescr(ood);
		
		return new OpenOrder(r.getRefId(), r.getUserRef(), r.getStatus(), r.getOpenTm(), r.getStartTm(), r.getExpireTm(), r.getDescr(), r.getVol(),
					r.getVol_exec(), r.getCost(), r.getFee(), r.getPrice());
	}

}
