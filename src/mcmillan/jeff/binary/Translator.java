package mcmillan.jeff.binary;

public class Translator {
	public static int MAX_BINPWR = 16;
	public static int MAX_VALUE = 65536;
	
	public static int binaryToDecimal(String binary) {
		StringBuilder bin = new StringBuilder(binary);
		if (bin.length() > 16) throw new IllegalArgumentException("Binary string input too long for maximum value!");
		if (bin.length() == 0) return 0;
		int index = bin.length()-1, place = 0, sum = 0;
		while (index >= 0) {
			char d = bin.charAt(index);
			if (d != '0' && d != '1') throw new IllegalArgumentException("Binary string input contains non-one/non-zero: '" + d + "'");
			
			if (d == '1') {
				sum += Math.pow(2, place);
			}
			place++;
			index--;
		}
		return sum;
	}
	
	public static String decimalToBinary(int dec) {
		if (dec < 0) throw new IllegalArgumentException("Negative decimal input!");
		if (dec > MAX_VALUE) throw new IllegalArgumentException("Negative decimal input!");
		
		String bin = "";
		int place = MAX_BINPWR;
		while (place >= 0) {
			int subtrahend = (int) Math.pow(2, place);
			
			if (subtrahend <= dec) {
				dec -= subtrahend;
				bin = bin + "1";
			} else {
				if (bin.length() > 0) bin = bin + "0";
			}
			
			place--;
		}
		return bin.toString();
	}
}
