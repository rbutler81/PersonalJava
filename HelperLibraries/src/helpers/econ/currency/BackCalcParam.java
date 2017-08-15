package helpers.econ.currency;

import java.math.BigDecimal;
import java.util.function.Predicate;

import helpers.math.DataPoint;

public class BackCalcParam {

	private Predicate<DataPoint> buy;
	private Predicate<DataPoint> sell;
	private BigDecimal buyAmount;
	private BigDecimal buyAmountByVolume;
	private BigDecimal sellAmount;
	
	public BackCalcParam() {}

	public BackCalcParam(Predicate<DataPoint> buy, Predicate<DataPoint> sell, BigDecimal buyAmount,
			BigDecimal buyAmountByVolume, BigDecimal sellAmount) {
		this.buy = buy;
		this.sell = sell;
		this.buyAmount = buyAmount;
		this.buyAmountByVolume = buyAmountByVolume;
		this.sellAmount = sellAmount;
	}

	public Predicate<DataPoint> getBuy() {
		return buy;
	}

	public void setBuy(Predicate<DataPoint> buy) {
		this.buy = buy;
	}

	public Predicate<DataPoint> getSell() {
		return sell;
	}

	public void setSell(Predicate<DataPoint> sell) {
		this.sell = sell;
	}

	public BigDecimal getBuyAmount() {
		return buyAmount;
	}

	public void setBuyAmount(BigDecimal buyAmount) {
		this.buyAmount = buyAmount;
	}

	public BigDecimal getBuyAmountByVolume() {
		return buyAmountByVolume;
	}

	public void setBuyAmountByVolume(BigDecimal buyAmountByVolume) {
		this.buyAmountByVolume = buyAmountByVolume;
	}

	public BigDecimal getSellAmount() {
		return sellAmount;
	}

	public void setSellAmount(BigDecimal sellAmount) {
		this.sellAmount = sellAmount;
	}

}
