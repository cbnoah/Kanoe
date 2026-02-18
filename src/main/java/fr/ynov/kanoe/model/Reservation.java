package main.java.fr.ynov.kanoe.model;

import main.java.fr.ynov.kanoe.enums.MethodPayment;
import main.java.fr.ynov.kanoe.enums.StatusPayment;
import main.java.fr.ynov.kanoe.enums.StatusReservation;
import main.java.fr.ynov.kanoe.service.Payment;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

public class Reservation {
    private int userId;
    private String numeroReservation;
    private LocalDateTime dateReservation;
    private int nombrePassager;
    private double prixTotale;
    private StatusReservation statut;
    private Payment payment;

    private List<Passenger> passengerList;

    // Constructeur
    public Reservation(int nombrePassager, double prixTotale, List<Passenger> passengerList, int userId, MethodPayment methodPayment) {
        this.userId = userId;
        this.numeroReservation = genererNumeroReservation();
        this.dateReservation = LocalDateTime.now();
        this.nombrePassager = nombrePassager;
        this.prixTotale = prixTotale;
        this.passengerList = passengerList;
        this.payment = new Payment(0, prixTotale, LocalDateTime.now(), methodPayment);
        this.statut = StatusReservation.PENDING;
    }

    private String genererNumeroReservation(){

        String prefix = "RES";
        String dateFormat = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String uniqueId = UUID.randomUUID().toString().substring(0, 5).toUpperCase();

        return prefix + "-" + dateFormat + "-" + uniqueId;
    }


    // Getters
    public List<Passenger> getPassengerList() {
        return passengerList;
    }

    public String getNumeroReservation() {
        return numeroReservation;
    }

    public int getUserId() {
        return userId;
    }

    public LocalDateTime getDateReservation() {
        return dateReservation;
    }

    public int getNombrePassager() {
        return nombrePassager;
    }

    public double getPrixTotale() {
        return prixTotale;
    }

    public StatusReservation getStatut() {
        return statut;
    }

    // Setters
    public void setNombrePassager(int nombrePassager) {
        this.nombrePassager = nombrePassager;
    }

    public void setPrixTotale(double prixTotale) {
        this.prixTotale = prixTotale;
    }

    // Méthodes métier
    public void confirmer(){
        this.statut = StatusReservation.SUCCESS;
    }

    public void annuler(){
        this.statut = StatusReservation.CANCELED;
        this.payment.cancelPayment();
    }

    public void ajouterPassager(){
        this.nombrePassager++;
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
                "numeroReservation='" + numeroReservation + '\'' +
                ", dateReservation=" + dateReservation +
                ", nombrePassager=" + nombrePassager +
                ", prixTotale=" + prixTotale +
                ", statut=" + statut +
                ", paymentStatus=" + payment.getStatus() +
                ", passengerList=" + passengerList +
                '}';
    }
}

