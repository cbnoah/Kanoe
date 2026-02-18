package main.java.fr.ynov.kanoe.service;

import main.java.fr.ynov.kanoe.enums.MethodPayment;
import main.java.fr.ynov.kanoe.enums.TypeBillet;
import main.java.fr.ynov.kanoe.model.Passenger;
import main.java.fr.ynov.kanoe.model.Reservation;
import main.java.fr.ynov.kanoe.model.Transport;
import main.java.fr.ynov.kanoe.model.Users;
import main.java.fr.ynov.kanoe.observer.NotificationManager;
import main.java.fr.ynov.kanoe.observer.Observer;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SystemeReservation {

    private List<Transport> transportsDisponibles;
    private List<Reservation> reservations;
    private List<Users> utilisateurs;

    // ✅ Ajout du NotificationManager
    private NotificationManager notificationManager;

    public SystemeReservation() {
        this.transportsDisponibles = new ArrayList<>();
        this.reservations = new ArrayList<>();
        this.utilisateurs = new ArrayList<>();
        this.notificationManager = new NotificationManager(); // ✅ Initialisation
    }

    // ✅ Méthodes pour gérer les abonnements depuis l'extérieur
    public void addObserver(Observer observer) {
        notificationManager.addObserver(observer);
    }

    public void removeObserver(Observer observer) {
        notificationManager.removeObserver(observer);
    }


    public List<Transport> rechercherTransports(String origine, String destination, LocalDateTime dateDepart) {
        System.out.println("\n🔍 Search transport from " + origine + " to " + destination +
                " on " + dateDepart.toLocalDate());

        List<Transport> resultats = transportsDisponibles.stream()
                .filter(t -> t.getStatingPoint().equalsIgnoreCase(origine))
                .filter(t -> t.getEndPoint().equalsIgnoreCase(destination))
                .filter(t -> t.getTimeDepart().toLocalDate().equals(dateDepart.toLocalDate()))
                .filter(t -> t.getAvailableSeats() > 0)
                .collect(Collectors.toList());

        System.out.println("✅ " + resultats.size() + " transport(s) found:");
        return resultats;
    }

    public Transport searchForTransportWithID(String id) {
        List<Transport> resultats = transportsDisponibles.stream()
                .filter(t -> t.getId().equalsIgnoreCase(id))
                .filter(t -> t.getAvailableSeats() > 0)
                .toList();

        if (resultats.isEmpty()) {
            System.out.println("No transport found with ID: " + id);
        }
        return resultats.getFirst();
    }


    public void creerReservation(Users utilisateur, Transport transport, List<Passenger> passengerList, TypeBillet typeBillet, MethodPayment methodPayment) {

        if (!utilisateurs.contains(utilisateur)) {
            System.out.println("❌ User not registered in the system");
            return;
        }

        double prixBase = transport.getBasePrice() * passengerList.size();
        double prixTotal = calculerPrixAvecTypeBillet(prixBase, typeBillet);

        Reservation reservation = new Reservation(passengerList.size(), prixTotal, passengerList, utilisateur.getId(), methodPayment);
        reservations.add(reservation);

        // ✅ Notification de confirmation de réservation
        Notification notifReservation = new Notification(
                "RReservation confirmed",
                "Your reservation " + reservation.getNumeroReservation() +
                        " for " + transport.getStatingPoint() + " → " + transport.getEndPoint() +
                        " has been successfully created. Total price: " + prixTotal + "€",
                "CONFIRMATION"
        );
        notificationManager.notifyObserver(utilisateur, notifReservation); // ✅ Notifie uniquement cet utilisateur

        System.out.println("✅ RReservation created: " + reservation.getNumeroReservation());
    }


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


    public void ajouterTransport(Transport transport) {
        if (transport == null) {
            System.out.println("❌ The transport cannot be null");
            return;
        }

        transportsDisponibles.add(transport);
        System.out.println("✅ Transport added: " + transport.getStatingPoint() + " → " +
                transport.getEndPoint() + " (" + transport.getClass().getSimpleName() + ")");
    }


    public void enregistrerUtilisateur(Users utilisateur) {
        if (utilisateur == null) {
            System.out.println("❌ The user cannot be null");
            return;
        }

        boolean emailExiste = utilisateurs.stream()
                .anyMatch(u -> u.getEmail().equalsIgnoreCase(utilisateur.getEmail()));

        if (emailExiste) {
            System.out.println("❌ A user with this email already exists");
            return;
        }

        utilisateurs.add(utilisateur);

        // ✅ Abonnement automatique de l'utilisateur aux notifications
        notificationManager.addObserver(utilisateur);

        // ✅ Welcome notification
        Notification notifBienvenue = new Notification(
                "Welcome to Kanoe!",
                "Welcome " + utilisateur.getFirstName() + "! Your account has been successfully created.",
                "INFO"
        );
        notificationManager.notifyObserver(utilisateur, notifBienvenue);

        System.out.println("✅ User registered: " + utilisateur.getFirstName() + " " + utilisateur.getLastName() +
                " (" + utilisateur.getEmail() + ")");
    }


    // Getters
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
        System.out.println("       SYSTEM RESERVATION SUMMARY");
        System.out.println("═══════════════════════════════════════════");
        System.out.println("📍 Available transports : " + transportsDisponibles.size());
        System.out.println("📋 Active reservations   : " + reservations.size());
        System.out.println("👥 Registered users  : " + utilisateurs.size());
        System.out.println("═══════════════════════════════════════════\n");
    }
}