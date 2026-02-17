package main.java.fr.ynov.kanoe.cli;

import main.java.fr.ynov.kanoe.enums.TypeBillet;
import main.java.fr.ynov.kanoe.model.Passenger;
import main.java.fr.ynov.kanoe.model.Reservation;
import main.java.fr.ynov.kanoe.model.Users;
import main.java.fr.ynov.kanoe.service.SystemeReservation;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UserConnectedInterface {
    private final Users user; // needs to be changed to User object when implemented
    private final SystemeReservation system;

    public UserConnectedInterface(Users user, SystemeReservation system) {
        this.user = user;
        this.system = system;
    }

    public void displayUserMenu() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== User menu, welcome back ! ===");
        System.out.println(this.user);
        System.out.println("1. Afficher les transports disponibles");
        System.out.println("2. Réserver un transport");
        System.out.println("3. Afficher mes réservations");
        System.out.println("4. Se déconnecter");

        int choice = scanner.nextInt();

        switch (choice) {
            case 1 -> {
                System.out.println("Available transports:");
                system.getTransportsDisponibles().forEach(System.out::println);
                displayUserMenu();
            }
            case 2 -> bookTransport();
            case 3 ->
                    System.out.println("Displaying my reservations..."); // TODO: call method to display user's reservations
            case 4 -> {
                System.out.println("Logging out...");
                Menu.displayMainMenu(system);
            }
        }
    }

    public void bookTransport() {
        Scanner scanner = new Scanner(System.in);
        system.getTransportsDisponibles().forEach(System.out::println);
        System.out.print("Enter the ID of the transport you want to book: ");
        String transportId = scanner.nextLine();
        if (system.searchForTransportWithID(transportId) != null) {
            List<Passenger> passengerList = new ArrayList<>();
            system.creerReservation(user, system.searchForTransportWithID(transportId), addPassagerToReservation(passengerList), chooseTypeBillet());
        }
    }

    public List<Passenger> addPassagerToReservation(List<Passenger> passengerList) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the number of passengers you want to add: ");
        int numberOfPassengers = scanner.nextInt();
        scanner.nextLine(); // Consume the newline
        for (int i = 0; i < numberOfPassengers; i++) {
            System.out.print("Enter the first name of the passenger: ");
            String firstName = scanner.nextLine();
            System.out.print("Enter the last name of the passenger: ");
            String lastName = scanner.nextLine();
            System.out.print("Enter the age of the passenger: ");
            int age = scanner.nextInt();

            scanner.nextLine(); // Consume the newline
            passengerList.add(new Passenger(lastName, firstName, age, "123456789"));
        }
        return passengerList;
    }

    public TypeBillet chooseTypeBillet() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Choose the type of ticket:");
        System.out.println("1. Economique");
        System.out.println("2. Affaire");
        System.out.println("3. Première classe");
        int choice = scanner.nextInt();
        return switch (choice) {
            case 1 -> TypeBillet.ECONOMIQUE;
            case 2 -> TypeBillet.AFFAIRE;
            case 3 -> TypeBillet.PREMIERE_CLASSE;
            default -> TypeBillet.ECONOMIQUE; // Default to ECONOMIQUE if invalid choice
        };
    }
}