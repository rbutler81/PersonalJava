package helpers.econ.currency;
import java.math.BigDecimal;

public class Wallet {

	private Coin coin;
			
	public Wallet(CoinType coin){
		this.coin = new Coin(coin);
	}
	
	public Wallet(CoinType coin, double initialBalance){
		this(coin);
		this.coin.setValue(BigDecimal.valueOf(initialBalance));
	}
	
	public Wallet(CoinType coin, BigDecimal initialBalance){
		this(coin);
		this.coin.setValue(initialBalance);
		initialBalance = BigDecimal.valueOf(0.0);
	}
	
	public Wallet(Coin initialCoin){
		this(initialCoin.getType(), initialCoin.getValue());
		initialCoin.setValue(BigDecimal.valueOf(0.0));
	}
	
	public double getDoubleBalance(){
		BigDecimal bd = coin.getValue();
		return bd.doubleValue();
	}
	
	public BigDecimal getBigDecBalance(){
		return coin.getValue();
	}
	
	public CoinType getType(){
		return coin.getType();
	}
	
	public boolean deposit(Coin amount){
		if (coin.getType() == amount.getType()){
			BigDecimal a = coin.getValue();
			BigDecimal b = amount.getValue();
			a = a.add(b);
			coin.setValue(a);
			amount.setValue(BigDecimal.valueOf(0.0));
			return true;
		}
		else{		
			System.out.println("Wallet.deposit(): Coin type mismatch");
			return false;
		}
	}
	
	public void deposit(double amount){
		Coin aCoin = new Coin(this.coin.getType(), amount);
		BigDecimal x = this.coin.getValue();
		BigDecimal y = aCoin.getValue();
		x = x.add(y);
		this.coin.setValue(x);
		
	}
	
	public Coin withdrawal(double amount){
		BigDecimal a = coin.getValue();
		BigDecimal b = BigDecimal.valueOf(amount);
		if (a.compareTo(b) >= 0){
			a = a.subtract(b);
			coin.setValue(a);
			return new Coin(this.coin.getType(), amount);			
		}
		else{
			System.out.println("Wallet.withdrawal(): Insufficient funds");
			return null;
		}
	}
	
	
	public Coin withdrawalAll(){
		Coin returnCoin = new Coin(coin.getType());
		returnCoin.setValue(coin.getValue());
		coin.setValue(BigDecimal.valueOf(0.0));
		return returnCoin;		
	}
}
