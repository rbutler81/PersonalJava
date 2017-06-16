package helpers;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Time {

	public static String getDateTimeStamp() {
	    return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date());
	}
	
	public static String convertMilliToHMS(long milli){
		
		BigDecimal milliBD = new BigDecimal(milli).setScale(0, RoundingMode.DOWN);
		BigDecimal seconds = milliBD.divide(new BigDecimal("1000").setScale(0), 10, RoundingMode.DOWN);
		BigDecimal minutes = seconds.divide(new BigDecimal("60").setScale(0), 10, RoundingMode.DOWN);
		BigDecimal hours = minutes.divide(new BigDecimal("60").setScale(0), 10, RoundingMode.DOWN);
		
		seconds = seconds.remainder(new BigDecimal("60").setScale(0)).setScale(3, RoundingMode.DOWN);
		minutes = minutes.remainder(new BigDecimal("60").setScale(0)).setScale(0, RoundingMode.DOWN);
		hours = hours.setScale(0, RoundingMode.DOWN);
		
		DecimalFormat f = new DecimalFormat("00.000");
		
		return String.format("%02d", Integer.parseInt(hours.toString())) + ":" 
				+ String.format("%02d", Integer.parseInt(minutes.toString())) + ":" 
				+ f.format(Double.parseDouble(seconds.toString()));
	}
}
