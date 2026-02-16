package main.java.fr.ynov.kanoe.service;

import main.java.fr.ynov.kanoe.enums.TypeBillet;
import main.java.fr.ynov.kanoe.service.Notification;
import main.java.fr.ynov.kanoe.model.Reservation;
import main.java.fr.ynov.kanoe.model.Transport;
import main.java.fr.ynov.kanoe.model.Users;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class SystemeReservation {

    private List<Transport> transportsDisponibles;
    private List<Reservation> reservations;
    private List<Users> utilisateurs;

    public SystemeReservation() {
        this.transportsDisponibles = new ArrayList<>();
        this.reservations = new ArrayList<>();
        this.utilisateurs = new ArrayList<>();
    }


    public List<Transport> rechercherTransports(String origine, String destination, LocalDateTime dateDepart) {
        System.out.println("\n🔍 Recherche de transports de " + origine + " à " + destination +
                          " le " + dateDepart.toLocalDate());

        List<Transport> resultats = transportsDisponibles.stream()
                .filter(t -> t.getStatingPoint().equalsIgnoreCase(origine))
                .filter(t -> t.getEndPoint().equalsIgnoreCase(destination))
                .filter(t -> t.getTimeDepart().toLocalDate().equals(dateDepart.toLocalDate()))
                .filter(t -> t.getAvailableSeats() > 0)
                .collect(Collectors.toList());

        System.out.println("✅ " + resultats.size() + " transport(s) trouvé(s)");
        return resultats;
    }


    public Reservation creerReservation(Users utilisateur, Transport transport, int nombrePassagers, TypeBillet typeBillet) {



        if (!utilisateurs.contains(utilisateur)) {
            System.out.println("❌ Utilisateur non enregistré dans le système");
            return null;
        }

        // Calcul du prix selon le type de billet
        double prixBase = transport.getBasePrice() * nombrePassagers;
        double prixTotal = calculerPrixAvecTypeBillet(prixBase, typeBillet);

        // Créer la réservation
        Reservation reservation = new Reservation(nombrePassagers, prixTotal);
        reservations.add(reservation);


        // Envoi d'une notification
        String message = "Votre réservation " + reservation.getNumeroReservation() +
                        " pour " + transport.getStatingPoint() + " → " + transport.getEndPoint() +
                        " a été créée avec succès. Prix total: " + prixTotal + "€";

        System.out.println("✅ Réservation créée : " + reservation.getNumeroReservation());
        return reservation;
    }

    /**
     * Calcule le prix en fonction du type de billet
     */

    private double calculerPrixAvecTypeBillet(double prixBase, TypeBillet typeBillet) {
        switch (typeBillet) {
            case ECONOMIQUE:
                return prixBase;
            case AFFAIRE:
                return prixBase * 1.5;
            case PREMIERE_CLASSE:
                return prixBase * 2.0;
            default:
                return prixBase;
        }
    }

    /**
     * Ajoute un transport au système
     * @param transport Le transport à ajouter
     */
    public void ajouterTransport(Transport transport) {
        if (transport == null) {
            System.out.println("❌ Le transport ne peut pas être null");
            return;
        }

        transportsDisponibles.add(transport);
        System.out.println("✅ Transport ajouté : " + transport.getStatingPoint() + " → " +
                          transport.getEndPoint() + " (" + transport.getClass().getSimpleName() + ")");
    }


    public void enregistrerUtilisateur(Users utilisateur) {
        if (utilisateur == null) {
            System.out.println("❌ L'utilisateur ne peut pas être null");
            return;
        }

        // Vérifier si l'email existe déjà
        boolean emailExiste = utilisateurs.stream()
                .anyMatch(u -> u.getEmail().equalsIgnoreCase(utilisateur.getEmail()));

        if (emailExiste) {
            System.out.println("❌ Un utilisateur avec cet email existe déjà");
            return;
        }

        utilisateurs.add(utilisateur);
        System.out.println("✅ Utilisateur enregistré : " + utilisateur.getPrenom() + " " + utilisateur.getNom() +
                          " (" + utilisateur.getEmail() + ")");

        // Notification de bienvenue
        String message = "Bienvenue " + utilisateur.getPrenom() + "! Votre compte a été créé avec succès.";
    }




    // Getters pour accéder aux listes
    public List<Transport> getTransportsDisponibles() {
        return new ArrayList<>(transportsDisponibles);
    }

    public List<Reservation> getReservations() {
        return new ArrayList<>(reservations);
    }

    public List<Users> getUtilisateurs() {
        return new ArrayList<>(utilisateurs);
    }



    public void afficherResume() {
        System.out.println("\n═══════════════════════════════════════════");
        System.out.println("       RÉSUMÉ DU SYSTÈME DE RÉSERVATION");
        System.out.println("═══════════════════════════════════════════");
        System.out.println("📍 Transports disponibles : " + transportsDisponibles.size());
        System.out.println("📋 Réservations actives   : " + reservations.size());
        System.out.println("👥 Utilisateurs inscrits  : " + utilisateurs.size());
        System.out.println("═══════════════════════════════════════════\n");
    }
}

