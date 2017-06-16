package helpers.econ.currency;

public class Bank {

	private Wallet myWallet;
	private Wallet bankWallet;
	
	public Bank(Wallet myWallet, Wallet bankWallet){
		
		if (myWallet.getType() == bankWallet.getType()){
			this.myWallet = myWallet;
			this.bankWallet = bankWallet;
		}
		else{
			System.out.println("Bank(): Wallet type mismatch");
		}
		
	}
	
	public void deposit(double amount){
		bankWallet.deposit(myWallet.withdrawal(amount));
	}
	
	public void depoisitAll(){
		bankWallet.deposit(myWallet.withdrawalAll());
	}
	
	public void withdrawal(double amount){
		myWallet.deposit(bankWallet.withdrawal(amount));
	}
	
	public void withdrawalAll(){
		myWallet.deposit(bankWallet.withdrawalAll());
	}
	
	
}
