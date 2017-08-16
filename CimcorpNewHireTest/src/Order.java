import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

public class Order implements Comparable<Order>, CSVWriter {

	private String productNumber;
	private int orderNumber;
	private int orderQuantity;
	
	public Order() {}
	
	public Order(int orderNumber, String productNumber, int orderQuantity) {
		this.orderNumber = orderNumber;
		this.productNumber = productNumber;
		this.orderQuantity = orderQuantity;
	}
	
	public Order(String[] i) {
		this.orderNumber = Integer.parseInt(i[0]);
		this.productNumber = i[1];
		this.orderQuantity = Integer.parseInt(i[2]);
	}
	
	public String getProductNumber() {
		return productNumber;
	}
	public void setProductNumber(String productNumber) {
		this.productNumber = productNumber;
	}
	public int getOrderNumber() {
		return orderNumber;
	}
	public void setOrderNumber(int orderNumber) {
		this.orderNumber = orderNumber;
	}
	public int getOrderQuantity() {
		return orderQuantity;
	}
	public void setOrderQuantity(int orderQuantity) {
		this.orderQuantity = orderQuantity;
	}
	
	public String[] toStringArray() {
		String[] rVal = new String[3];
		rVal[0] = Integer.toString(orderNumber);
		rVal[1] = productNumber;
		rVal[2] = Integer.toString(orderQuantity);
		return rVal;
	}
	
	public void setFromStringArray(String[] i) {
		orderNumber = Integer.parseInt(i[0]);
		productNumber = i[1];
		orderQuantity = Integer.parseInt(i[2]);
	}
	
	public static List<Order> toListOfOrder(List<String[]> list) {
		
		List<Order> orders = new ArrayList<Order>();
		
		for (int i = 0; i < list.size(); i++) {
			orders.add(new Order(list.get(i)));
		}
		
		return orders;
	}
	
	public static List<Order> sumAndSortByQuantityWithStream(List<Order> l) {
	
		return l.stream()
				.collect(Collectors.groupingBy(Order::getProductNumber,
												Collectors.summingInt(Order::getOrderQuantity)))
													.entrySet()
													.stream()
					.map(e -> new Order(0, e.getKey(), e.getValue()))
					.sorted(Comparator.comparing(Order::getOrderQuantity).reversed())
					.collect(Collectors.toList());
		
	}
	
	public static List<Order> sumAndSortByQuantity(List<Order> l) {
		
		Map<String, Order> m = new HashMap<String, Order>();
				
		for (int i = 0; i < l.size(); i++){
			
			if (!m.containsKey(l.get(i).getProductNumber())) {
				m.put(l.get(i).getProductNumber(), l.get(i));
			}
			else {
				Order j = m.get(l.get(i).getProductNumber());
				j.setOrderNumber(0);
				j.setOrderQuantity(l.get(i).getOrderQuantity() + j.getOrderQuantity());
				m.put(l.get(i).getProductNumber(), j);
			}
		}
		
		Set<Entry<String, Order>> s = m.entrySet();
		List<Order> returnList = new ArrayList<Order>();
		
		for (Entry<String, Order> e : s) {
			returnList.add(e.getValue());
		}
		
		Collections.sort(returnList);
		
		return returnList;
		
	}

	@Override
	public int compareTo(Order o) {
		return Integer.compare(o.orderQuantity, this.orderQuantity);
	}

	@Override
	public List<String[]> toCSV(List<?> l) {
		
		List<String[]> o = new ArrayList<String[]>();
		
		for (Object e : l) {
			
			Order i = (Order)e;
			String[] s = new String[2];
			s[0] = i.getProductNumber();
			s[1] = Integer.toString(i.getOrderQuantity());
			o.add(s);
		}
		
		return o;
	}
}
