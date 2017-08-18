package helpers.evoAlg;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Predicate;

public class EvoParamSet {

	private Map<Integer, EvoTest> paramSet;
	private BigDecimal fitness = null;
	
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
	public void setParamSet(Map<Integer, EvoTest> paramSet) { this.paramSet = paramSet;	}
	
	public EvoParamSet setPredicate(int i, Predicate<EvoValue> p) { getParam(i).setValue(EvoValue.newValidInstance(p)); return this; }
	
	public EvoTest getParam(int i) { return paramSet.get(i); }
	
	private EvoParamSet regenAll() {
		paramSet.entrySet().stream()
			.forEach(p -> paramSet.put(p.getKey(), new EvoTest(EvoValue.newValidInstance(p.getValue().getValue().getPredicate()), new EvoCompare())));
		return this;
	}
	
	public static List<EvoParamSet> generatePopulationFrom(EvoParamSet e, int size) {
		List<EvoParamSet> r = new ArrayList<EvoParamSet>();
		for (int j = 0; j < size; j++) {
			System.out.println("Generating parameter set " + j + " of " + size);
			r.add(e.regenAll());
			for (Entry<Integer, EvoTest> en : r.get(j).getParamSet().entrySet()) {
				System.out.print(j + ": " + en.getValue().getComparator().getOp() + en.getValue().getValue().valueAsBigDec().toPlainString() + " ");
			}
			System.out.println();
		}
		return r;
	}

}
