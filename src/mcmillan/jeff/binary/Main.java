package mcmillan.jeff.binary;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.swing.JFileChooser;

public class Main {

	public static Scanner console;
	public static Scanner fileScanner;
	
	public static void main(String[] args) {
		console = new Scanner(System.in);
		System.out.print("Is your input binary or decimal (B/D): ");
		boolean binaryOrDecimal = readEitherChar('b', 'd');
		if (binaryOrDecimal) {
			System.out.println("Binary to Decimal translation");
		} else {
			System.out.println("Decimal to Binary translation");
		}
		System.out.print("Console or file input (C/F): ");
		boolean consoleOrFile = readEitherChar('c', 'f');
		
		String fileIn;
		String binIn = "";
		int decIn = 0;
		if (consoleOrFile) {
			if (binaryOrDecimal) {
				System.out.print("Binary Input: ");
				binIn = readBinStr();
			} else {
				System.out.print("Decimal Input: ");
				decIn = readInt();
			}
		} else 	{
			System.out.println("File dialog opened...");
			File f = chooseFile();
			System.out.println("\""+ f.getAbsolutePath() + "\" selected!");
			try {
				fileScanner = new Scanner(f);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			fileIn = fileScanner.nextLine().trim();
			try {
				if (binaryOrDecimal) {
					if (!binValidate(fileIn)) throw new NumberFormatException("File string not a valid binary number!");
					binIn = fileIn;
				} else {
					try { // Try catch just to throw a simpler exception with a user readable message.
						decIn = Integer.parseInt(fileIn);
					} catch (NumberFormatException e) {throw new NumberFormatException("File string not a valid integer number!");}
				}
			} catch (NumberFormatException e) {
				System.err.println(e.getMessage());
				console.nextLine(); // Keep console open to read
				System.exit(1); // Exit with error code 1
			}
		}
		System.out.print(binaryOrDecimal ? "Decimal Output: " : "Binary Output: ");
		System.out.println(binaryOrDecimal ? Translator.binaryToDecimal(binIn) : Translator.decimalToBinary(decIn));
		
		console.nextLine(); // Keep console open to read
		System.exit(0);
	}
	
	
	// Console input methods are mostly stripped from my Guessing Game project.
	// Returns a valid integer, will retry on invalid input
	public static int readInt() {
		while (true) {
			try {
				return Integer.parseInt(console.nextLine());
			} catch (Exception e) {
				System.out.print("Not a whole number, try again: ");
			}
		}
	}
	
	public static String readBinStr() {
		String in = "";
		while (true) {
			in = console.nextLine().trim();
			if (binValidate(in)) {
				return in;
			}
			System.out.print("Not a binary number, try again: ");
		}
	}
	
	public static boolean readEitherChar(char t, char f) {
		String r = "";
		char c;
		while (true) {
			try {
				r = console.nextLine().toLowerCase();
				
				if (r.length() == 1) {
					c = r.charAt(0);
					if (c == t) {
						return true;
					} else if (c == f) {
						return false;
					}
				};
				throw new Exception("Not '" + t + "' or '" + f +"' or valid string.");
			} catch (Exception e) {
				System.out.print("Not the character '" + t + "' or '" + f + "', try again: ");
			}
		}
		
	}
	
	// I don't know how to make dialog draw focus over other windows, makes it hard for user-experience.
	public static File chooseFile() {
		JFileChooser fc = new JFileChooser();
		fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
		int result = JFileChooser.CANCEL_OPTION;
		while (result != JFileChooser.APPROVE_OPTION) result = fc.showOpenDialog(null);
		return fc.getSelectedFile();
	}

	public static boolean binValidate(String b) {
		char c;
		for (int i=0;i<b.length();i++) {
			c = b.charAt(i);
			if (c != '0' && c != '1') return false;
		}
		return true;
	}
	
}
