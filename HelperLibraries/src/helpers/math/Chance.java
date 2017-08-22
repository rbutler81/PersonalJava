package helpers.math;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;

import helpers.BigDec;

public class Chance {

	public static boolean percent(double percent) {
		Random rand = new Random();
		BigDecimal odds = BigDec.valueOf(percent, 10).divide(BigDec.valueOf(100), 10, RoundingMode.HALF_EVEN);
		int n = rand.nextInt(Integer.MAX_VALUE) + 1;
		BigDecimal result = BigDec.valueOf(n).divide(BigDec.valueOf(Integer.MAX_VALUE), 20, RoundingMode.HALF_EVEN);
		if (BigDec.EQ(odds, BigDec.zero())) return false;
		else if (BigDec.GE(odds, result)) return true;
		else return false;
	}
	
	public static BigDecimal zeroToOne() {
		Random rand = new Random();
		int n = rand.nextInt(Integer.MAX_VALUE) + 1;
		BigDecimal result = BigDec.valueOf(n).divide(BigDec.valueOf(Integer.MAX_VALUE), 30, RoundingMode.HALF_EVEN);
		return result;
	}
	
	public static int intLT(int LT) {
		Random rand = new Random();
		return rand.nextInt(LT);
	}
}
