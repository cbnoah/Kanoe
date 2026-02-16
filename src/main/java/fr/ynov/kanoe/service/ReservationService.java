package main.java.fr.ynov.kanoe.service;

import main.java.fr.ynov.kanoe.model.Reservation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Service pour gérer plusieurs réservations
 */

public class ReservationService {

    private List<Reservation> reservations;

    // Constructeur
    public ReservationService() {
        this.reservations = new ArrayList<>();
    }

    /**
     * Crée une nouvelle réservation et l'ajoute à la liste
     */

    public Reservation creerReservation(int nombrePassager, double prixTotale) {
        Reservation nouvelleReservation = new Reservation(nombrePassager, prixTotale);
        reservations.add(nouvelleReservation);
        System.out.println("Réservation créée : " + nouvelleReservation.getNumeroReservation());
        return nouvelleReservation;
    }

    /**
     * Recherche une réservation par son numéro
     */

    public Optional<Reservation> rechercherReservation(String numeroReservation) {
        return reservations.stream()
                .filter(r -> r.getNumeroReservation().equals(numeroReservation))
                .findFirst();
    }

    /**
     * Confirme une réservation
     */

    public boolean confirmerReservation(String numeroReservation) {
        Optional<Reservation> reservation = rechercherReservation(numeroReservation);
        if (reservation.isPresent()) {
            reservation.get().confirmer();
            System.out.println("Réservation " + numeroReservation + " confirmée");
            return true;
        }
        System.out.println("Réservation " + numeroReservation + " introuvable");
        return false;
    }

    /**
     * Annule une réservation
     */

    public boolean annulerReservation(String numeroReservation) {
        Optional<Reservation> reservation = rechercherReservation(numeroReservation);
        if (reservation.isPresent()) {
            reservation.get().annuler();
            System.out.println("Réservation " + numeroReservation + " annulée");
            return true;
        }
        System.out.println("Réservation " + numeroReservation + " introuvable");
        return false;
    }

    /**
     * Supprime une réservation de la liste
     */

    public boolean supprimerReservation(String numeroReservation) {
        return reservations.removeIf(r -> r.getNumeroReservation().equals(numeroReservation));
    }

    /**
     * Retourne toutes les réservations
     */

    public List<Reservation> getToutesLesReservations() {
        return new ArrayList<>(reservations);
    }

    /**
     * Retourne le nombre total de réservations
     */

    public int getNombreReservations() {
        return reservations.size();
    }

    /**
     * Affiche toutes les réservations
     */

    public void afficherReservations() {
        if (reservations.isEmpty()) {
            System.out.println("Aucune réservation");
        } else {
            System.out.println("\n=== Liste des réservations ===");
            for (Reservation r : reservations) {
                System.out.println(r);
            }
        }
    }
}



