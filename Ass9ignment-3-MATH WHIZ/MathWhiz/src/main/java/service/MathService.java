package service;

/**
 * MathService — Contains all mathematical functions used by the game.
 * Each function independently calculates the answer using Java's Math class.
 */
public class MathService {

    // ================================================================
    // TASK 03 — Prime Number Function
    // ================================================================

    /**
     * Determines whether a given integer is prime.
     *
     * @param number the integer to check
     * @return 1 if prime, 0 otherwise
     */
    public static int isPrime(int number) {
        if (number < 2) {
            return 0;
        }
        if (number == 2) {
            return 1;
        }
        if (number % 2 == 0) {
            return 0;
        }
        for (int i = 3; i <= Math.sqrt(number); i += 2) {
            if (number % i == 0) {
                return 0;
            }
        }
        return 1;
    }

    // ================================================================
    // TASK 04 — Modulus Function
    // ================================================================

    /**
     * Calculates the remainder of integer division.
     *
     * @param a the dividend
     * @param b the divisor (must not be zero)
     * @return the remainder of a % b
     * @throws ArithmeticException if b is zero
     */
    public static int calculateMod(int a, int b) {
        if (b == 0) {
            throw new ArithmeticException("Division by zero: the divisor must not be zero.");
        }
        return a % b;
    }

    // ================================================================
    // TASK 05 — Trigonometric Functions
    // ================================================================

    /**
     * Calculates the sine of an angle given in degrees.
     *
     * @param degree the angle in degrees
     * @return the sine value
     */
    public static double calculateSin(double degree) {
        double radians = Math.toRadians(degree);
        return Math.sin(radians);
    }

    /**
     * Calculates the cosine of an angle given in degrees.
     *
     * @param degree the angle in degrees
     * @return the cosine value
     */
    public static double calculateCos(double degree) {
        double radians = Math.toRadians(degree);
        return Math.cos(radians);
    }

    /**
     * Calculates the tangent of an angle given in degrees.
     * Handles undefined tangent values (e.g., 90°, 270°).
     *
     * @param degree the angle in degrees
     * @return the tangent value
     * @throws ArithmeticException if the tangent is undefined for the given angle
     */
    public static double calculateTan(double degree) {
        // Normalize degree to [0, 360)
        double normalized = degree % 360;
        if (normalized < 0) normalized += 360;

        // Tangent is undefined at 90° and 270°
        if (Math.abs(normalized - 90) < 1e-9 || Math.abs(normalized - 270) < 1e-9) {
            throw new ArithmeticException("Tangent is undefined for " + degree + " degrees.");
        }

        double radians = Math.toRadians(degree);
        return Math.tan(radians);
    }

    // ================================================================
    // Answer comparison helpers
    // ================================================================

    /** Tolerance for comparing trigonometric (decimal) answers. */
    private static final double TOLERANCE = 0.01;

    /**
     * Compares two decimal values within a tolerance.
     *
     * @param expected the correct value
     * @param actual   the player's answer
     * @return true if the values are within the tolerance
     */
    public static boolean isCloseEnough(double expected, double actual) {
        return Math.abs(expected - actual) <= TOLERANCE;
    }
}

