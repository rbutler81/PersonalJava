package helpers.math;

public class Conversions {
	
	public static String intAsBits(int n) {
		if (n == 0) return "0000";
		else if (n == 1) return "0001";
		else if (n == 2) return "0010";
		else if (n == 3) return "0011";
		else if (n == 4) return "0100";
		else if (n == 5) return "0101";
		else if (n == 6) return "0110";
		else if (n == 7) return "0111";
		else if (n == 8) return "1000";
		else if (n == 9) return "1001";
		else return "1111";
		
	}
	
	public static int bitsAsInt(String bits) {
		if (bits.equals("0000")) return 0;
		else if (bits.equals("0001")) return 1;
		else if (bits.equals("0010")) return 2;
		else if (bits.equals("0011")) return 3;
		else if (bits.equals("0100")) return 4;
		else if (bits.equals("0101")) return 5;
		else if (bits.equals("0110")) return 6;
		else if (bits.equals("0111")) return 7;
		else if (bits.equals("1000")) return 8;
		else if (bits.equals("1001")) return 9;
		else return 10;
	}

}
