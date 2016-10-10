package org.justgive.util;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

/**
 * Wrapper for money math.  All methods use a scale of two with RoundingMode.HALF_UP.
 * <p/>
 * Methods are static and work with BigDecimal objects which are immutable.
 * <p/>
 * BigDecimal arguments to these methods are assumed to have been created with the correct scale and rounding
 * which can be assured by obtaining BigDecimal objects from one of the newBigDecimal() methods.
 * <p/>
 * <p/>
 * Created by IntelliJ IDEA.
 * User: curtis
 * Date: 5/16/13
 * Time: 4:41 PM
 */
public class MoneyMath {
    public static int SCALE = 2;
    public static RoundingMode ROUNDING_MODE = RoundingMode.HALF_UP;
    public static BigDecimal ZERO = newBigDecimal(0);

    private MoneyMath() {

    }

    // Conversion methods that obtain a new BigDecimal with the correct math context
    // from values in other formats
    public static BigDecimal newBigDecimal(double val) {
        BigDecimal newValue = new BigDecimal(val);
        return round(newValue);
    }

    public static BigDecimal newBigDecimal(int val) {
        BigDecimal newValue = new BigDecimal(val);
        return round(newValue);
    }

    public static BigDecimal newBigDecimal(String val) {
        BigDecimal newValue = new BigDecimal(val);
        return round(newValue);
    }

    public static BigDecimal round(BigDecimal newValue) {
        return newValue.setScale(SCALE, ROUNDING_MODE);
    }

    // BigDecimal operations
    // null is always interpreted as zero
    public static BigDecimal add(BigDecimal a, BigDecimal b) {
        if (a == null) a = BigDecimal.ZERO;
        if (b == null) b = BigDecimal.ZERO;

        BigDecimal newValue = a.add(b);
        return round(newValue);
    }

    public static BigDecimal subtract(BigDecimal a, BigDecimal b) {
        if (a == null) a = BigDecimal.ZERO;
        if (b == null) b = BigDecimal.ZERO;

        BigDecimal newValue = a.subtract(b);
        return round(newValue);
    }

    public static BigDecimal multiply(BigDecimal a, BigDecimal b) {
        if (a == null) a = BigDecimal.ZERO;
        if (b == null) b = BigDecimal.ZERO;

        BigDecimal newValue = a.multiply(b);
        return round(newValue);
    }

    public static BigDecimal divide(BigDecimal a, BigDecimal b) {
        if (a == null) a = BigDecimal.ZERO;
        if (b == null) b = BigDecimal.ZERO;

        MathContext mathContext = new MathContext(10, ROUNDING_MODE);
        BigDecimal newValue = a.divide(b, mathContext);
        return round(newValue);
    }

    public static BigDecimal truncate(BigDecimal a) {
        return newBigDecimal(a.intValue());
    }

    // BigDecimal comparisons
    // null is always interpreted as zero
    // Tests whether a > b
    public static boolean largerThan(BigDecimal a, BigDecimal b) {
        if (a == null) a = BigDecimal.ZERO;
        if (b == null) b = BigDecimal.ZERO;

        return a.compareTo(b) > 0;
    }

    // Tests whether a < b
    public static boolean smallerThan(BigDecimal a, BigDecimal b) {
        if (a == null) a = BigDecimal.ZERO;
        if (b == null) b = BigDecimal.ZERO;

        return a.compareTo(b) < 0;
    }

    // Tests whether a equals b
    public static boolean equalTo(BigDecimal a, BigDecimal b) {
        if (a == null) a = BigDecimal.ZERO;
        if (b == null) b = BigDecimal.ZERO;

        return a.compareTo(b) == 0;
    }

    // Tests whether a > 0
    public static boolean isPositive(BigDecimal a) {
        return a != null && a.signum() > 0;
    }

    // Tests whether a < 0
    public static boolean isNegative(BigDecimal a) {
        return a != null && a.signum() < 0;
    }

    // Tests whether a equals 0
    public static boolean isZero(BigDecimal a) {
        return a != null && equalTo(a, BigDecimal.ZERO);
    }
}
