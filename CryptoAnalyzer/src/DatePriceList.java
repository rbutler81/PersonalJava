import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;


@JsonDeserialize(using=DatePriceDeserializer.class)
public class DatePriceList {

	private List<DatePrice> data;
	
	public DatePriceList() { this.data = new ArrayList<DatePrice>(); }

	public List<DatePrice> getData() { return data;	}

	public void setData(List<DatePrice> data) { this.data = data; }
	
}
