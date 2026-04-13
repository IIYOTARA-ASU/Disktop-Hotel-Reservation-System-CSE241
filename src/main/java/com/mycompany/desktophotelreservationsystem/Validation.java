package com.mycompany.desktophotelreservationsystem;

import java.util.Scanner;

public class Validation {

	public static int getInt(Scanner scanner, String prompt) {
		while (true) {
			System.out.print(prompt);
			String input = scanner.nextLine().trim();
			if (input.isEmpty()) {
				System.out.println("   [Error] Input cannot be empty. Please enter a number.");
				continue;
			}
			try {
				return Integer.parseInt(input);
			} catch (NumberFormatException e) {
				System.out.println("   [Error] Invalid input. Please enter a valid integer.");
			}
		}
	}

	public static int getIntInRange(Scanner scanner, String prompt, int min, int max) {
		while (true) {
			System.out.print(prompt);
			String input = scanner.nextLine().trim();
			if (input.isEmpty()) {
				System.out.println("   [Error] Input cannot be empty. Please enter a number between " + min + " and " + max + ".");
				continue;
			}
			try {
				int value = Integer.parseInt(input);
				if (value >= min && value <= max) { return value; }
				System.out.println("   [Error] Please enter a number between " + min + " and " + max + ".");
			} catch (NumberFormatException e) {
				System.out.println("   [Error] Invalid input. Please enter a valid integer.");
			}
		}
	}

	public static double getDouble(Scanner scanner, String prompt) {
		while (true) {
			System.out.print(prompt);
			String input = scanner.nextLine().trim();
			if (input.isEmpty()) {
				System.out.println("   [Error] Input cannot be empty. Please enter a number.");
				continue;
			}
			try {
				return Double.parseDouble(input);
			} catch (NumberFormatException e) {
				System.out.println("   [Error] Invalid input. Please enter a valid decimal number.");
			}
		}
	}

	public static String getString(Scanner scanner, String prompt) {
		while (true) {
			System.out.print(prompt);
			String input = scanner.nextLine().trim();
			if (!input.isEmpty()) { return input; }
			System.out.println("   [Error] Input cannot be empty. Please try again.");
		}
	}

	public static int getOption(Scanner scanner, int maxOption, String prompt) {
		while (true) {
			System.out.print(prompt);
			String input = scanner.nextLine().trim();
			if (input.isEmpty()) {
				System.out.println("   [Error] Input cannot be empty. Please enter a number between 1 and " + maxOption + ".");
				continue;
			}
			try {
				int option = Integer.parseInt(input);
				if (option >= 1 && option <= maxOption) { return option; }
				System.out.println("   [Error] Invalid option. Please enter a number between 1 and " + maxOption + ".");
			} catch (NumberFormatException e) {
				System.out.println("   [Error] Invalid input. Please enter a number between 1 and " + maxOption + ".");
			}
		}
	}

	public static boolean getYesNo(Scanner scanner, String prompt) {
		while (true) {
			System.out.print(prompt);
			String input = scanner.nextLine().trim().toLowerCase();
			if (input.equals("y")) { return true;  }
			if (input.equals("n")) { return false; }
			System.out.println("   [Error] Invalid input. Please enter 'y' or 'n'.");
		}
	}

	public static void centerText(String text, int width) {
		if (text.length() >= width) { System.out.println(text); return; }
		int padding = width - text.length();
		int left    = padding / 2;
		System.out.println(" ".repeat(left) + text);
	}
}