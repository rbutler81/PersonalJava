import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
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
import helpers.evoAlg.EvoCompare;
import helpers.evoAlg.EvoGene;
import helpers.evoAlg.EvoParamSet;
import helpers.evoAlg.EvoTest;
import helpers.evoAlg.EvoValue;
import helpers.http.connection.Connect;
import helpers.math.Chance;
import helpers.math.DataPoint;

public class Main {

	static ObjectMapper mapper = new ObjectMapper();
	
	public Predicate<DataPoint> buy = p -> p.getAux().containsKey(1) && BigDec.LE(p.getAux().get(1), BigDec.valueOf(-2.00, 2));
	
	public Predicate<DataPoint> sell = p -> p.getAux().containsKey(1) && BigDec.GE(p.getAux().get(1), BigDec.valueOf(2.0, 2));
		
	static Predicate<EvoValue> validPercent = p -> !BigDec.EQ(p.valueAsBigDec(), BigDec.zero()) 
			&& BigDec.GE(p.valueAsBigDec(), BigDec.valueOf(-100)) && BigDec.LE(p.valueAsBigDec(), BigDec.valueOf(100));
	
	
	public static void main(String[] args) throws JsonParseException, JsonMappingException, IOException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException {
		
		
		EvoParamSet temp = new EvoParamSet(5);
		temp.getParam(0).getValue().setPredicate(validPercent);
		temp.getParam(1).getValue().setPredicate(validPercent);
		temp.getParam(2).getValue().setPredicate(validPercent);
		temp.getParam(3).getValue().setPredicate(validPercent);
		temp.getParam(4).getValue().setPredicate(validPercent);
		
		List<EvoParamSet> population = EvoParamSet.generatePopulationFrom(temp, 100);
		
		System.out.println();
		
		
		/*boolean done = false;
		long count = 0;
		while (!done) {
			EvoTest t = new EvoTest(validPercent);
			EvoTest e = new EvoTest(validPercent);
			EvoTest child = EvoTest.breedAndMutateValidChild(t, e, 0.1);
			System.out.println("Parents " + count + ": " + t.getComparator().getOp() + t.getValue().valueAsBigDec().toPlainString() + " & "
				+ e.getComparator().getOp() + e.getValue().valueAsBigDec().toPlainString()
				+ " Child: " + child.getComparator().getOp() + child.getValue().valueAsBigDec().toPlainString());
			done = !child.getComparator().getOp().equals(e.getComparator().getOp()) && !child.getComparator().getOp().equals(t.getComparator().getOp());
			count++;
		}*/
		
		
		/*while (true) {
			EvoValue one = EvoValue.newValidInstance(validPercent);
			EvoValue two = EvoValue.newValidInstance(validPercent);
			System.out.println("One: " + one.valueAsBigDec().toPlainString() + " " + one.getBits());
			System.out.println("Two: " + two.valueAsBigDec().toPlainString() + " " + two.getBits());
			EvoValue v = EvoValue.breedAndMutateValidChild(one, two, 0.01, validPercent);
			System.out.println("Child: " + v.valueAsBigDec().toPlainString() + " " + v.getBits());
			System.out.println();
		}*/
		
		
		
		/*DatePriceList dpl = mapper.readValue(Connect.httpsGet("etherchain.org/api/statistics/price", true), DatePriceList.class);
		
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
		System.out.println("After trades: " + ex.getPrimary().getValue().toPlainString() + " " + ex.getSecondary().getValue().toPlainString());*/
	}

}
