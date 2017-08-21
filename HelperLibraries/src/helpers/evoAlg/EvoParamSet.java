package helpers.evoAlg;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;


public class EvoParamSet {

	private Map<Integer, EvoTest> paramSet;
	private BigDecimal fitness = null;
	
	public EvoParamSet() {}
	
	public EvoParamSet(int size) {
		paramSet = new HashMap<Integer, EvoTest>();
		int i = 0;
		while (paramSet.entrySet().size() < size) {
			paramSet.put(i, new EvoTest(p -> true));
			i++;
		}
	}
	
	public EvoParamSet(EvoParamSet e) {
		paramSet = e.getParamSet();
	}

	public Map<Integer, EvoTest> getParamSet() { return paramSet; }
	public EvoParamSet setParamSet(Map<Integer, EvoTest> paramSet) { this.paramSet = paramSet; return this;	}
	public int getParamCount() { return paramSet.entrySet().size(); }
	public EvoParamSet setFitness(BigDecimal fitness) { this.fitness = fitness; return this; }
	public BigDecimal getFitness() { return this.fitness; }
	public EvoParamSet setPredicate(int i, Predicate<EvoValue> p) { getParam(i).setValue(EvoValue.newValidInstance(p)); return this; }
	public EvoTest getParam(int i) { return paramSet.get(i); }
	
	
	@Override
	public String toString() {
		
		String r = paramSet.entrySet().stream()
			.sorted(Map.Entry.comparingByKey())
			.map(p -> "P" + p.getKey() + ": " + p.getValue().toString() + "\t")
			.collect(Collectors.joining(" "));
			
		return r;
	}
	
	private static EvoParamSet regenAll(Map<Integer, EvoTest> paramSet) {
		Map<Integer, EvoTest> r = new HashMap<Integer, EvoTest>();
		paramSet.entrySet().stream()
			.forEach(p -> r.put(p.getKey(), new EvoTest(EvoValue.newValidInstance(p.getValue().getValue().getPredicate()), new EvoCompare())));
		return new EvoParamSet().setParamSet(r);
	}
	
	public static List<EvoParamSet> generatePopulationFrom(EvoParamSet e, int size) {
		List<EvoParamSet> r = new ArrayList<EvoParamSet>();
		for (int j = 0; j < size; j++) {
			System.out.println("Generating parameter set " + j + " of " + size);
			r.add(EvoParamSet.regenAll(e.getParamSet()));
		}
		return r;
	}
	
	public static EvoParamSet generateNewFrom(EvoParamSet e) {
		if (e.getParamCount() == 0) {
			System.out.println();
		}
		EvoParamSet r = EvoParamSet.regenAll(e.getParamSet());
		return r;
	}

	public static EvoParamSet breedAndMutateValidChild(EvoParamSet one, EvoParamSet two, double chance) {
		if (one.getParamCount() != two.getParamCount()) {
			return null;
		}
		else {
			EvoParamSet ep = new EvoParamSet();
			Map<Integer, EvoTest> ps = new HashMap<Integer, EvoTest>();
			for (int i = 0; i < one.getParamCount(); i++) {
				EvoTest et = EvoTest.breedAndMutateValidChild(one.getParam(i), two.getParam(i), chance);
				ps.put(i, et);
			}
			ep.setParamSet(ps);
			return ep;
		}
	}
}
