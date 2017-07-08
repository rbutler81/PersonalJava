package helpers.econ.currency;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Coin {

	private CoinType type;
	private BigDecimal value;
		
	public Coin(CoinType type){
		this.type = type;
		value = new BigDecimal("0.0").setScale(type.getDecimalPlaces(), RoundingMode.DOWN);
		
	}
	
	public Coin(CoinType type, double initialVal){
		this(type);
		value = BigDecimal.valueOf(initialVal).setScale(type.getDecimalPlaces(), RoundingMode.DOWN);
		
	}
	
	public CoinType getType(){
		return type;
	}
	
	public BigDecimal getValue(){
		return value;
	}
	
	public void setValue(BigDecimal val){
		value = val.setScale(type.getDecimalPlaces(), RoundingMode.DOWN);
	}
	
	public void setValue(String val){
		value = new BigDecimal(val).setScale(type.getDecimalPlaces(), RoundingMode.DOWN);
	}
	
}
