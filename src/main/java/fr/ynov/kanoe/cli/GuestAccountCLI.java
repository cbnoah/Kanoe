package main.java.fr.ynov.kanoe.cli;

import main.java.fr.ynov.kanoe.model.Users;
import main.java.fr.ynov.kanoe.service.SystemeReservation;

import java.util.Scanner;

import static main.java.fr.ynov.kanoe.utils.ScannerUtils.readInt;

public class GuestAccountCLI {
    private final Scanner scanner = new Scanner(System.in);
    private final SystemeReservation system;

    public GuestAccountCLI(SystemeReservation system) {
        this.system = system;
    }

    public void displayMainMenu() {
        boolean running = true;

        while (running) {

            System.out.println("=== Main menu ===");
            System.out.println("1. Login");
            System.out.println("2. Sign in");
            System.out.println("3. Show available transports");
            System.out.println("4. Leave");

            System.out.print("Please select an option: ");
            int choice = readInt("", scanner);

            switch (choice) {
                case 1 -> connectionToUser();
                case 2 -> createAccount();
                case 3 -> {
                    System.out.println("Available transports:");
                    for (int i = 0; i < system.getTransportsDisponibles().size(); i++) {
                        System.out.println(i + 1 + " - " + system.getTransportsDisponibles().get(i));
                    }
                }
                case 4 -> running = false;
                default -> System.out.println("Invalid choice, please try again.");
            }
        }

    }

    public void connectionToUser() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your email: ");
        String email = scanner.nextLine();
        System.out.print("Enter your password: ");
        String password = scanner.nextLine();

        for (Users user : system.getUtilisateurs()) {
            if (user.getEmail().equals(email) && user.verifyPassword(password)) {
                System.out.println("Connexion successful for " + user.getFirstName() + " " + user.getLastName());
                new UserConnectedCLI(user, system).displayUserMenu();
                return;
            }
        }
        System.out.println("Invalid email or password. Please try again.");
    }

    public void createAccount() {
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
        new UserConnectedCLI(newUser, system).displayUserMenu();
    }
}