package main.java.fr.ynov.kanoe.model;

import java.time.LocalDate;

import javax.management.Notification;

public class Passenger {
    protected String nom;
    protected String prenom;
    protected LocalDate dateNaissance;
    protected String numeroPasseport;
    protected String numeroSiege;

    public Passenger(String nom, String prenom, LocalDate dateNaissance, String numeroPasseport, String numeroSiege) {
        if (nom == null || nom.trim().isEmpty()) {
        throw new IllegalArgumentException("Le nom ne peut pas être vide");
    }
    if (prenom == null || prenom.trim().isEmpty()) {
        throw new IllegalArgumentException("Le prénom ne peut pas être vide");
    }
    if (dateNaissance == null) {
        throw new IllegalArgumentException("La date de naissance ne peut pas être nulle");
    }
    if (dateNaissance.isAfter(LocalDate.now())) {
        throw new IllegalArgumentException("La date de naissance ne peut pas être dans le futur");
    }
    if (numeroPasseport == null || numeroPasseport.trim().isEmpty()) {
        throw new IllegalArgumentException("Le numéro de passeport ne peut pas être vide");
    }
    
        this.nom = nom;
        this.prenom = prenom;
        this.dateNaissance = dateNaissance;
        this.numeroPasseport = numeroPasseport;
        this.numeroSiege = numeroSiege;
    }

    public String getNomComplet() {
        return prenom + " " + nom;
    }

    public LocalDate getDateNaissance() {
        return dateNaissance;
    }

    public String getNumeroPasseport() {
        return numeroPasseport;
    }

    public String getNumeroSiege() {
        return numeroSiege;
    }


    public void notification(Notification notification) {
    if (notification == null) {
        throw new IllegalArgumentException("La notification ne peut pas être nulle");
    }
    System.out.println("Notification pour " + prenom + " " + nom + ": " + notification.getMessage());
}   

        @Override
    public String toString() {
        return "Passenger{" +
                "nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", dateNaissance=" + dateNaissance +
                ", numeroPasseport='" + numeroPasseport + '\'' +
                ", numeroSiege='" + numeroSiege + '\'' +
                '}';
    }
}   