import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("input: ");
        var input = scanner.nextLine();

        var result = calc(input);

        System.out.println(result);
    }

    public static String calc(String input)
    {
        StringBuilder first = new StringBuilder();
        int i;
        boolean isRoman = false;

        for (i = 0; i<input.length(); i++){
            if (input.charAt(i)==' ' || input.charAt(i)=='+' || input.charAt(i)=='-' || input.charAt(i)=='*' || input.charAt(i)=='/'){
                break;
            }

            first.append(input.charAt(i));
        }

        int firstNumber;
        if (isRoman(first.toString())) {
            firstNumber = transformToArabic(first.toString());
            isRoman = true;
        }
        else {
            firstNumber = Integer.parseInt(first.toString());
        }

        char sign = input.charAt(++i);

        i += 2;

        StringBuilder second = new StringBuilder();

        for (; i<input.length(); i++){
            if (input.charAt(i)==' ' || input.charAt(i)=='+' || input.charAt(i)=='-' || input.charAt(i)=='*' || input.charAt(i)=='/'){
                break;
            }

            second.append(input.charAt(i));
        }

        int secondNumber;

        if (isRoman(second.toString())) {
            secondNumber = transformToArabic(second.toString());
            isRoman = true;
        }
        else {
            secondNumber = Integer.parseInt(second.toString());
        }

        if (firstNumber > 10 || secondNumber > 10) {
            throw new IllegalArgumentException("Unexpected value");
        }

        Integer result;
        switch (sign) {
            case '+':
                result = firstNumber + secondNumber;
                break;
            case '-':
                result = firstNumber - secondNumber;
                break;
            case '*': // case это условие
                result = firstNumber * secondNumber;
                break;
            case '/': // case это условие
                result = firstNumber / secondNumber;
                break;
            default:
                throw new IllegalArgumentException("Unexpected value");
        }

        if (isRoman)
            return transformToRoman(result);

        return result.toString();
    }

    private static boolean isRoman(String number)
    {
        if (number.charAt(0) < '0' || number.charAt(0) > '9') {
            return true;
        }

        return false;
    }

    public static int transformToArabic(String roman_numeral) {
        Map<Character, Integer> roman_char_dict = new HashMap<>();
        roman_char_dict.put('I', 1);
        roman_char_dict.put('V', 5);
        roman_char_dict.put('X', 10);
        roman_char_dict.put('L', 50);
        roman_char_dict.put('C', 100);
        roman_char_dict.put('D', 500);
        roman_char_dict.put('M', 1000);

        int res = 0;
        for (int i = 0; i < roman_numeral.length(); i += 1) {
            if (i == 0 || roman_char_dict.get(roman_numeral.charAt(i)) <= roman_char_dict.get(roman_numeral.charAt(i - 1)))
                res += roman_char_dict.get(roman_numeral.charAt(i));
            else
                res += roman_char_dict.get(roman_numeral.charAt(i)) - 2 * roman_char_dict.get(roman_numeral.charAt(i - 1));
        }
        return res;
    }
    public static String transformToRoman(int num) {
        var keys = new String[] { "M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I" };
        var vals = new int[] { 1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1 };

        StringBuilder ret = new StringBuilder();
        int ind = 0;

        while(ind < keys.length)
        {
            while(num >= vals[ind])
            {
                var d = num / vals[ind];
                num = num % vals[ind];
                for(int i=0; i<d; i++)
                    ret.append(keys[ind]);
            }
            ind++;
        }

        return ret.toString();
    }
}