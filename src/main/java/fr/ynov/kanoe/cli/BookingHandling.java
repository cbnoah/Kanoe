package main.java.fr.ynov.kanoe.cli;

import main.java.fr.ynov.kanoe.enums.StatusReservation;
import main.java.fr.ynov.kanoe.model.Reservation;

import java.util.Scanner;

public class BookingHandling {
    Reservation reservation;

    public BookingHandling(Reservation reservation) {
        this.reservation = reservation;
    }

    public void ManageOneBooking() {
        Scanner scanner = new Scanner(System.in);
        if (reservation.getStatut() == StatusReservation.CANCELED) {
            System.out.println("This reservation is already canceled.");
            return;
        }
        if (this.reservation.getStatut() == StatusReservation.SUCCESS) {
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
                if (reservation.processPayment()) {
                    System.out.println("Reservation confirmed!");
                } else {
                    System.out.println("Payment failed, reservation not confirmed.");
                }
            }
            case 2 -> {
                reservation.annuler();
                reservation.getTransport().freeSeat(reservation.getPassengerList().size());
                System.out.println("Reservation canceled.");
            }
        }
    }
}
