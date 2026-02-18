package main.java.fr.ynov.kanoe.model;

import main.java.fr.ynov.kanoe.enums.paymentMethod;
import main.java.fr.ynov.kanoe.enums.StatusPayment;
import main.java.fr.ynov.kanoe.enums.StatusReservation;
import main.java.fr.ynov.kanoe.service.Payment;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

public class Booking {
    private final int userId;
    private final String bookingNumber;
    private final LocalDateTime dateReservation;
    private int passengersNumber;
    private double totalPrice;
    private StatusReservation statut;
    private final Payment payment;
    private final Transport transport;
    private final List<Passenger> passengerList;

    // Constructor
    public Booking(int passengersNumber, double totalPrice, List<Passenger> passengerList, int userId, paymentMethod paymentMethod, Transport transport) {
        this.userId = userId;
        this.bookingNumber = generateBookingNumber();
        this.dateReservation = LocalDateTime.now();
        this.passengersNumber = passengersNumber;
        this.totalPrice = totalPrice;
        this.passengerList = passengerList;
        this.payment = new Payment(0, totalPrice, LocalDateTime.now(), paymentMethod);
        this.statut = StatusReservation.PENDING;
        this.transport = transport;
    }

    private String generateBookingNumber(){

        String prefix = "RES";
        String dateFormat = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String uniqueId = UUID.randomUUID().toString().substring(0, 5).toUpperCase();

        return prefix + "-" + dateFormat + "-" + uniqueId;
    }


    // Getters
    public List<Passenger> getPassengerList() {
        return passengerList;
    }

    public String getBookingNumber() {
        return bookingNumber;
    }

    public int getUserId() {
        return userId;
    }

    public LocalDateTime getDateReservation() {
        return dateReservation;
    }

    public int getPassengersNumber() {
        return passengersNumber;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public StatusReservation getStatut() {
        return statut;
    }

    public Transport getTransport() {
        return transport;
    }

    // Setters
    public void setPassengersNumber(int passengersNumber) {
        this.passengersNumber = passengersNumber;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    // Methods
    public void confirmer(){
        this.statut = StatusReservation.SUCCESS;
    }

    public void annuler(){
        this.statut = StatusReservation.CANCELED;
        this.payment.cancelPayment();
    }

    public void addPassenger(){
        this.passengersNumber++;
    }

    public boolean processPayment() {
        this.payment.processPayment();
        if (this.payment.getStatus() == StatusPayment.SUCCESS) {
            confirmer();
            return true;
        } else {
            annuler();
            return false;
        }
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "userId=" + userId +
                "numeroReservation='" + bookingNumber + '\'' +
                ", dateReservation=" + dateReservation +
                ", nombrePassager=" + passengersNumber +
                ", prixTotale=" + totalPrice +
                ", statut=" + statut +
                ", paymentStatus=" + payment.getStatus() +
                ", passengerList=" + passengerList +
                '}';
    }
}

