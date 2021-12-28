package me.enzo.armoreffects.util;

public class NumberUtils {

    public static boolean isInteger(String string) {
        try {
            Integer.parseInt(string);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    //Int to roman
    public static String toRoman(int number) {
        StringBuilder roman = new StringBuilder();

        while (number > 0) {
            if (number >= 1000) {
                roman.append("M");
                number -= 1000;
            } else if (number >= 900) {
                roman.append("CM");
                number -= 900;
            } else if (number >= 500) {
                roman.append("D");
                number -= 500;
            } else if (number >= 400) {
                roman.append("CD");
                number -= 400;
            } else if (number >= 100) {
                roman.append("C");
                number -= 100;
            } else if (number >= 90) {
                roman.append("XC");
                number -= 90;
            } else if (number >= 50) {
                roman.append("L");
                number -= 50;
            } else if (number >= 40) {
                roman.append("XL");
                number -= 40;
            } else if (number >= 10) {
                roman.append("X");
                number -= 10;
            } else if (number >= 9) {
                roman.append("IX");
                number -= 9;
            } else if (number >= 5) {
                roman.append("V");
                number -= 5;
            } else if (number >= 4) {
                roman.append("IV");
                number -= 4;
            } else {
                roman.append("I");
                number -= 1;
            }
        }
        return roman.toString();
    }

    //Roman numerals to int
    public static int romanToInt(String roman) {
        int result = 0;
        int i = 0;
        while (i < roman.length()) {
            char c = roman.charAt(i);
            if (c == 'M') {
                result += 1000;
            } else if (c == 'D') {
                result += 500;
            } else if (c == 'C') {
                if (i + 1 < roman.length() && roman.charAt(i + 1) == 'M') {
                    result += 900;
                    i++;
                } else if (i + 1 < roman.length() && roman.charAt(i + 1) == 'D') {
                    result += 400;
                    i++;
                } else {
                    result += 100;
                }
            } else if (c == 'L') {
                result += 50;
            } else if (c == 'X') {
                if (i + 1 < roman.length() && roman.charAt(i + 1) == 'C') {
                    result += 90;
                    i++;
                } else if (i + 1 < roman.length() && roman.charAt(i + 1) == 'L') {
                    result += 40;
                    i++;
                } else {
                    result += 10;
                }
            } else if (c == 'V') {
                result += 5;
            } else if (c == 'I') {
                if (i + 1 < roman.length() && roman.charAt(i + 1) == 'X') {
                    result += 9;
                    i++;
                } else if (i + 1 < roman.length() && roman.charAt(i + 1) == 'V') {
                    result += 4;
                    i++;
                } else {
                    result += 1;
                }
            }
            i++;
        }
        return result;
    }

}