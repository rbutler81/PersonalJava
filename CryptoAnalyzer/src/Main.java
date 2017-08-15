import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import helpers.BigDec;
import helpers.csvUtil.CSVUtil;
import helpers.econ.currency.BackCalcParam;
import helpers.econ.currency.Coin;
import helpers.econ.currency.CoinType;
import helpers.econ.currency.Exchange;
import helpers.http.connection.Connect;
import helpers.math.DataPoint;

public class Main {

	static ObjectMapper mapper = new ObjectMapper();
	
	public static Predicate<DataPoint> buy() {
		return p -> p.getAux().containsKey(1) && BigDec.LE(p.getAux().get(1), BigDec.valueOf(-10.00, 2));
	}
	
	public static Predicate<DataPoint> sell() {
		return p -> p.getAux().containsKey(1) && BigDec.GE(p.getAux().get(1), BigDec.valueOf(10.00, 2));
	}
	
	public static void main(String[] args) throws JsonParseException, JsonMappingException, IOException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException {
		
		DatePriceList dpl = mapper.readValue(Connect.httpsGet("etherchain.org/api/statistics/price", true), DatePriceList.class);
		
		List<DataPoint> dataList = new ArrayList<DataPoint>();
		for (DatePrice e : dpl.getData()) {
			dataList.add(new DataPoint(e.getDateTime(), e.getPrice()));
		}
		
		dataList = DataPoint.removeDeltasLT(dataList, 0.5, 1);
		DataPoint.calcDerivatives(dataList, 3);
		
		for (int i = 1; i < dataList.size(); i++) {
			dataList.get(i).getAux().put(1, BigDec
					.percentDiff(dataList.get(i - 1).getDataPoint(), dataList.get(i).getDataPoint()));
		}
		
		for (int i = 24; i < dataList.size(); i++) {
			dataList.get(i).getAux().put(2, BigDec
					.percentDiff(dataList.get(i - 24).getDataPoint(), dataList.get(i).getDataPoint()));
		}
		
		CSVUtil.writeObject(dataList, "csvFiles/test.csv", ",");
		
		Double initVal = 1000.00;
		Exchange ex = new Exchange(new Coin(CoinType.CAD, initVal), new Coin(CoinType.ETH, 0.0));
		BackCalcParam param = new BackCalcParam();
		param.setBuy(buy());
		param.setSell(sell());
		ex.backTest(dataList, param);
		
		System.out.println();
		System.out.println("Buy and hold: " + BigDec.valueOf(initVal, 2).divide(dataList.get(0).getDataPoint(), 8, RoundingMode.DOWN));
		System.out.println("After trades: " + ex.getPrimary().getValue().toPlainString() + " " + ex.getSecondary().getValue().toPlainString());
	}

}
