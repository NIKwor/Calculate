import java.util.*;
import java.util.stream.Collectors;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String... args) {
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
            var currentChar = input.charAt(i);
            if (currentChar == ' ' || currentChar == '+' || currentChar == '-' || currentChar == '*' || currentChar == '/'){
                break;
            }

            first.append(currentChar);
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
            if (!isRoman) {
                throw new IllegalArgumentException("Can't calculate for arabic and roman args.");
            }

            secondNumber = transformToArabic(second.toString());
            isRoman = true;
        }
        else {
            if (isRoman) {
                throw new IllegalArgumentException("Can't calculate for arabic and roman args.");
            }

            secondNumber = Integer.parseInt(second.toString());
        }

        if (i < input.length() - 1)
        {
            throw new IllegalArgumentException("Incorrect input.");
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
            case '*':
                result = firstNumber * secondNumber;
                break;
            case '/':
                result = firstNumber / secondNumber;
                break;
            default:
                throw new IllegalArgumentException("Unexpected value");
        }

        if (isRoman){
            if (result <= 0){
                throw new IllegalArgumentException("Result is lower than or equals to 0.");
            }

            return transformToRoman(result);
        }

        return result.toString();
    }

    private static boolean isRoman(String number)
    {
        if (number.charAt(0) < '0' || number.charAt(0) > '9') {
            return true;
        }

        return false;
    }

    public static int transformToArabic(String romanNumeral) {
        var isValid = ValidateRoman(romanNumeral);

        if (!isValid) {
            throw new IllegalArgumentException("Invalid input string.");
        }

        int res = 0;
        boolean isExcepted = true;

        for (int i = romanNumeral.length() - 1; i > 0; i--)
        {
            int num;

            try
            {
                num = RomanNumeral.valueOf(romanNumeral.substring(i - 1, i + 1)).getValue();
                isExcepted = false;
            }
            catch (Exception e)
            {
                num = RomanNumeral.valueOf(
                        Character.toString(romanNumeral.charAt(i))
                ).getValue();
            }

            res += num;
        }

        if (isExcepted)
        {
            res += RomanNumeral.valueOf(
                    Character.toString(romanNumeral.charAt(0))
            ).getValue();
        }

        return res;
    }
    private static boolean ValidateRoman(String romanNumeral) {
        // Не придумал решщения лучше.
        List<String> invalidRoman = new ArrayList<>()
        {{
            add("IIII");
            add("IIIII");
            add("IIIIII");
            add("IIIIIII");
            add("IIIIIIII");
            add("IIIIIIIII");
            add("VIIII");
            add("VIIIII");
            add("VIIIIII");
            add("IM");
            add("ID");
        }};

        for (String invalid: invalidRoman) {
            if (romanNumeral.contains(invalid))
                return false;
        }

        return true;
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

enum RomanNumeral {
    I(1), IV(4), V(5), IX(9), X(10),
    XL(40), L(50), XC(90), C(100),
    CD(400), D(500), CM(900), M(1000);

    private int value;

    RomanNumeral(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}