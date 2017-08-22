package helpers.evoAlg;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import helpers.BigDec;
import helpers.evoAlg.EvoParamSet;
import helpers.math.BigDecMap;
import helpers.math.Chance;

public class EvoBuySell {

	private EvoParamSet buy;
	private EvoParamSet sell;
	private BigDecimal fitness;
	private BigDecimal rouletteRatio;
	
	public EvoBuySell() {}
	
	public EvoBuySell(EvoParamSet buy, EvoParamSet sell) {
		this.buy = buy;
		this.sell = sell;
		this.fitness = BigDec.zero();
	}
	
	public EvoParamSet getBuy() { return buy; }
	public void setBuy(EvoParamSet buy) { this.buy = buy; }
	public EvoParamSet getSell() { return sell;	}
	public void setSell(EvoParamSet sell) {	this.sell = sell; }
	public BigDecimal getFitness() { return this.fitness; }
	public EvoBuySell setFitness(BigDecimal fitness) { this.fitness = fitness; return this; } 
	public BigDecimal getRouletteRatio() { return this.rouletteRatio; }
	public EvoBuySell setRouletteRatio(BigDecimal r) {this.rouletteRatio = r; return this; }
	
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
	
	public static List<EvoBuySell> rouletteSelection(List<EvoBuySell> l) {
		List<EvoBuySell> r = new ArrayList<EvoBuySell>();
		BigDecimal sum = BigDec.valueOf(0, 30);
		for (EvoBuySell e : l) {
			sum = sum.add(e.getFitness());
		}
		for (EvoBuySell e : l) {
			e.setRouletteRatio(e.getFitness().divide(sum, 30, RoundingMode.HALF_EVEN));
		}
		
		for (int i = 0; i < l.size(); i++) {
			BigDecimal v = Chance.zeroToOne();
			sum = BigDec.valueOf(0, 30);
			boolean done = false;
			int j = 0;
			while(!done) {
				if (BigDec.GE(sum.add(l.get(j).getRouletteRatio()), v)) {
					r.add(l.get(j));
					done = true;
				}
				else {
					sum = sum.add(l.get(j).getRouletteRatio());
				}
				j++;
			}
		}
		return r;
	}
	
	public static List<EvoBuySell> limitAndRemoveDupes(List<EvoBuySell> l, int limit) {
		
		List<EvoBuySell> i = l.stream()
				.filter(p -> BigDec.GT(p.getFitness(), BigDec.valueOf(1)))
				.sorted(Comparator.comparing(EvoBuySell::getFitness).reversed())
				.collect(Collectors.toList());
		
		Map<BigDecimal, EvoBuySell> rd = new HashMap<BigDecimal, EvoBuySell>();
		for (EvoBuySell e : i) {
			rd.put(e.getFitness(), e);
		}
		
		i = rd.entrySet().stream()
				.map(p -> p.getValue())
				.sorted(Comparator.comparing(EvoBuySell::getFitness).reversed())
				.limit(limit)
				.collect(Collectors.toList());
		
		return i;
	}
}
