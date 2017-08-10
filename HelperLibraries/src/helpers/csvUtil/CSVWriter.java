package helpers.csvUtil;
import java.util.List;

public interface CSVWriter {

	public List<String[]> toCSV(List<?> l);
	
}
