package helpers;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class BigDec {

	public static boolean GT(BigDecimal one, BigDecimal two){
		return (one.compareTo(two) > 0);
	}
	
	public static boolean GE(BigDecimal one, BigDecimal two){
		return (one.compareTo(two) >= 0);
	}
	
	public static boolean EQ(BigDecimal one, BigDecimal two){
		return (one.compareTo(two) == 0);
	}
	
	public static boolean LE(BigDecimal one, BigDecimal two){
		return (one.compareTo(two) <= 0);
	}
	
	public static boolean LT(BigDecimal one, BigDecimal two){
		return (one.compareTo(two) < 0);
	}
	
	public static BigDecimal zero() { return new BigDecimal(0).setScale(0, RoundingMode.DOWN); }
	
	public static BigDecimal powerOfTen(int power) { return BigDec.valueOf(1).scaleByPowerOfTen(power); } 
	
	public static BigDecimal valueOf(double value, int scale) { return new BigDecimal(value).setScale(scale, RoundingMode.DOWN); }
	
	public static BigDecimal valueOf(double value) { return new BigDecimal(value).setScale(0, RoundingMode.DOWN); }
	
	public static BigDecimal valueOf(float value, int scale) { return new BigDecimal(value).setScale(scale, RoundingMode.DOWN); }
	
	public static BigDecimal valueOf(float value) { return new BigDecimal(value).setScale(0, RoundingMode.DOWN); }
	
	public static BigDecimal valueOf(int value) { return new BigDecimal(value).setScale(0, RoundingMode.DOWN); }
	
	public static BigDecimal percentDiff(BigDecimal v1, BigDecimal v2) {
		return v2.subtract(v1).divide(v1, 4, RoundingMode.DOWN).multiply(BigDec.valueOf(100.0, 0));
	}

}
