package main.java.fr.ynov.kanoe.utils;

public class ScannerUtils {
    public static int readInt(String prompt, java.util.Scanner scanner) {
        if (prompt != null && !prompt.isEmpty()) System.out.print(prompt);
        while (!scanner.hasNextInt()) {
            System.out.println("Please enter a valid integer.");
            scanner.nextLine();
            System.out.print(prompt);
        }
        int val = scanner.nextInt();
        scanner.nextLine(); // consume newline
        return val;
    }
}
