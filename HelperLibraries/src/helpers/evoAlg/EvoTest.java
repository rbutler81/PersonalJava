package helpers.evoAlg;

import java.math.BigDecimal;
import java.util.function.Predicate;

public class EvoTest {

	private EvoValue value;
	private EvoCompare comparator;
	
	public EvoTest(EvoValue value, EvoCompare comparator) { this.value = value; this.comparator = comparator; }
	public EvoTest(Predicate<EvoValue> p) { this.value = EvoValue.newValidInstance(p); this.comparator = new EvoCompare(); }
	
	public EvoValue getValue() { return value; }
	public EvoTest setValue(EvoValue value) { this.value = value; return this; }
	public EvoCompare getComparator() { return comparator; }
	
	public boolean test(BigDecimal val) {
		return comparator.test(val, value.valueAsBigDec());
	}
	
	@Override
	public String toString() { return comparator.toString() + value.toString(); }
	
	public static EvoTest breedAndMutateValidChild(EvoTest one, EvoTest two, double chanceOfMutation) {
		EvoValue childValue = EvoValue.breedAndMutateValidChild(one.getValue(), two.getValue(), chanceOfMutation);
		EvoCompare childCompare = EvoCompare.breedAndMutateValidChild(one.getComparator(), two.getComparator(), chanceOfMutation);
		return new EvoTest(childValue, childCompare);
	}
}
