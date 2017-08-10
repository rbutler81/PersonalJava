import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import helpers.csvUtil.CSVWriter;

public class DatePrice implements CSVWriter{

	private final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
	
	private long dateTime;
	private BigDecimal price;
	
	
	public DatePrice(String dateTime, String price) {
		Date d = new Date();
		try { d = format.parse(dateTime);  
		} catch (ParseException e) {}
		this.dateTime = d.getTime() / 1000L;
		this.price = new BigDecimal(price).setScale(2, RoundingMode.HALF_UP);
	}
	
	public DatePrice() {}

	public long getDateTime() { return dateTime; }

	public void setDateTime(String dateTime) { 
		Date d = new Date();
		try { d = format.parse(dateTime);  
		} catch (ParseException e) {}
		this.dateTime = d.getTime() / 1000L;
	}

	public BigDecimal getPrice() { return price; }

	public void setPrice(String price) { this.price = new BigDecimal(price).setScale(2, RoundingMode.HALF_UP); }

	@SuppressWarnings("unchecked")
	@Override
	public List<String[]> toCSV(List<?> l) {
		
		List<String[]> r = new ArrayList<String[]>();
		
		if (l.size() > 0) {
			
			List<DatePrice> a = (List<DatePrice>) l;
			
			r.add(new String[] {"time", "price"});
			
			for (DatePrice e : a) {
				r.add(new String[] {Long.toString(e.getDateTime()), e.getPrice().toString()});
			}
		}
		return r;
	}
	
}
