package bigint;

/**
 * This class encapsulates a BigInteger, i.e. a positive or negative integer with
 * any number of digits, which overcomes the computer storage length limitation of
 * an integer.
 */
public class BigInteger {

    /**
     * True if this is a negative integer
     */
    boolean negative;

    /**
     * Number of digits in this integer
     */
    int numDigits;

    /**
     * Reference to the first node of this integer's linked list representation
     * NOTE: The linked list stores the Least Significant Digit in the FIRST node.
     * For instance, the integer 235 would be stored as:
     * 5 --> 3  --> 2
     * <p>
     * Insignificant digits are not stored. So the integer 00235 will be stored as:
     * 5 --> 3 --> 2  (No zeros after the last 2)
     */
    DigitNode front;

    /**
     * Initializes this integer to a positive number with zero digits, in other
     * words this is the 0 (zero) valued integer.
     */
    public BigInteger() {
        negative = false;
        numDigits = 0;
        front = null;
    }

    /**
     * Parses an input integer string into a corresponding BigInteger instance.
     * A correctly formatted integer would have an optional sign as the first
     * character (no sign means positive), and at least one digit character
     * (including zero).
     * Examples of correct format, with corresponding values
     * Format     Value
     * +0            0
     * -0            0
     * +123        123
     * 1023       1023
     * 0012         12
     * 0             0
     * -123       -123
     * -001         -1
     * +000          0
     * <p>
     * Leading and trailing spaces are ignored. So "  +123  " will still parse
     * correctly, as +123, after ignoring leading and trailing spaces in the input
     * string.
     * <p>
     * Spaces between digits are not ignored. So "12  345" will not parse as
     * an integer - the input is incorrectly formatted.
     * <p>
     * An integer with value 0 will correspond to a null (empty) list - see the BigInteger
     * constructor
     *
     * @param integer Integer string that is to be parsed
     * @return BigInteger instance that stores the input integer.
     * @throws IllegalArgumentException If input is incorrectly formatted
     */
    public static BigInteger parse(String integer) throws IllegalArgumentException {
        integer = integer.trim();
        if (integer.isEmpty()) throw new IllegalArgumentException(); // empty string
        for (char c : integer.toCharArray()) {
            if (c != '+' && c != '-' && !Character.isDigit(c)) {
                throw new IllegalArgumentException(); // contains invalid characters
            }
        }
        boolean negative = integer.charAt(0) == '-';
        if (integer.charAt(0) == '+' || integer.charAt(0) == '-') {
            integer = integer.substring(1);
        }

        if (checkAllZeros(integer)) {
            return new BigInteger(); // if all digits are 0, return a BigInteger with the default values (not negative, 0 digits, front node is null)
        }

        int numDigits = 0;

        // removes leading 0s
        for (int i = 0; i < integer.length(); i++) {
            if (integer.charAt(i) != '0') {
                integer = integer.substring(i);
                break;
            }
        }

        DigitNode front = new DigitNode(Character.digit(integer.charAt(0), 10), null);
        for (int i = 1; i < integer.length(); i++) {
            char curr = integer.charAt(i);
            front = new DigitNode(Character.digit(curr, 10), front); // add each new digit to the front
            numDigits++;
        }
        BigInteger result = new BigInteger();
        result.negative = negative;
        result.front = front;
        result.numDigits = numDigits + 1; // because loop runs n - 1 times (first digit is added before loop starts)
        return result;
    }

    // method to check if all characters in a string are the '0' char
    private static boolean checkAllZeros(String integer) {
        for (char c : integer.toCharArray()) {
            if (c != '0') {
                return false;
            }
        }
        return true;
    }

