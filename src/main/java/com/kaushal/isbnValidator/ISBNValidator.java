package com.kaushal.isbnValidator;

public class ISBNValidator {

    public static final int SHORT_ISBN = 10;
    public static final int LONG_ISBN = 13;
    public static final int LONG_ISBN_MOD = 10;
    public static final int SHORT_ISBN_MOD = 11;

    static String stripOfExtraChars(String chars, String given) {
        String newString = "";
        for (char c : given.toCharArray()) {
            if (chars.contains(c + "")) continue;
            newString += c;
        }
        return newString;
    }

    static boolean checkIfValidShortISBN(String isbn) {
        long total = 0;
        for (int i = 0; i < SHORT_ISBN; i++) {
            total += Character.getNumericValue(isbn.charAt(i)) * (10 - i);
        }
        return total % SHORT_ISBN_MOD == 0;
    }

    static boolean checkIfValidLongISBN(String isbn) {
        long total = 0;
        for (int i = 0; i < LONG_ISBN; i++) {
            if (i % 2 == 1) {
                total += Character.getNumericValue(isbn.charAt(i)) * 3;
            } else {
                total += Character.getNumericValue(isbn.charAt(i));
            }
        }
        return total % LONG_ISBN_MOD == 0;
    }

    public static boolean validate(String isbn) {
        isbn = stripOfExtraChars("- ", isbn);
        if (isbn.length() == SHORT_ISBN) {
            return checkIfValidShortISBN(isbn);
        } else if (isbn.length() == LONG_ISBN) {
            return checkIfValidLongISBN(isbn);
        }
        throw new IllegalArgumentException("Number should be either 10 or 13 digit long.");
    }
}
