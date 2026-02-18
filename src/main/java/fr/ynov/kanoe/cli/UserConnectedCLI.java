// java
package main.java.fr.ynov.kanoe.cli;

import main.java.fr.ynov.kanoe.enums.MethodPayment;
import main.java.fr.ynov.kanoe.enums.TicketType;
import main.java.fr.ynov.kanoe.model.Passenger;
import main.java.fr.ynov.kanoe.model.Booking;
import main.java.fr.ynov.kanoe.model.Transport;
import main.java.fr.ynov.kanoe.model.User;
import main.java.fr.ynov.kanoe.service.BookingSystem;
import main.java.fr.ynov.kanoe.utils.NotificationCreator;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static main.java.fr.ynov.kanoe.utils.ScannerUtils.readInt;

public class UserConnectedCLI {
    private final User user;
    private final BookingSystem system;
    private final Scanner scanner = new Scanner(System.in);

    public UserConnectedCLI(User user, BookingSystem system) {
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

            int choice = readInt("Choose an option: ", scanner);

            switch (choice) {
                case 1 -> {
                    System.out.println("Available transports:");
                    for (int i = 0; i < system.getTransportsDisponibles().size(); i++) {
                        System.out.println(i + 1 + " - " + system.getTransportsDisponibles().get(i));
                    }
                }
                case 2 -> bookTransport();
                case 3 -> {
                    List<Booking> userBookings = system.getReservations().stream()
                            .filter(r -> r.getUserId() == user.getId())
                            .toList();
                    if (userBookings.isEmpty()) {
                        System.out.println("You have no reservations.");
                    } else {
                        System.out.println("Your reservations:");
                        for (int i = 0; i < userBookings.size(); i++) {
                            System.out.println(i + 1 + " - " + userBookings.get(i));
                        }
                        System.out.print("Enter the index of the reservation you want to manage: ");
                        int reservationChoice = readInt("", scanner);
                        if (reservationChoice > 0 && reservationChoice <= userBookings.size()) {
                            new BookingHandling(userBookings.get(reservationChoice - 1)).ManageOneBooking();
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
            numberOfPassengers = readInt("Enter the number of passengers you want to add: ", scanner);
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
                age = readInt("Enter the age of the passenger: ", scanner);
                if (age >= 0) break;
                System.out.println("Please enter a valid age.");
            }
            passengerList.add(new Passenger(lastName, firstName, age, "123456789"));
        }
        return passengerList;
    }

    public TicketType chooseTypeBillet() {
        System.out.println("Choose the type of ticket:");
        System.out.println("1. Economique");
        System.out.println("2. Affaire");
        System.out.println("3. Première classe");
        int choice = readInt("", scanner);
        return switch (choice) {
            case 2 -> TicketType.BUSINESS;
            case 3 -> TicketType.FIRST_CLASS;
            default -> TicketType.ECO;
        };
    }

    public MethodPayment chooseMethodPayment() {
        System.out.println("Choose the method of payment:");
        System.out.println("1. Credit card");
        System.out.println("2. PayPal");
        System.out.println("3. Bank transfer");
        int choice = readInt("", scanner);
        return switch (choice) {
            case 2 -> MethodPayment.DEBIT_CARD;
            case 3 -> MethodPayment.PAYPAL;
            case 4 -> MethodPayment.BANK_TRANSFER;
            default -> MethodPayment.CREDIT_CARD;
        };
    }
}
