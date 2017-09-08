import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import helpers.BigDec;
import helpers.csvUtil.CSVUtil;
import helpers.econ.currency.Coin;
import helpers.econ.currency.CoinType;
import helpers.evoAlg.EvoBuySell;
import helpers.evoAlg.EvoExchange;
import helpers.evoAlg.EvoParamSet;
import helpers.evoAlg.EvoValue;
import helpers.http.connection.Connect;
import helpers.math.Chance;
import helpers.math.DataPoint;

public class Main {

	static ObjectMapper mapper = new ObjectMapper();
	
	static final int POP_SIZE = 100;
	static final int MAX_POPULATION = 1000;
	
	public static Predicate<DataPoint> buy = p -> p.getAux().containsKey(1) && BigDec.LE(p.getAux().get(1), BigDec.valueOf(-2.00, 2));
	
	public static Predicate<DataPoint> sell = p -> p.getAux().containsKey(1) && BigDec.GE(p.getAux().get(1), BigDec.valueOf(2.0, 2));
		
	static Predicate<EvoValue> validPercent = p -> BigDec.GE(p.valueAsBigDec(), BigDec.valueOf(-100)) && BigDec.LE(p.valueAsBigDec(), BigDec.valueOf(100));
	
	
	public static void main(String[] args) throws JsonParseException, JsonMappingException, IOException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException {
		
		
		/////////////////////////////
		DatePriceList dpl = mapper.readValue(Connect.httpsGet("etherchain.org/api/statistics/price", true), DatePriceList.class);
		
		EvoParamSet temp = new EvoParamSet(1);
		/*temp.setPredicate(0, validPercent);
		temp.setPredicate(1, validPercent);*/
		
		List<EvoBuySell> population = EvoBuySell.generatePopulationFrom(temp, POP_SIZE);
		
		List<DataPoint> dataList = new ArrayList<DataPoint>();
		for (DatePrice e : dpl.getData()) {
			dataList.add(new DataPoint(e.getDateTime(), e.getPrice()));
		}
		
		dataList = DataPoint.removeDeltasLT(dataList, 0.5, 1);
		//DataPoint.calcDerivatives(dataList, 3);
		
		for (int i = 1; i < dataList.size(); i++) {
			dataList.get(i).getAux().put(1, BigDec
					.percentDiff(dataList.get(i - 1).getDataPoint(), dataList.get(i).getDataPoint()));
		}
		
		/*for (int i = 24; i < dataList.size(); i++) {
			dataList.get(i).getAux().put(2, BigDec
					.percentDiff(dataList.get(i - 24).getDataPoint(), dataList.get(i).getDataPoint()));
		}*/
		
		for (int i = 0; i < 15012; i++) {
			dataList.remove(0);
		}
		
		CSVUtil.writeObject(dataList, "csvFiles/test.csv", ",");
		
		Double initVal = 1000.00;
		EvoExchange ex = new EvoExchange(new Coin(CoinType.CAD, initVal), new Coin(CoinType.ETH, 0.0));
		
		for (int i = 0; i < 100000; i++) {
		
			System.out.println();
			System.out.println("Running back tests... ");
			for (EvoBuySell ebs : population) {
				if (BigDec.EQ(ebs.getFitness(), BigDec.zero())) {
					ex.backTest(dataList, ebs, initVal);
				}
			}
			
			if (Chance.percent(5) && population.size() > MAX_POPULATION) {
				int size = population.size();
				for (int k = 0; k < size - 1; k++) {
					population.remove(1);
				}
				List<EvoBuySell> regenPop = EvoBuySell.generatePopulationFrom(temp, POP_SIZE - population.size());
				population.addAll(regenPop);
				System.out.println();
				System.out.println("Running back tests... ");
				for (EvoBuySell ebs : population) {
					if (BigDec.EQ(ebs.getFitness(), BigDec.zero())) {
						ex.backTest(dataList, ebs, initVal);
					}
				}
			}
			
			population = EvoBuySell.limitAndRemoveDupes(population, MAX_POPULATION);
			
			List<EvoBuySell> breedList = EvoBuySell.rouletteSelectionChosenOnce(population);
			
			System.out.println();
			System.out.println("Breeding population... ");
			List<EvoBuySell> child = new ArrayList<EvoBuySell>();
			if (population.size() > 1) {
				for (int j = 0; j < breedList.size() - 1; j = j + 2) {
					child.add(EvoBuySell.breedAndMutateValidChild(breedList.get(j), breedList.get(j + 1), 0.01));
				}
			}
			population.addAll(child);
			
			System.out.println();
			System.out.println("Generating new members... ");
			List<EvoBuySell> newPopulation = EvoBuySell.generatePopulationFrom(temp, POP_SIZE - population.size());
			population.addAll(newPopulation);
			
			if (population.size() >= 100) {
				System.out.println();
				newPopulation = EvoBuySell.generatePopulationFrom(temp, 50);
			}
			population.addAll(newPopulation);
			
			population = population.stream()
					.sorted(Comparator.comparing(EvoBuySell::getFitness).reversed())
					.collect(Collectors.toList());
			
			System.out.println();
			System.out.println("Best params of population " + i + ":");
			System.out.println(population.get(0));
			System.out.println("Fitness: " + population.get(0).getFitness().toPlainString());
			System.out.println("Population size: " + population.size());
		}
	}
}
