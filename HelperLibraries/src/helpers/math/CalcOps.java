package helpers.math;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class CalcOps {

	public static BigDecimal calcDerivative(BigDecimal t1, BigDecimal t2, BigDecimal v1, BigDecimal v2) {
		
		BigDecimal n = v2.subtract(v1);
		BigDecimal d = t2.subtract(t1);
		return n.divide(d, v1.scale() + 5, RoundingMode.HALF_UP);
		
	}
}
