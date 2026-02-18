package main.java.fr.ynov.kanoe.cli;

import main.java.fr.ynov.kanoe.enums.StatusReservation;
import main.java.fr.ynov.kanoe.model.Booking;

import java.util.Scanner;

public class BookingHandling {
    Booking booking;

    public BookingHandling(Booking booking) {
        this.booking = booking;
    }

    public void ManageOneBooking() {
        Scanner scanner = new Scanner(System.in);
        if (booking.getStatut() == StatusReservation.CANCELED) {
            System.out.println("This reservation is already canceled.");
            return;
        }
        if (this.booking.getStatut() == StatusReservation.SUCCESS) {
            System.out.println("This reservation is already confirmed.");
            return;
        }
        System.out.println("=== Booking management ===");
        System.out.println("1. Confirm reservation");
        System.out.println("2. Cancel reservation");
        System.out.println("3. Back to user menu");
        int choice = scanner.nextInt();
        switch (choice) {
            case 1 -> {
                if (booking.processPayment()) {
                    System.out.println("Reservation confirmed!");
                } else {
                    System.out.println("Payment failed, reservation not confirmed.");
                }
            }
            case 2 -> {
                booking.annuler();
                booking.getTransport().freeSeat(booking.getPassengerList().size());
                System.out.println("Reservation canceled.");
            }
        }
    }
}
