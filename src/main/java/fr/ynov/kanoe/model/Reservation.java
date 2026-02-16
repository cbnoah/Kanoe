package main.java.fr.ynov.kanoe.model;

import main.java.fr.ynov.kanoe.enums.StatusReservation;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

public class Reservation {

    private String numeroReservation;
    private LocalDateTime dateReservation;
    private int nombrePassager;
    private double prixTotale;
    private StatusReservation statut;
    private List<Passenger> passengerList;

    // Constructeur
    public Reservation(int nombrePassager, double prixTotale) {
        this.numeroReservation = genererNumeroReservation();
        this.dateReservation = LocalDateTime.now();
        this.nombrePassager = nombrePassager;
        this.prixTotale = prixTotale;
        this.statut = StatusReservation.PENDING;
    }

    // Génère un numéro de réservation unique
    private String genererNumeroReservation(){

        String prefix = "RES";
        String dateFormat = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String uniqueId = UUID.randomUUID().toString().substring(0, 5).toUpperCase();

        return prefix + "-" + dateFormat + "-" + uniqueId;
    }

    // Getters
    public String getNumeroReservation() {
        return numeroReservation;
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
    }

    public void ajouterPassager(){
        this.nombrePassager++;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "numeroReservation='" + numeroReservation + '\'' +
                ", dateReservation=" + dateReservation +
                ", nombrePassager=" + nombrePassager +
                ", prixTotale=" + prixTotale +
                ", statut=" + statut +
                '}';
    }
}

