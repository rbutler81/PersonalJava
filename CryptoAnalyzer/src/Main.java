import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import helpers.csvUtil.CSVUtil;
import helpers.http.connection.Connect;

public class Main {

	static ObjectMapper mapper = new ObjectMapper();
	
	public static void main(String[] args) throws JsonParseException, JsonMappingException, IOException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException {
		
		DatePriceList dpl = mapper.readValue(Connect.httpsGet("etherchain.org/api/statistics/price", true), DatePriceList.class);
		CSVUtil.writeObject(dpl.getData(), "csvFiles/dateprice.csv", ",");
		
	}

}
