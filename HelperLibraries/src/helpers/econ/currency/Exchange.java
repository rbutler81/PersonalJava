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
		
		if (BigDec.GE(primary.getValue(), amount.multiply(price)) && !BigDec.EQ(primary.getValue(), BigDec.zero())) {
			primary.setValue(primary.getValue().subtract(amount.multiply(price)));
			secondary.setValue(secondary.getValue().add(amount));
			System.out.println("Buy! " + primary.getValue().toPlainString() + " " + secondary.getValue().toPlainString() + " " + price.toPlainString());
		}
	}
	
	public void sellSecondary(BigDecimal amount, BigDecimal price) {
		
		if (BigDec.GE(secondary.getValue(), amount) && !BigDec.EQ(secondary.getValue(), BigDec.zero())){
			primary.setValue(primary.getValue().add(amount.multiply(price)));
			secondary.setValue(secondary.getValue().subtract(amount));
			System.out.println("Sell! " + primary.getValue().toPlainString() + " " + secondary.getValue().toPlainString() + " " + price.toPlainString());
		}
	}
	
	public void buySecondaryByVolume(BigDecimal totalAmountToSpend, BigDecimal currentPrice) {
		
		if (BigDec.GE(primary.getValue(), totalAmountToSpend)) {
			buySecondary(totalAmountToSpend.divide(currentPrice, secondary.getType().getDecimalPlaces(), RoundingMode.DOWN), currentPrice);
		}
	}
	
	public void backTest(List<DataPoint> dp, BackCalcParam param) {
		
		dp.stream()
			.forEach(e -> {
				if (param.getBuy().test(e) && !param.getSell().test(e)) {
					buySecondaryByVolume(primary.getValue(), e.getDataPoint());
				}
				else if (!param.getBuy().test(e) && param.getSell().test(e)) {
					sellSecondary(secondary.getValue(), e.getDataPoint());
				}
			});
	}
	
}
