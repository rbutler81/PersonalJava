import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class Main {

	public static final String ORDERLINES_CSV = "csvFiles/orderlines.csv";
	public static final String OUTPUT_FILE = "csvFiles/ABC.csv";
	
	public static void main(String[] args) throws IOException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException {
		
		List<Order> orders = Order.toListOfOrder(CSVUtil.read(ORDERLINES_CSV, ";"));
		List<Order> sortedOrders = Order.sumAndSortByQuantityWithStream(orders);
		CSVUtil.writeObject(sortedOrders, OUTPUT_FILE, ",");
		
	}
}