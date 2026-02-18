package main.java.fr.ynov.kanoe.cli;

import main.java.fr.ynov.kanoe.enums.MethodPayment;
import main.java.fr.ynov.kanoe.enums.TypeBillet;
import main.java.fr.ynov.kanoe.model.Passenger;
import main.java.fr.ynov.kanoe.model.Reservation;
import main.java.fr.ynov.kanoe.model.Transport;
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
        System.out.println("1. Available transports");
        System.out.println("2. Book a transport");
        System.out.println("3. Manage my reservations");
        System.out.println("4. Log out");

        int choice = scanner.nextInt();

        switch (choice) {
            case 1 -> {
                System.out.println("Available transports:");
                for (int i = 0; i < system.getTransportsDisponibles().size(); i++) {
                    System.out.println(i + 1 + " - " + system.getTransportsDisponibles().get(i));
                }
                displayUserMenu();
            }
            case 2 -> bookTransport();
            case 3 -> {
                List<Reservation> userReservations = system.getReservations().stream()
                        .filter(r -> r.getUserId() == user.getId())
                        .toList();
                if (userReservations.isEmpty()) {
                    System.out.println("You have no reservations.");
                } else {
                    System.out.println("Your reservations:");
                    for (int i = 0; i < userReservations.size(); i++) {
                        System.out.println(i + 1 + " - " + userReservations.get(i));
                    }
                    System.out.print("Enter the index of the reservation you want to manage: ");
                    int reservationChoice = scanner.nextInt();
                    if (reservationChoice > 0 && reservationChoice <= userReservations.size()) {
                        new BookingHandling(userReservations.get(reservationChoice - 1)).ManageOneBooking();
                    } else {
                        System.out.println("Invalid reservation number.");
                    }
                }
                displayUserMenu();
            }
            case 4 -> {
                System.out.println("Logging out...");
                Menu.displayMainMenu(system);
            }
        }
    }

    public void bookTransport() {
        Scanner scanner = new Scanner(System.in);
        for (int i = 0; i < system.getTransportsDisponibles().size(); i++) {
            System.out.println(i + 1 + " - " + system.getTransportsDisponibles().get(i));
        }
        System.out.print("Enter the ID of the transport you want to book: ");
        String transportId = scanner.nextLine();
        Transport selectedTransport = null;
        if (system.searchForTransportWithID(transportId) != null) {
            selectedTransport = system.searchForTransportWithID(transportId);
        } else if (system.getTransportsDisponibles().get(Integer.parseInt(transportId) - 1) != null) {
            selectedTransport = system.getTransportsDisponibles().get(Integer.parseInt(transportId) - 1);
        }
        if (selectedTransport != null && selectedTransport.getAvailableSeats() > 0) {
            List<Passenger> passengerList = new ArrayList<>();
            system.creerReservation(user, selectedTransport, addPassagerToReservation(passengerList), chooseTypeBillet(), chooseMethodPayment());
            displayUserMenu();
        }
        System.out.println("Invalid transport ID or no available seats. Please try again.");
        displayUserMenu();
    }

    public List<Passenger> addPassagerToReservation(List<Passenger> passengerList) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the number of passengers you want to add: ");
        int numberOfPassengers = scanner.nextInt();
        if (numberOfPassengers <= 0) {
            System.out.println("Invalid number of passengers. Please try again.");
            return addPassagerToReservation(passengerList);
        }
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

    public MethodPayment chooseMethodPayment() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Choose the method of payment:");
        System.out.println("1. Credit card");
        System.out.println("2. PayPal");
        System.out.println("3. Bank transfer");
        int choice = scanner.nextInt();
        return switch (choice) {
            case 1 -> MethodPayment.CREDIT_CARD;
            case 2 -> MethodPayment.PAYPAL;
            case 3 -> MethodPayment.BANK_TRANSFER;
            default -> MethodPayment.CREDIT_CARD; // Default to CREDIT_CARD if invalid choice
        };
    }
}