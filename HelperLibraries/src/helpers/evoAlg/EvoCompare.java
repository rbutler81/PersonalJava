package helpers.evoAlg;

import java.math.BigDecimal;

import helpers.BigDec;
import helpers.math.Chance;
import helpers.math.Conversions;

public class EvoCompare {

	private String bits;
	private String op;
	private int id;
	private boolean isValid = false;
	
	public EvoCompare(int id) {
		decideType(id);
	}
	
	public EvoCompare() {
		decideType(Chance.intLT(6));
	}
	
	public String getBits() { return this.bits; }
	public String getOp() { return this.op; }
	public int getID() { return this.id; }
	public boolean isValid() { return isValid; }
	
	public void setID(int id) { decideType(id);	}
	
	private void decideType(int id) {
		if (id >= 6 || id < 0) isValid = false;
		else {
			isValid = true;
			this.id = id;
			if (id == 0) { this.bits = "0000"; this.op = "EQ"; }
			else if (id == 1) { this.bits = "0001"; this.op = "NE"; }
			else if (id == 2) { this.bits = "0010"; this.op = "GT"; }
			else if (id == 3) { this.bits = "0011"; this.op = "GE"; }
			else if (id == 4) { this.bits = "0100"; this.op = "LT"; }
			else { this.bits = "0101"; this.op = "LE"; }
		}
	}
	
	public boolean test(BigDecimal val1, BigDecimal val2) {
		if (id == 0) { return BigDec.EQ(val1, val2); }
		else if (id == 1) { return !BigDec.EQ(val1, val2); }
		else if (id == 2) { return BigDec.GT(val1, val2); }
		else if (id == 3) { return BigDec.GE(val1, val2); }
		else if (id == 4) { return BigDec.LT(val1, val2); }
		else { return BigDec.LE(val1, val2); }
	}
	
	@Override
	public String toString() { return op; } 
	
	public static EvoCompare breed(EvoCompare parentOne, EvoCompare parentTwo) {
		if (Chance.percent(50)) return new EvoCompare(parentOne.getID());
		else return new EvoCompare(parentTwo.getID());
	}
	
	public static void mutate(EvoCompare c, double chance) {
		String newBits = "";
		for (int i = 0; i < 4; i++) {
			if (Chance.percent(chance)) {
				if (c.getBits().charAt(i) == '0') newBits = newBits + '1';
				else newBits = newBits + '0';
			}
			else newBits = newBits + c.getBits().charAt(i);
		}
		c.setID(Conversions.bitsAsInt(newBits));
	}
	
	public static EvoCompare breedAndMutateValidChild(EvoCompare one, EvoCompare two, double chance) {
		boolean done = false;
		EvoCompare child = null;
		while (!done) {
			child = breed(one, two);
			mutate(child, chance);
			done = child.isValid();
		}
		return child;
	}
}
