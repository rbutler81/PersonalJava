package helpers.evoAlg;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import helpers.BigDec;
import helpers.evoAlg.EvoParamSet;
import helpers.math.BigDecMap;

public class EvoBuySell {

	private EvoParamSet buy;
	private EvoParamSet sell;
	private BigDecimal fitness;
	
	public EvoBuySell() {}
	
	public EvoBuySell(EvoParamSet buy, EvoParamSet sell) {
		this.buy = buy;
		this.sell = sell;
		this.fitness = BigDec.zero();
		if (this.buy == null) {
			System.out.println();
		}
	}
	
	public EvoParamSet getBuy() { return buy; }
	public void setBuy(EvoParamSet buy) { this.buy = buy; }
	public EvoParamSet getSell() { return sell;	}
	public void setSell(EvoParamSet sell) {	this.sell = sell; }
	public BigDecimal getFitness() { return this.fitness; }
	public EvoBuySell setFitness(BigDecimal fitness) { this.fitness = fitness; return this; } 
	
	public boolean buy(BigDecMap o) {
		Map<Integer, BigDecimal> tests = o.toBigDecMap();
		boolean result = true;
		
		for (Entry<Integer, BigDecimal> e : tests.entrySet()) {
			if (result) {
				if (e.getKey() < 100) {
					if (buy.getParamSet().containsKey(e.getKey())) {
						result = result && buy.getParam(e.getKey()).test(e.getValue());
					}
				}
			}
		}
		return result;
	}
	
	public boolean sell(BigDecMap o) {
		Map<Integer, BigDecimal> tests = o.toBigDecMap();
		boolean result = true;
		
		for (Entry<Integer, BigDecimal> e : tests.entrySet()) {
			if (result) {
				if (e.getKey() < 100) {
					if (sell.getParamSet().containsKey(e.getKey())) {
						result = result && sell.getParam(e.getKey()).test(e.getValue());
					}
				}
			}
		}
		return result;
	}
	
	@Override
	public String toString() { return "buy if: " + buy.toString() + " sell if: " + sell.toString(); }
	
	public static EvoBuySell breedAndMutateValidChild(EvoBuySell one, EvoBuySell two, double chance) {
		return new EvoBuySell(EvoParamSet.breedAndMutateValidChild(one.getBuy(), two.getBuy(), chance),
				EvoParamSet.breedAndMutateValidChild(one.getSell(), two.getSell(), chance));
	}
	
	public static List<EvoBuySell> generatePopulationFrom(EvoParamSet e, int size) {
		List<EvoBuySell> r = new ArrayList<EvoBuySell>();
		for (int i = 0; i < size; i++) {
			System.out.println("Generating EvoBuySell population " + i + " of " + size);
			r.add(new EvoBuySell(EvoParamSet.generateNewFrom(e), EvoParamSet.generateNewFrom(e)));
		}
		return r;
	}
}
