package helpers.evoAlg;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.Predicate;

import helpers.BigDec;
import helpers.math.Chance;

public class EvoValue extends EvoGene {

	private float max;
	private float min;
	private int decimalPlaces;
	private int numLeftOfDecMax;
	private BigDecimal value;
	
	public EvoValue(float max, float min, int decimalPlaces) {
		this.max = max;
		this.min = min;
		this.decimalPlaces = decimalPlaces;
		
		numLeftOfDecMax = numLeftOfDecimal(max);
		int numLeftOfDecMin = numLeftOfDecimal(min);
		if (numLeftOfDecMin > numLeftOfDecMax) numLeftOfDecMax = numLeftOfDecMin;
		
		generateValue();
		encodeFromValue();
		decodeBits();
	}
	
	public EvoValue(String bits) {
		this.bits = bits;
		decodeBits();
	}

	public BigDecimal valueAsBigDec() { return value; }
	
	public float valueAsFloat() { return value.floatValue(); }
	
	public void setBits(String bits) { this.bits = bits; decodeBits(); }
	
	//Privates
	private void generateValue() {
		
		Random rand = new Random();
		
		BigDecimal range = BigDec.valueOf(max, decimalPlaces + numLeftOfDecMax).subtract(BigDec.valueOf(min, decimalPlaces));
		
		BigDecimal rnd = BigDec.valueOf(rand.nextLong()).abs();
		rnd = rnd.divide(BigDec.valueOf(Long.MAX_VALUE), decimalPlaces + numLeftOfDecMax, RoundingMode.HALF_EVEN);
		
		BigDecimal val = range.multiply(rnd);
		value = val.add(BigDec.valueOf(min, decimalPlaces)).setScale(decimalPlaces, RoundingMode.HALF_EVEN);
	}
	
	private void encodeFromValue() {
		
		if (BigDec.GE(value, BigDec.zero())) bits = "0";
		else bits = "1";
		
		Map<Integer, Integer> values = new HashMap<Integer, Integer>();
		
		BigDecimal v = value.divide(BigDec.powerOfTen(numLeftOfDecimal(value.floatValue()))).setScale(numLeftOfDecimal(value.floatValue()) + decimalPlaces, RoundingMode.DOWN);
		for (int i = numLeftOfDecimal(value.floatValue()) + decimalPlaces + 1; i > 0; i--) {
				
			if (i == decimalPlaces + 1) values.put(i, 10);
			else {
				v = v.multiply(BigDec.valueOf(10)).abs();
				values.put(i, v.intValue());
				v = v.subtract(BigDec.valueOf(v.intValue()));;
			}
		}
		
		values.entrySet().stream()
			.sorted(Map.Entry.<Integer, Integer>comparingByKey().reversed())
			.forEach(e -> {
				bits = bits + numberAsBits(e.getValue());
			});;
			
		valid = true;
	}
	
	protected void decodeBits() {
		
		valid = true;
		value = BigDec.zero();
		
		BigDecimal[] mod = BigDec.valueOf(bits.length() - 1).divideAndRemainder(BigDec.valueOf(4));
		if (!BigDec.EQ(mod[1], BigDec.zero())) valid = false;
		
		int tenToThe = 0;
		
		if (valid) {
			
			
			List<Integer> decList = new ArrayList<Integer>();
			String[] decStrings = {"1010", "1011", "1100", "1101", "1110", "1111"};
			for (String s : decStrings) {
				int decIndex = 0;
				int lastDecIndex = 0;
				while (decIndex != -1) {
					decIndex = bits.indexOf(s, lastDecIndex);
					lastDecIndex = decIndex + 1;
					if (decIndex != -1) decList.add(decIndex);
				}
			//if (decList.size() == 0) valid = false;
			}
			
			if (valid) {
				
				int validIndex = 0;
				for (Integer i : decList) {
					for (int j = 1; j < bits.length(); j = j + 4) {
						if (i == j) {
							validIndex++;
							tenToThe = ((i - 1) / 4) - 1;
						}
					}
				}
				//if (validIndex != 1) valid = false;
				if (validIndex != 0 && validIndex != 1) valid = false;
			}
			
		}
		
		if (valid) {
			
			int decPlacesFound = 0;
			for (int i = 1; i < bits.length(); i = i + 4) {
				if (bitsAsInt(bits.substring(i, i + 4)) > 9) decPlacesFound++;
			}
			//if (decPlacesFound != 1) valid = false;
			if (decPlacesFound != 0 && decPlacesFound != 1) valid = false;
			if (decPlacesFound == 0) tenToThe = ((bits.length() - 1) / 4) - 1;
		}
		
		if (valid) {
			
			BigDecimal val = BigDec.zero();
			
			int sign = 1;
			if (bits.substring(0, 1).equals("1")) sign = -1;
			
			for (int i = 1; i < bits.length(); i = i + 4) {
				
				int x = bitsAsInt(bits.substring(i, i + 4));
				if (x < 10) {
					val = BigDec.valueOf(x).multiply(BigDec.powerOfTen(tenToThe)).add(val);
					tenToThe--;
				}
				else {
					if (BigDec.EQ(val, BigDec.zero())) {
						tenToThe = -1;
					}
				}
			}
			value = val.multiply(BigDec.valueOf(sign));
		}
	}

