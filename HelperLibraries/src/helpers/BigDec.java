package helpers;

import java.math.BigDecimal;

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

}
