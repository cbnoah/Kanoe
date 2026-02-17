package main.java.fr.ynov.kanoe.cli;

import main.java.fr.ynov.kanoe.model.Users;
import main.java.fr.ynov.kanoe.service.SystemeReservation;

import java.util.Scanner;

public class Menu {
    public static void displayMainMenu(SystemeReservation system) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== Main menu ===");
        System.out.println("1. Login");
        System.out.println("2. Sign in");
        System.out.println("3. Show available transports");
        System.out.println("4. Leave");

        System.out.print("Please select an option: ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1 -> connectionToUser(system);
            case 2 -> createAccount(system);
            case 3 -> {
                System.out.println("Available transports:");
                system.getTransportsDisponibles().forEach(System.out::println);
                displayMainMenu(system);
            }
            case 4 -> System.exit(0);
            default -> {
                System.out.println("Invalid choice, please try again.");
                displayMainMenu(system);
            }
        }


    }

    public static void connectionToUser(SystemeReservation system) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your email: ");
        String email = scanner.nextLine();
        System.out.print("Enter your password: ");
        String password = scanner.nextLine();

        for (Users user : system.getUtilisateurs()) {
            if (user.getEmail().equals(email) && user.verifyPassword(password)) {
                System.out.println("Connexion successful for " + user.getFirstName() + " " + user.getLastName());
                new UserConnectedInterface(user, system).displayUserMenu();
                return;
            }
        }
        System.out.println("Invalid email or password. Please try again.");
        displayMainMenu(system);
    }

    public static void createAccount(SystemeReservation system) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your last name: ");
        String lastName = scanner.nextLine();
        System.out.print("Enter your first name: ");
        String firstName = scanner.nextLine();
        System.out.print("Enter yout email: ");
        String email = scanner.nextLine();
        System.out.print("Enter your phone number: ");
        String phone = scanner.nextLine();
        System.out.print("Enter your password: ");
        String password = scanner.nextLine();

        Users newUser = new Users(lastName, firstName, email, phone, password, system.getUtilisateurs().size() + 1);
        system.enregistrerUtilisateur(newUser);
        new UserConnectedInterface(newUser, system).displayUserMenu();
    }
}