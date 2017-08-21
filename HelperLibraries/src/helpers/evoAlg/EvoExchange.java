package helpers.evoAlg;

import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import helpers.BigDec;
import helpers.econ.currency.Coin;
import helpers.econ.currency.Exchange;
import helpers.math.DataPoint;

public class EvoExchange extends Exchange {

	public EvoExchange(Coin primary, Coin secondary) {
		super(primary, secondary);
	}

	public void backTest(List<DataPoint> dp, EvoBuySell param, double initialValue) {
		
		primary.setValue(BigDec.valueOf(initialValue, primary.getType().getDecimalPlaces()));
		secondary.setValue(BigDec.valueOf(0, secondary.getType().getDecimalPlaces()));
		Map<String, String> trades = new HashMap<String, String>();
		
		dp.stream()
			.forEach(e -> {
				if (param.buy(e) && !param.sell(e) && !BigDec.EQ(primary.getValue(), BigDec.zero())) {
					buySecondaryByVolume(primary.getValue(), e.getDataPoint());
					trades.put("buy " + e.getDataPoint().toPlainString(), e.getTime().toPlainString());
				}
				else if (!param.buy(e) && param.sell(e) && !BigDec.EQ(secondary.getValue(), BigDec.zero())) {
					sellSecondary(secondary.getValue(), e.getDataPoint());
					trades.put("sell " + e.getDataPoint().toPlainString(), e.getTime().toPlainString());
				}
			});
		
		if (BigDec.EQ(primary.getValue(), BigDec.zero())) {
			primary.setValue(dp.get(dp.size() - 1).getDataPoint().multiply(secondary.getValue()));
		}
		
		param.setFitness(primary.getValue().divide(BigDec.valueOf(initialValue), primary.getType().getDecimalPlaces(), RoundingMode.HALF_EVEN));
		if (trades.entrySet().size() == 1) param.setFitness(BigDec.zero());
		
		/*if (BigDec.GT(param.getFitness(), BigDec.valueOf(1))) { 
			System.out.println(param);
			System.out.println(trades.entrySet().size());
			
			trades.entrySet().stream()
				.sorted(Map.Entry.comparingByValue())
				.forEach(p -> System.out.println(p.getKey() + " " + p.getValue()));
		}*/
	}
}
