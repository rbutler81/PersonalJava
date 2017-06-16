package helpers.econ.currency;

public class CAD extends Coin {

	public CAD(){
		super(CoinType.CAD);
	}
	
	public CAD(double initialBalance){
		super(CoinType.CAD, initialBalance);
	}
	
}
