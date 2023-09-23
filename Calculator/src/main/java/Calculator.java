import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Calculator {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите выражение (например, 2 + 2 или II + II):");
        String input = scanner.nextLine().trim();

        try {
            String[] tokens = input.split(" ");
            if (tokens.length != 3) {
                throw new IllegalArgumentException("Неправильный формат ввода. Используйте: число оператор число");
            }

            String leftToken = tokens[0];
            String operator = tokens[1];
            String rightToken = tokens[2];

            int num1, num2;
            boolean isRoman = isRomanNumber(leftToken) && isRomanNumber(rightToken);

            if (isRoman) {
                num1 = romanToArabic(leftToken);
                num2 = romanToArabic(rightToken);
            } else {
                num1 = Integer.parseInt(leftToken);
                num2 = Integer.parseInt(rightToken);

                if (num1 < 1 || num1 > 10 || num2 < 1 || num2 > 10) {
                    throw new IllegalArgumentException("Вводите числа только от 1 до 10 включительно.");
                }
            }

            int result;
            switch (operator) {
                case "+":
                    result = num1 + num2;
                    break;
                case "-":
                    result = num1 - num2;
                    break;
                case "*":
                    result = num1 * num2;
                    break;
                case "/":
                    if (num2 == 0) {
                        throw new ArithmeticException("Деление на ноль недопустимо.");
                    }
                    result = num1 / num2;
                    break;
                default:
                    throw new IllegalArgumentException("Недопустимая операция. Используйте +, -, *, или /.");
            }

            String output = isRoman ? arabicToRoman(result) : Integer.toString(result);
            System.out.println("Результат: " + output);
        } catch (NumberFormatException e) {
            System.err.println("Ошибка: Неправильный формат числа.");
        } catch (IllegalArgumentException | ArithmeticException e) {
            System.err.println("Ошибка: " + e.getMessage());
        }
    }

    private static boolean isRomanNumber(String input) {
        return input.matches("^[IVXLCDM]+$");
    }

    private static int romanToArabic(String input) {
        Map<Character, Integer> romanMap = new HashMap<>();
        romanMap.put('I', 1);
        romanMap.put('V', 5);
        romanMap.put('X', 10);
        romanMap.put('L', 50);
        romanMap.put('C', 100);
        romanMap.put('D', 500);
        romanMap.put('M', 1000);

        int result = 0;
        int prevValue = 0;

        for (int i = input.length() - 1; i >= 0; i--) {
            int currentValue = romanMap.get(input.charAt(i));
            if (currentValue < prevValue) {
                result -= currentValue;
            } else {
                result += currentValue;
            }
            prevValue = currentValue;
        }

        if (!input.equals(arabicToRoman(result))) {
            throw new IllegalArgumentException("Результат римской операции должен быть положительным числом.");
        }

        return result;
    }

    private static String arabicToRoman(int num) {
        if (num <= 0) {
            throw new IllegalArgumentException("Результат римской операции должен быть положительным числом.");
        }

        String[] romanSymbols = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};
        int[] arabicValues = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
        StringBuilder result = new StringBuilder();

        int i = 0;
        while (num > 0) {
            while (num >= arabicValues[i]) {
                result.append(romanSymbols[i]);
                num -= arabicValues[i];
            }
            i++;
        }

        return result.toString();
    }
}
