package main.java.fr.ynov.kanoe.service;

import main.java.fr.ynov.kanoe.enums.MethodPayment;
import main.java.fr.ynov.kanoe.enums.TicketType;
import main.java.fr.ynov.kanoe.model.Passenger;
import main.java.fr.ynov.kanoe.model.Booking;
import main.java.fr.ynov.kanoe.model.Transport;
import main.java.fr.ynov.kanoe.model.User;
import main.java.fr.ynov.kanoe.observer.NotificationManager;
import main.java.fr.ynov.kanoe.observer.Observer;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BookingSystem {

    private List<Transport> transportsDisponibles;
    private List<Booking> bookings;
    private List<User> utilisateurs;

    private NotificationManager notificationManager;

    public BookingSystem() {
        this.transportsDisponibles = new ArrayList<>();
        this.bookings = new ArrayList<>();
        this.utilisateurs = new ArrayList<>();
        this.notificationManager = new NotificationManager();
    }

    public void addObserver(Observer observer) {
        notificationManager.addObserver(observer);
    }

    public void removeObserver(Observer observer) {
        notificationManager.removeObserver(observer);
    }


    public List<Transport> rechercherTransports(String origine, String destination, LocalDateTime dateDepart) {
        System.out.println("\nSearch transport from " + origine + " to " + destination +
                " on " + dateDepart.toLocalDate());

        List<Transport> resultats = transportsDisponibles.stream()
                .filter(t -> t.getStatingPoint().equalsIgnoreCase(origine))
                .filter(t -> t.getEndPoint().equalsIgnoreCase(destination))
                .filter(t -> t.getTimeDepart().toLocalDate().equals(dateDepart.toLocalDate()))
                .filter(t -> t.getAvailableSeats() > 0)
                .collect(Collectors.toList());

        System.out.println(resultats.size() + " transport(s) found:");
        return resultats;
    }

    public Transport searchForTransportWithID(String id) {
        List<Transport> resultats = transportsDisponibles.stream()
                .filter(t -> t.getId().equalsIgnoreCase(id))
                .filter(t -> t.getAvailableSeats() > 0)
                .toList();
        return resultats.isEmpty() ? null : resultats.getFirst();
    }


    public void creerReservation(User utilisateur, Transport transport, List<Passenger> passengerList, TicketType ticketType, MethodPayment methodPayment) {

        if (!utilisateurs.contains(utilisateur)) {
            System.out.println("User not registered in the system");
            return;
        }

        double prixBase = transport.getBasePrice() * passengerList.size();
        double prixTotal = calculerPrixAvecTypeBillet(prixBase, ticketType);

        Booking booking = new Booking(passengerList.size(), prixTotal, passengerList, utilisateur.getId(), methodPayment, transport);
        bookings.add(booking);
        transport.reserverSeat(passengerList.size());

        Notification notifReservation = new Notification(
                "Reservation confirmed",
                "Your reservation " + booking.getNumeroReservation() +
                        " for " + transport.getStatingPoint() + " → " + transport.getEndPoint() +
                        " has been successfully created. Total price: " + prixTotal + "€",
                "CONFIRMATION"
        );
        notificationManager.notifyObserver(utilisateur, notifReservation);

        System.out.println("Reservation created: " + booking.getNumeroReservation());
    }


    private double calculerPrixAvecTypeBillet(double prixBase, TicketType ticketType) {
        return switch (ticketType) {
            case BUSINESS -> prixBase * 1.5;
            case FIRST_CLASS -> prixBase * 2.0;
            default -> prixBase;
        };
    }


    public void ajouterTransport(Transport transport) {
        if (transport == null) {
            System.out.println("The transport cannot be null");
            return;
        }

        transportsDisponibles.add(transport);
        System.out.println("Transport added: " + transport.getStatingPoint() + " → " +
                transport.getEndPoint() + " (" + transport.getClass().getSimpleName() + ")");
    }


    public void enregistrerUtilisateur(User utilisateur) {
        if (utilisateur == null) {
            System.out.println("The user cannot be null");
            return;
        }

        boolean emailExiste = utilisateurs.stream()
                .anyMatch(u -> u.getEmail().equalsIgnoreCase(utilisateur.getEmail()));

        if (emailExiste) {
            System.out.println("A user with this email already exists");
            return;
        }

        utilisateurs.add(utilisateur);

        notificationManager.addObserver(utilisateur);

        Notification notifBienvenue = new Notification(
                "Welcome to Kanoe!",
                "Welcome " + utilisateur.getFirstName() + "! Your account has been successfully created.",
                "INFO"
        );
        notificationManager.notifyObserver(utilisateur, notifBienvenue);

        System.out.println("User registered: " + utilisateur.getFirstName() + " " + utilisateur.getLastName() +
                " (" + utilisateur.getEmail() + ")");
    }


    // Getters
    public List<Transport> getTransportsDisponibles() {
        return new ArrayList<>(transportsDisponibles);
    }

    public List<Booking> getReservations() {
        return new ArrayList<>(bookings);
    }

    public List<User> getUtilisateurs() {
        return new ArrayList<>(utilisateurs);
    }

    public NotificationManager getNotificationManager() {
        return notificationManager;
    }
}