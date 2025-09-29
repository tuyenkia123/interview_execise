package vn.com.execise.shoppingservice.shared;

import java.util.Scanner;

public final class ScannerUtils {

    private static final Scanner SCANNER = new Scanner(System.in);

    private ScannerUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static String getStringInput(String input) {
        System.out.print(input);
        return SCANNER.nextLine().trim();
    }

    public static int getIntInput(String input) {
        while (true) {
            try {
                System.out.print(input);
                return Integer.parseInt(SCANNER.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Vui lòng nhập một số hợp lệ");
            }
        }
    }
}
