package helpers.econ.currency;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import helpers.BigDec;
import helpers.math.DataPoint;

public class Exchange {

	private Coin primary;
	private Coin secondary;
	
	public Exchange(Coin primary, Coin secondary) {
		this.primary = primary;
		this.secondary = secondary;
	}

	public Coin getPrimary() { return primary; }

	public void setPrimary(Coin primary) { this.primary = primary; }

	public Coin getSecondary() { return secondary; }

	public void setSecondary(Coin secondary) { this.secondary = secondary; }
	
	public void buySecondary(BigDecimal amount, BigDecimal price) {
		
		if (BigDec.GE(primary.getValue(), amount.multiply(price))) {
			primary.setValue(primary.getValue().subtract(amount.multiply(price)));
			secondary.setValue(secondary.getValue().add(amount));
		}
	}
	
	public void sellSecondary(BigDecimal amount, BigDecimal price) {
		
		if (BigDec.GE(secondary.getValue(), amount)){
			primary.setValue(primary.getValue().add(amount.multiply(price)));
			secondary.setValue(secondary.getValue().subtract(amount));
		}
	}
	
	public void buySecondaryByVolume(BigDecimal totalAmountToSpend, BigDecimal currentPrice) {
		
		if (BigDec.GE(primary.getValue(), totalAmountToSpend)) {
			buySecondary(totalAmountToSpend.divide(currentPrice, secondary.getType().getDecimalPlaces(), RoundingMode.DOWN), currentPrice);
		}
	}
	
	public void backTest(List<DataPoint> dp, BackCalcParam p) {
		
		for (DataPoint e : dp) {
			
			if (p.getBuy().test(e) && !p.getSell().test(e)) {
				buySecondaryByVolume(primary.getValue(), e.getDataPoint());
				System.out.println("Buy! " + primary.getValue().toPlainString() + " " + secondary.getValue().toPlainString());
			}
			else if (!p.getBuy().test(e) && p.getSell().test(e)) {
				sellSecondary(secondary.getValue(), e.getDataPoint());
				System.out.println("Sell! " + primary.getValue().toPlainString() + " " + secondary.getValue().toPlainString());
			}
		}
	}
	
}