	//Statics
	public static EvoValue newInstance(int length) {
		String bits = generateBits(length, true);
		return new EvoValue(bits);
	}
	
	public static EvoValue newInstance() {
		Random rand = new Random();
		String bits = generateBits(rand.nextInt(1001), false);
		return new EvoValue(bits);
	}
	
	public static EvoValue newValidInstance(int length) {
		boolean done = false;
		EvoValue v = new EvoValue("111");
		while (!done) {
			v = new EvoValue(generateBits(length, true));
			done = v.isValid();
		}
		return v;
	}
	
	public static EvoValue newValidInstance() {
		boolean done = false;
		EvoValue v = new EvoValue("111");
		Random rand = new Random();
		while (!done) {
			v = new EvoValue(generateBits(rand.nextInt(1001), true));
			done = v.isValid();
		}
		return v;
	}
	
	public static EvoValue newValidInstance(Predicate<EvoValue> p) {
		boolean done = false;
		EvoValue v = new EvoValue("111");
		Random rand = new Random();
		while (!done) {
			v = new EvoValue(generateBits(rand.nextInt(1001), true));
			done = v.isValid() && p.test(v);
		}
		return v;
	}
	
	public static List<EvoValue> newValidPopulation(int size, Predicate<EvoValue> p) {
		List<EvoValue> l = new ArrayList<EvoValue>();
		while (l.size() < size) {
			l.add(EvoValue.newValidInstance(p));
			System.out.print("Population Size: " + l.size() + "\r");
		}
		return l;
	}
	
	public static void mutate(double chanceAsPercent, EvoValue val) {
		
		if (val.isValid()) {
			String bits = val.getBits();
			String newBits = "";
			for (int i = 0; i < bits.length(); i++) {
				if (Chance.percent(chanceAsPercent)) {
					if (bits.charAt(i) == '0') newBits = newBits + '1';
					else newBits = newBits + '0';
				}
				else newBits = newBits + bits.charAt(i);
			}
			val.setBits(newBits);
		}
	}
	
	public static EvoValue breedAndMutate(EvoValue one, EvoValue two, double chanceAsPercent) {
		EvoValue child = breed(one, two);
		mutate(chanceAsPercent, child);
		return child;
	}
	
	public static EvoValue breedAndMutateValidChild(EvoValue one, EvoValue two, double chanceAsPercent, Predicate<EvoValue> p) {
		boolean valid = false;
		EvoValue child = null;
		while (!valid) {
			child = breedAndMutate(one, two, chanceAsPercent);
			valid = child.isValid() && p.test(child); 
		}
		return child;
	}

	public static EvoValue breed(EvoValue one, EvoValue two) {
		
		/*int childLength = 0;*/
		String childBits = "";
		String[] parent = new String[2];
		boolean segmentsDetermined = false;
		
		if (one.getBits().length() == two.getBits().length()) {
			parent[0] = one.getBits();
			parent[1] = two.getBits();
			segmentsDetermined = true;
		}
		else if (one.getBits().length() > two.getBits().length()) {
			parent[0] = one.getBits();
			parent[1] = two.getBits();
		}
		else {
			parent[0] = two.getBits();
			parent[1] = one.getBits();
		}
		
		int parent0Seg = (parent[0].length() - 1) / 4;
		int parent1Seg = (parent[1].length() - 1) / 4;
		int childSeg = 0;
		
		/*boolean childLikeParent0 = false;*/
		if (Chance.percent(50)) { /*childLength = parent[0].length(); childLikeParent0 = true;*/ childSeg = parent0Seg; }
		else { /*childLength = parent[1].length();*/ childSeg = parent1Seg; }
		
		boolean[] segments = new boolean[parent0Seg];
		for (int i = 0; i < Array.getLength(segments); i++) {
			if (segmentsDetermined) segments[i] = true;
			else segments[i] = false;
		}
		
		if (Chance.percent(50)) childBits = childBits + parent[0].charAt(0);
		else childBits = childBits + parent[1].charAt(0);
		
		double segmentPercent = 1.0 / (double) parent0Seg;
		int segmentsAssigned = 0;
		while (!segmentsDetermined) {
			for (int i = 0; i < Array.getLength(segments); i++) {
				if (segments[i] == false && segmentsAssigned < parent1Seg) {
					if (Chance.percent(segmentPercent)) { 
						segments[i] = true; 
						segmentsAssigned++;
						if (segmentsAssigned == parent1Seg) segmentsDetermined = true;
					}
				}
			}
		}
		
		int p0i = 1;
		int p1i = 1;
		int ci = 0;
		int i = 0;
		while (ci < childSeg) {
			if (segments[i]) {
				if (Chance.percent(50)) {
					childBits = childBits + parent[0].substring(p0i, p0i + 4);
				}
				else {
					childBits = childBits + parent[1].substring(p1i, p1i + 4);
				}
				p0i = p0i + 4;
				p1i = p1i + 4;
				ci++;
			}
			else {
				if (childSeg == parent0Seg) {
					childBits = childBits + parent[0].substring(p0i, p0i + 4);
					ci++;
				}
				p0i = p0i + 4;
			}
			i++;
		}
		
		return new EvoValue(childBits);
	}

	//Abstracts
	@Override
	public boolean isValid() { return valid; }
}
