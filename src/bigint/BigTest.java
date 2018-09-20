package bigint;

import java.io.IOException;
import java.util.Scanner;

public class BigTest {

	static Scanner sc;
	
	public static void parse() 
	throws IOException {
		System.out.print("\tEnter integer => ");
		String integer = sc.nextLine();
		try {
			BigInteger bigInteger = BigInteger.parse(integer);
			System.out.println("\t\tValue = " + bigInteger);
			System.out.print("\t\tList = "); bigInteger.print();
			System.out.println("\t\tNegative = " + bigInteger.negative);
			System.out.println("\t\tNum Digits = " + bigInteger.numDigits);
		} catch (IllegalArgumentException e) {
			System.out.println("\t\tIncorrect Format");
		}
	}
	
	public static void add() 
	throws IOException {
		System.out.print("\tEnter first integer => ");
		String integer = sc.nextLine();
		BigInteger firstBigInteger = BigInteger.parse(integer);
		
		System.out.print("\tEnter second integer => ");
		integer = sc.nextLine();
		BigInteger secondBigInteger = BigInteger.parse(integer);

		System.out.println("=============");
		BigInteger result = BigInteger.add(firstBigInteger,secondBigInteger);
		System.out.println("=============");

		System.out.println("\t\tSum: " + result);

		System.out.print("\t\tList = "); result.print();
		System.out.println("\t\tNegative = " + result.negative);
		System.out.println("\t\tNum Digits = " + result.numDigits);
	}
	
	public static void multiply() 
	throws IOException {
		System.out.print("\tEnter first integer => ");
		String integer = sc.nextLine();
		BigInteger firstBigInteger = BigInteger.parse(integer);
		
		System.out.print("\tEnter second integer => ");
		integer = sc.nextLine();
		BigInteger secondBigInteger = BigInteger.parse(integer);
		
		BigInteger result = BigInteger.multiply(firstBigInteger,secondBigInteger);
		System.out.println("\t\tProduct: " + result);

		System.out.print("\t\tList = "); result.print();
		System.out.println("\t\tNegative = " + result.negative);
		System.out.println("\t\tNum Digits = " + result.numDigits);
	}
	
	public static void main(String[] args) 
	throws IOException {
		
		// TODO Auto-generated method stub
		sc = new Scanner(System.in);
		
		char choice;
		while ((choice = getChoice()) != 'q') {
			switch (choice) {
				case 'p' : parse(); break;
				case 'a' : add(); break;
				case 'm' : multiply(); break;
				default: System.out.println("Incorrect choice"); 
			}
		}
	}

	private static char getChoice() {
		System.out.print("\n(p)arse, (a)dd, (m)ultiply, or (q)uit? => ");
		String in = sc.nextLine();
		char choice;
		if (in == null || in.length() == 0) {
			choice = ' ';
		} else {
			choice = in.toLowerCase().charAt(0);
		}
		return choice;
	}

}

