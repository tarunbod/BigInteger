package bigint;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Scanner;

public class AutoTester {

    static Scanner sc;

    public static void parse() throws IOException {
//		System.out.print("\tEnter integer => ");
        String integer = sc.nextLine();
        try {
            BigInteger bigInteger = BigInteger.parse(integer);
            printInfo(bigInteger);
//			System.out.println("Expected = " + Integer.parseInt(integer));
//			System.out.println(bigInteger.toString().equals(String.valueOf(Integer.parseInt(integer))) ? "IT WORKS" : "YOU'RE A RETARD");
        } catch (IllegalArgumentException e) {
            System.out.println("Incorrect Format");
        }
    }

    private static void printInfo(BigInteger bigInteger) {
        System.out.println(bigInteger);
        printList(bigInteger);
        System.out.println(bigInteger.negative);
        System.out.println(bigInteger.numDigits);
    }

    public static void add() throws IOException {
//		System.out.print("\tEnter first integer => ");
        String integer = sc.nextLine();
//		int sum = Integer.parseInt(integer);
        BigInteger firstBigInteger = BigInteger.parse(integer);

//		System.out.print("\tEnter second integer => ");
        integer = sc.nextLine();
//		sum += Integer.parseInt(integer);
        BigInteger secondBigInteger = BigInteger.parse(integer);

        BigInteger result = BigInteger.add(firstBigInteger, secondBigInteger);
        printInfo(result);
//		System.out.println("Sum: " + result);

//		System.out.println("Expected: " + sum);
//		System.out.println(result.toString().equals(String.valueOf(sum)) ? "IT WORKS" : "YOU'RE A RETARD");
    }

    public static void multiply() throws IOException {
//		System.out.print("\tEnter first integer => ");
        String integer = sc.nextLine();
//		int prod = Integer.parseInt(integer);
        BigInteger firstBigInteger = BigInteger.parse(integer);

//		System.out.print("\tEnter second integer => ");
        integer = sc.nextLine();
//		prod *= Integer.parseInt(integer);
        BigInteger secondBigInteger = BigInteger.parse(integer);

        BigInteger result = BigInteger.multiply(firstBigInteger, secondBigInteger);
        printInfo(result);
//		System.out.println("Product: " + result);
//		System.out.println("Expected: " + prod);
//		System.out.println(result.toString().equals(String.valueOf(prod)) ? "IT WORKS" : "YOU'RE A RETARD");
    }

    public static void main(String[] args) throws IOException {

        FileInputStream in = new FileInputStream("input.txt");
        PrintStream out = new PrintStream("output.txt");
        System.setIn(in);
        System.setOut(out);

        sc = new Scanner(System.in);

        char choice;
        while ((choice = getChoice()) != 'q') {
            switch (choice) {
                case 'p':
                    parse();
                    break;
                case 'a':
                    add();
                    break;
                case 'm':
                    multiply();
                    break;
                default:
                    System.out.println("Incorrect choice");
            }
        }

        StringBuilder expected = new StringBuilder(), output = new StringBuilder();
        sc = new Scanner(new File("expected.txt"));
        while (sc.hasNext()) {
            expected.append(sc.next());
        }
        sc.close();
        sc = new Scanner(new File("output.txt"));
        while (sc.hasNext()) {
            output.append(sc.next());
        }
        sc.close();
        if (expected.toString().equals(output.toString())) {
            System.err.println("you're good nigga");
        } else {
            System.err.println("You're retarded");
        }
    }

    private static char getChoice() {
//		System.out.print("\n(p)arse, (a)dd, (m)ultiply, or (q)uit? => ");
        String in = sc.nextLine();
        char choice;
        if (in == null || in.length() == 0) {
            choice = ' ';
        } else {
            choice = in.toLowerCase().charAt(0);
        }
        return choice;
    }

    private static void printList(BigInteger integer) {
        for (DigitNode ptr = integer.front; ptr != null; ptr = ptr.next) {
            System.out.print(ptr.digit + "->");
        }
        System.out.println("\\");
    }
}
