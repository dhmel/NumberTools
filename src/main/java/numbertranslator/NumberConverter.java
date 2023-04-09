//--------------------------------------------
// © Denis Khmel (dhmel@yandex.ru), 2021-2022
//--------------------------------------------

package numbertranslator;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public final class NumberConverter {

    private static final String[][] rubleBank = new String[][]{
            {"рубль ", "рубля ", "рублей "},
            {"тысяча ", "тысячи ", "тысяч "},
            {"миллион ", "миллиона ", "миллионов "},
            {"миллиард ", "миллиарда ", "миллиардов "},
            {"триллион ", "триллиона ", "триллионов "}
    };
    private static final String[] pennyBank = new String[]{
            " копейка",
            " копейки",
            " копеек"
    };
    private static final Map<Integer, String> numberBank = new HashMap<>();
    private static final Map<Integer, String> romanBank = new LinkedHashMap<>();

    static {
        numberBank.put(0, "");
        numberBank.put(1, "один ");
        numberBank.put(2, "два ");
        numberBank.put(3, "три ");
        numberBank.put(4, "четыре ");
        numberBank.put(5, "пять ");
        numberBank.put(6, "шесть ");
        numberBank.put(7, "семь ");
        numberBank.put(8, "восемь ");
        numberBank.put(9, "девять ");
        numberBank.put(10, "десять ");
        numberBank.put(11, "одиннадцать ");
        numberBank.put(12, "двенадцать ");
        numberBank.put(13, "тринадцать ");
        numberBank.put(14, "четырнадцать ");
        numberBank.put(15, "пятнадцать ");
        numberBank.put(16, "шестнадцать ");
        numberBank.put(17, "семнадцать ");
        numberBank.put(18, "восемнадцать ");
        numberBank.put(19, "девятнадцать ");
        numberBank.put(20, "двадцать ");
        numberBank.put(30, "тридцать ");
        numberBank.put(40, "сорок ");
        numberBank.put(50, "пятьдесят ");
        numberBank.put(60, "шестьдесят ");
        numberBank.put(70, "семьдесят ");
        numberBank.put(80, "восемьдесят ");
        numberBank.put(90, "девяносто ");
        numberBank.put(100, "сто ");
        numberBank.put(200, "двести ");
        numberBank.put(300, "триста ");
        numberBank.put(400, "четыреста ");
        numberBank.put(500, "пятьсот ");
        numberBank.put(600, "шестьсот ");
        numberBank.put(700, "семьсот ");
        numberBank.put(800, "восемьсот ");
        numberBank.put(900, "девятьсот ");
    }

    static {
        romanBank.put(1000, "M");
        romanBank.put(900, "CM");
        romanBank.put(500, "D");
        romanBank.put(400, "CD");
        romanBank.put(100, "C");
        romanBank.put(90, "XC");
        romanBank.put(50, "L");
        romanBank.put(40, "XL");
        romanBank.put(10, "X");
        romanBank.put(9, "IX");
        romanBank.put(5, "V");
        romanBank.put(4, "IV");
        romanBank.put(1, "I");
    }

    private NumberConverter() {
    }

    public static String toRoman(String input) throws IllegalArgumentException {

        if (!input.matches("\\d{1,4}")) throw new IllegalArgumentException("Incorrect number format");

        int number = Integer.parseInt(input);
        if (number < 1 || number > 3999) throw new IllegalArgumentException("Number has to be from 1 to 3999");

        String result = "";

        for (int i : romanBank.keySet()) {
            while (number >= i) {
                result = result + romanBank.get(i);
                number -= i;
            }
        }
        return result;
    }

    private static String convertRubles(String input) {

        if (input.equals("")) return "Ноль " + rubleBank[0][2];

        StringBuilder number = new StringBuilder(input);
        StringBuilder result = new StringBuilder();

        int i = -1;

        while (number.length() > 0) {
            int len = Integer.min(3, number.length());
            int rubles = Integer.parseInt(number.substring(number.length() - len));

            number = new StringBuilder(number.substring(0, number.length() - len));

            i += 1;

            if (rubles == 0 && i > 0) continue;

            if (rubles % 100 / 10 != 1 && rubles % 10 == 1) {
                result.insert(0, rubleBank[i][0]);
            } else if ((rubles % 100 / 10 != 1) && (rubles % 10 >= 2 && rubles % 10 <= 4)) {
                result.insert(0, rubleBank[i][1]);
            } else {
                result.insert(0, rubleBank[i][2]);
            }

            if (numberBank.containsKey(rubles % 100)) {
                result.insert(0, numberBank.get(rubles % 100));
            } else {
                result.insert(0, numberBank.get(rubles % 10));
                result.insert(0, numberBank.get(rubles % 100 / 10 * 10));
            }

            result.insert(0, numberBank.get(rubles / 100 * 100));
        }

        result = new StringBuilder(result.toString().replace("один тысяча", "одна тысяча"));
        result = new StringBuilder(result.toString().replace("два тысячи", "две тысячи"));

        return result.substring(0, 1).toUpperCase() + result.substring(1);
    }

    private static String convertPennies(String number) {

        int pennies = Integer.parseInt(number);
        if (number.length() < 2) pennies *= 10;

        StringBuilder result = new StringBuilder();

        if (pennies / 10 != 1 && pennies % 10 == 1) {
            result.append(pennyBank[0]);
        } else if (pennies / 10 != 1 && (pennies % 10 >= 2 && pennies % 10 <= 4)) {
            result.append(pennyBank[1]);
        } else {
            result.append(pennyBank[2]);
        }

        result.insert(0, pennies);

        if (pennies < 10) result.insert(0, "0");

        return new String(result);
    }

    private static String[] decodeInput(String input) throws IllegalArgumentException {

        if (input.equals("")) throw new IllegalArgumentException("Empty input");

        if (!input.matches("(\\d*)(|[,.]?\\d{1,2})")) {
            throw new IllegalArgumentException("Incorrect number format");
        }

        String[] number = (input + ".0").split("[,.]");

        number[0] = number[0].replaceAll("^0*", "");

        if (number[0].length() > 3 * rubleBank.length) {
            throw new IllegalArgumentException(
                    "The maximum allowed value 999 "
                            + rubleBank[rubleBank.length - 1][2]
                            + "has been exceeded"
            );
        }
        return number;
    }

    public static String toText(String input) throws IllegalArgumentException {

        String[] number = decodeInput(input);

        return convertRubles(number[0]) + convertPennies(number[1]);
    }
}
