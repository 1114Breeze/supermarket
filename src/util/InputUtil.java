package util;

import java.util.Scanner;

/**
 * @author test
 * @classname InputUtil
 * @date 2023/2/25 16:20
 */
public class InputUtil {
    private InputUtil() {
    }

    private static Scanner input = new Scanner(System.in);

    public static String input(String regex, String message) {
        while (true) {
            System.out.println(message);
            String str = input.next();
            if (str.matches(regex)) {
                return str;
            }
        }
    }

    public static void close() {
        input.close();
    }

    public static int inputInt() {
        return input.nextInt();
    }
}
