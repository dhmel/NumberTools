//--------------------------------------------
// © Denis Khmel (dhmel@yandex.ru), 2021-2022
//--------------------------------------------

package numbertranslator;

import java.util.Scanner;

public class NumberConverterTest {

    private NumberConverterTest() {
    }

    public static void main(String[] args) {

        while (true) {

            System.out.print("Введите неотрицательное число: ");
            String input = new Scanner(System.in).nextLine();
            String output;

            try {
                output = NumberConverter.toRoman(input);
                System.out.println(output);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }

            try {
                output = NumberConverter.toText(input);
                System.out.println(output);
                break;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