    /**
     * Adds the first and second big integers, and returns the result in a NEW BigInteger object.
     * DOES NOT MODIFY the input big integers.
     * <p>
     * NOTE that either or both of the input big integers could be negative.
     * (Which means this method can effectively subtract as well.)
     *
     * @param first  First big integer
     * @param second Second big integer
     * @return Result big integer
     */
    public static BigInteger add(BigInteger first, BigInteger second) {
        if (second.front == null) {
            return first;
        }
        if (first.front == null) {
            return second;
        }

        int c = compareSize(first, second);

        if (first.negative == second.negative) {
            if (c == 1) { // first is bigger
                BigInteger temp;
                temp = first;
                first = second;
                second = temp;
            }

            DigitNode firstPtr = first.front, secondPtr = second.front;
            int newNumDigits = second.numDigits;

            DigitNode newFront = new DigitNode(firstPtr.digit + secondPtr.digit, null);
            DigitNode ptr = newFront;
            for (int i = 1; i < second.numDigits; i++) {
                firstPtr = firstPtr == null ? null : firstPtr.next;
                secondPtr = secondPtr.next;
                if (firstPtr == null) { // if first has less digits and we ran out of them
                    ptr.next = new DigitNode(secondPtr.digit, null);
                    ptr = ptr.next;
                } else {
                    ptr.next = new DigitNode(firstPtr.digit + secondPtr.digit, null);
                    ptr = ptr.next;
                }
            }

            ptr = newFront;
            for (int i = 0; i < second.numDigits; i++) {
                if (ptr.digit >= 10) {
                    if (i == second.numDigits - 1) {
                        ptr.next = new DigitNode(ptr.digit / 10, null);
                        newNumDigits += 1; // extra digit due to carry
                    } else {
                        ptr.next.digit += ptr.digit / 10;
                    }
                    ptr.digit %= 10;
                }
                ptr = ptr.next;
            }

            BigInteger result = new BigInteger();
            result.negative = first.negative;
            result.front = newFront;
            result.numDigits = newNumDigits;
            return result;
        } else { // SUBTRACTING!
            if (c == 0) {
                return new BigInteger(); // numbers are equal, return 0
            }

            // MAKE SECOND THE POSITIVE AND LARGER NUMBER. ALWAYS
            boolean switched = false;
            if (c == 1) {
                System.out.println("first bigger");
                BigInteger temp;
                temp = first;
                first = second;
                second = temp;
            }

            if (second.negative) { // if second is negative (and is already larger in magnitude), switch signs
                first.negative = true;
                second.negative = false;
                switched = true;
            }

            System.out.println(first);
            System.out.println(second);

            DigitNode firstPtr = first.front, secondPtr = second.front;
            DigitNode newFront = new DigitNode(secondPtr.digit - firstPtr.digit, null);
            DigitNode ptr = newFront;
            for (int i = 1; i < second.numDigits; i++) {
                firstPtr = firstPtr == null ? null : firstPtr.next;
                secondPtr = secondPtr.next;
                if (firstPtr == null) { // if first has less digits and we ran out of them
                    ptr.next = new DigitNode(secondPtr.digit, null);
                    ptr = ptr.next;
                } else {
                    ptr.next = new DigitNode(secondPtr.digit - firstPtr.digit, null);
                    ptr = ptr.next;
                }
            }

            ptr = newFront;
            while (ptr != null) {
                if (ptr.digit < 0) {
                    ptr.digit += 10;
                    ptr.next.digit -= 1;
                }
                ptr = ptr.next;
            }

            /* TODO:
             - REMOVE TRAILING ZEROS
             - ADD CORRECT SIGN
             - CORRECT numDigits
             - jerk off
            */

            BigInteger result = new BigInteger();
            result.front = newFront;

            return result;
        }
    }

    private static int compareSize(BigInteger first, BigInteger second) {
        if (first.numDigits > second.numDigits) {
            return 1;
        } else if (second.numDigits > first.numDigits) {
            return -1;
        } else { // equal numDigits
            for (int j = first.numDigits - 1; j >= 0; j--) {
                DigitNode firstPtr = first.front;
                DigitNode secondPtr = second.front;
                for (int i = 0; i < j; i++) {
                    firstPtr = firstPtr.next;
                    secondPtr = secondPtr.next;
                }
                if (firstPtr.digit != secondPtr.digit) {
                    if (firstPtr.digit > secondPtr.digit) {
                        return 1;
                    } else {
                        return -1;
                    }
                }
            }
            return 0; // identical
        }
    }

    /**
     * Returns the BigInteger obtained by multiplying the first big integer
     * with the second big integer
     * <p>
     * This method DOES NOT MODIFY either of the input big integers
     *
     * @param first  First big integer
     * @param second Second big integer
     * @return A new BigInteger which is the product of the first and second big integers
     */
    public static BigInteger multiply(BigInteger first, BigInteger second) {
        if (first.front == null || second.front == null) {
            return new BigInteger(); // return 0 because one of the factors is 0
        }

        BigInteger result = new BigInteger();

        DigitNode firstPtr = first.front;
        for (int i = 0; i < first.numDigits; i++) {
            int c = firstPtr.digit * ((int) Math.pow(10, i));
            for (int j = 0; j < c; j++) {
                result = BigInteger.add(result, second);
            }
            firstPtr = firstPtr.next;
        }

        result.negative = first.negative != second.negative; // if not the same sign, answer is negative

        return result;
    }

    public String toString() {
        if (front == null) {
            return "0";
        }
        String retval = front.digit + "";
        for (DigitNode curr = front.next; curr != null; curr = curr.next) {
            retval = curr.digit + retval;
        }

        if (negative) {
            retval = '-' + retval;
        }
        return retval;

    }

    public void print() {
        for (DigitNode ptr = front; ptr != null; ptr = ptr.next) {
            System.out.print(ptr.digit + " -> ");
        }
        System.out.println("\\");
    }
}

