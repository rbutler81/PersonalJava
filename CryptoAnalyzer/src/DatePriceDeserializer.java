import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

public class DatePriceDeserializer extends JsonDeserializer<DatePriceList>{

	@Override
	public DatePriceList deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
		
		DatePriceList r = new DatePriceList();
		
		while (jp.getCurrentToken() != JsonToken.START_ARRAY) jp.nextToken();
		
		while (jp.getCurrentToken() != JsonToken.END_ARRAY) {
			
			if (jp.getCurrentToken() == JsonToken.START_OBJECT) {
				
				DatePrice i = new DatePrice();
				
				while (jp.getCurrentToken() != JsonToken.END_OBJECT) {
					
					if (jp.currentToken() == JsonToken.FIELD_NAME) {
						
						if (jp.getCurrentName().equals("time")) { jp.nextToken(); i.setDateTime(jp.getValueAsString()); }
						else if (jp.getCurrentName().equals("usd")) { jp.nextToken(); i.setPrice(Double.toString(jp.getValueAsDouble())); }
					}
					jp.nextToken();
				}
				r.getData().add(i);
			}
			jp.nextToken();
		}
		return r;
	}

}
