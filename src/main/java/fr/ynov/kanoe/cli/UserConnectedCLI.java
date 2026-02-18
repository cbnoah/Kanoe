// java
package main.java.fr.ynov.kanoe.cli;

import main.java.fr.ynov.kanoe.enums.MethodPayment;
import main.java.fr.ynov.kanoe.enums.TypeBillet;
import main.java.fr.ynov.kanoe.model.Passenger;
import main.java.fr.ynov.kanoe.model.Reservation;
import main.java.fr.ynov.kanoe.model.Transport;
import main.java.fr.ynov.kanoe.model.Users;
import main.java.fr.ynov.kanoe.service.SystemeReservation;
import main.java.fr.ynov.kanoe.utils.NotificationCreator;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UserConnectedCLI {
    private final Users user;
    private final SystemeReservation system;
    private final Scanner scanner = new Scanner(System.in);

    public UserConnectedCLI(Users user, SystemeReservation system) {
        this.user = user;
        this.system = system;
    }

    public void displayUserMenu() {
        boolean running = true;

        while (running) {
            System.out.println("=== User menu, welcome back ! ===");
            System.out.println(this.user);
            System.out.println("1. Available transports");
            System.out.println("2. Book a transport");
            System.out.println("3. Manage my reservations");
            System.out.println("=== Administrative options ===");
            System.out.println("4. Create a notification");
            System.out.println("===================================");
            System.out.println("5. Log out");

            int choice = readInt("Choose an option: ");

            switch (choice) {
                case 1 -> {
                    System.out.println("Available transports:");
                    for (int i = 0; i < system.getTransportsDisponibles().size(); i++) {
                        System.out.println(i + 1 + " - " + system.getTransportsDisponibles().get(i));
                    }
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
                        int reservationChoice = readInt("");
                        if (reservationChoice > 0 && reservationChoice <= userReservations.size()) {
                            new BookingHandling(userReservations.get(reservationChoice - 1)).ManageOneBooking();
                        } else {
                            System.out.println("Invalid reservation number.");
                        }
                    }
                }
                case 4 -> {
                    System.out.println("=== Create a notification ===");
                    System.out.println("Enter the notification details:");
                    System.out.print("Title: ");
                    String title = scanner.nextLine();
                    System.out.print("Message: ");
                    String message = scanner.nextLine();
                    System.out.print("Scope (transport ID): ");
                    String scope = scanner.nextLine();
                    new NotificationCreator(system).createNotification(title, message, scope);
                }
                case 5 -> {
                    System.out.println("Logging out...");
                    running = false;
                }
                default -> System.out.println("Invalid choice, please try again.");
            }
        }
    }

    public void bookTransport() {
        for (int i = 0; i < system.getTransportsDisponibles().size(); i++) {
            System.out.println(i + 1 + " - " + system.getTransportsDisponibles().get(i));
        }
        System.out.print("Enter the ID of the transport you want to book: ");
        String transportId = scanner.nextLine().trim();
        Transport selectedTransport = null;
        if (system.searchForTransportWithID(transportId) != null) {
            selectedTransport = system.searchForTransportWithID(transportId);
        } else {
            try {
                int idx = Integer.parseInt(transportId) - 1;
                if (idx >= 0 && idx < system.getTransportsDisponibles().size()) {
                    selectedTransport = system.getTransportsDisponibles().get(idx);
                }
            } catch (NumberFormatException ignored) {
            }
        }
        if (selectedTransport != null && selectedTransport.getAvailableSeats() > 0) {
            List<Passenger> passengerList = new ArrayList<>();
            system.creerReservation(user, selectedTransport, addPassagerToReservation(passengerList), chooseTypeBillet(), chooseMethodPayment());
            return;
        }
        System.out.println("Invalid transport ID or no available seats. Please try again.");
    }

    public List<Passenger> addPassagerToReservation(List<Passenger> passengerList) {
        int numberOfPassengers;
        while (true) {
            numberOfPassengers = readInt("Enter the number of passengers you want to add: ");
            if (numberOfPassengers > 0) break;
            System.out.println("Invalid number of passengers. Please try again.");
        }

        for (int i = 0; i < numberOfPassengers; i++) {
            System.out.println("Passenger " + (i + 1) + ":");
            System.out.print("Enter the first name of the passenger: ");
            String firstName = scanner.nextLine().trim();
            System.out.print("Enter the last name of the passenger: ");
            String lastName = scanner.nextLine().trim();

            int age;
            while (true) {
                age = readInt("Enter the age of the passenger: ");
                if (age >= 0) break;
                System.out.println("Please enter a valid age.");
            }
            passengerList.add(new Passenger(lastName, firstName, age, "123456789"));
        }
        return passengerList;
    }

    public TypeBillet chooseTypeBillet() {
        System.out.println("Choose the type of ticket:");
        System.out.println("1. Economique");
        System.out.println("2. Affaire");
        System.out.println("3. Première classe");
        int choice = readInt("");
        return switch (choice) {
            case 2 -> TypeBillet.AFFAIRE;
            case 3 -> TypeBillet.PREMIERE_CLASSE;
            default -> TypeBillet.ECONOMIQUE;
        };
    }

    public MethodPayment chooseMethodPayment() {
        System.out.println("Choose the method of payment:");
        System.out.println("1. Credit card");
        System.out.println("2. PayPal");
        System.out.println("3. Bank transfer");
        int choice = readInt("");
        return switch (choice) {
            case 2 -> MethodPayment.PAYPAL;
            case 3 -> MethodPayment.BANK_TRANSFER;
            default -> MethodPayment.CREDIT_CARD;
        };
    }

    public int readInt(String prompt) {
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
