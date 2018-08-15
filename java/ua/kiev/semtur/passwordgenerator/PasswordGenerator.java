package ua.kiev.semtur.passwordgenerator;

import java.security.SecureRandom;

/**
 * Created by SemTur on 31.08.2017.
 */

public class PasswordGenerator {
    private static final char[] sNumbers = {'1', '2', '3', '4', '5', '6', '7', '8', '9', '0'};
    private static final char[] sUpperCase = {'Q', 'W', 'E', 'R', 'T', 'Y', 'U', 'I', 'O', 'P',
            'A', 'S', 'D', 'F', 'G', 'H', 'J', 'K', 'L', 'Z', 'X', 'C', 'V', 'B', 'N', 'M'};
    private static final char[] sLowerCase = {'q', 'w', 'e', 'r', 't', 'y', 'u', 'i', 'o', 'p',
            'a', 's', 'd', 'f', 'g', 'h', 'j', 'k', 'l', 'z', 'x', 'c', 'v', 'b', 'n', 'm'};
    private static final char[] sSpecialChars = {'`', '~', '!', '@', '#', '$', '%', '^', '&', '*', '(', ')',
            '-', '_', '=', '+', '[', '{', ']', '}', ';', ':', '\\', '|', ',', '<', '.', '>', '/', '?', '\"'};
    private static final String sExcludeSimilarChars = "I|1lO0S5";

    public static String generatePassword(char[] initialCharSet, int passwordLength, boolean isDoNotRepeatChars, boolean isExcludeSimilarChars) {
        if (initialCharSet == null || passwordLength == 0) {
            return null;
        }
        StringBuilder stringBuilder = new StringBuilder();
        SecureRandom secureRandom = new SecureRandom();
        int i = 0;
        while (i < passwordLength) {
            int index = secureRandom.nextInt(initialCharSet.length - 1);
            char c = initialCharSet[index];
            if (isDoNotRepeatChars) {
                if (stringBuilder.indexOf(String.valueOf(c)) >= 0)
                    continue;
            }
            if (isExcludeSimilarChars) {
                if (sExcludeSimilarChars.contains(String.valueOf(c)))
                    continue;
            }
            stringBuilder.append(c);
            i++;
        }
        return stringBuilder.toString();
    }

    public static char[] getInitialCharSet(boolean numbers, boolean upperCase, boolean lowerCase,
                                           boolean specialChars) {
        char[] initialCharSet = null;
        StringBuilder stringBuilder = new StringBuilder();
        if (numbers) {
            stringBuilder.append(sNumbers);
        }
        if (upperCase) {
            stringBuilder.append(sUpperCase);
        }
        if (lowerCase) {
            stringBuilder.append(sLowerCase);
        }
        if (specialChars) {
            stringBuilder.append(sSpecialChars);
        }
        int length = stringBuilder.length();
        if (length > 0) {
            initialCharSet = new char[length];
            stringBuilder.getChars(0, length, initialCharSet, 0);
        }
        shuffle(initialCharSet);
        return initialCharSet;
    }

    public static int getPasswordStrength(int charSet, int passwordLength) {
        return (int) (Math.log(charSet) * (passwordLength / Math.log(2)));
    }

    private static void shuffle(char[] chars) {
        int index;
        char c;
        SecureRandom secureRandom = new SecureRandom();

        for (int i = chars.length - 1; i > 0; i--) {
            index = secureRandom.nextInt(i + 1);
            if (index != i) {
                c = chars[index];
                chars[index] = chars[i];
                chars[i] = c;
            }
        }
    }
}
