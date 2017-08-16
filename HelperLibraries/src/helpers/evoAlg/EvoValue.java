package helpers.evoAlg;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.Predicate;

import helpers.BigDec;

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
		System.out.println();
	}
	
	public EvoValue(String bits) {
		this.bits = bits;
		decodeBits();
	}

	public BigDecimal valueAsBigDec() { return value; }
	
	public float valueAsFloat() { return value.floatValue(); }
	
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
			
			int decIndex = 0;
			List<Integer> decList = new ArrayList<Integer>();
			int lastDecIndex = 0;
			
			while (decIndex != -1) {
				decIndex = bits.indexOf("1111", lastDecIndex);
				lastDecIndex = decIndex + 1;
				if (decIndex != -1) decList.add(decIndex);
			}
			if (decList.size() == 0) valid = false;
			
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
				if (validIndex != 1) valid = false;
			}
			
		}
		
		if (valid) {
			
			int decPlacesFound = 0;
			for (int i = 1; i < bits.length(); i = i + 4) {
				if (bitsAsInt(bits.substring(i, i + 4)) > 9) decPlacesFound++;
			}
			if (decPlacesFound != 1) valid = false;
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

	//Abstracts
	@Override
	public boolean isValid() { return valid; }
}
