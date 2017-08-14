import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import helpers.BigDec;
import helpers.csvUtil.CSVUtil;
import helpers.http.connection.Connect;
import helpers.math.DataPoint;

public class Main {

	static ObjectMapper mapper = new ObjectMapper();
	
	public static void main(String[] args) throws JsonParseException, JsonMappingException, IOException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException {
		
		DatePriceList dpl = mapper.readValue(Connect.httpsGet("etherchain.org/api/statistics/price", true), DatePriceList.class);
		
		List<DataPoint> dataList = new ArrayList<DataPoint>();
		for (DatePrice e : dpl.getData()) {
			dataList.add(new DataPoint(e.getDateTime(), e.getPrice()));
		}
		
		dataList = DataPoint.removeDeltasLT(dataList, 0.5, 1);
		
		for (int i = 1; i < dataList.size(); i++) {
			dataList.get(i).getAux().put(1, BigDec
					.percentDiff(dataList.get(i - 1).getDataPoint(), dataList.get(i).getDataPoint()));
		}
		
		for (int i = 24; i < dataList.size(); i++) {
			dataList.get(i).getAux().put(2, BigDec
					.percentDiff(dataList.get(i - 24).getDataPoint(), dataList.get(i).getDataPoint()));
		}
		
		
		
		dataList = DataPoint.removeDeltasLT(dataList, 0.5, 1);
		DataPoint.calcDerivatives(dataList, 3);
		CSVUtil.writeObject(dataList, "csvFiles/dailyPriceWPercent.csv", ",");
	}

}
