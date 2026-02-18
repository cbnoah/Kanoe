package main.java.fr.ynov.kanoe.service;

import main.java.fr.ynov.kanoe.enums.paymentMethod;
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

    private final List<Transport> availableTransports;
    private final List<Booking> bookings;
    private final List<User> users;

    private final NotificationManager notificationManager;

    public BookingSystem() {
        this.availableTransports = new ArrayList<>();
        this.bookings = new ArrayList<>();
        this.users = new ArrayList<>();
        this.notificationManager = new NotificationManager();
    }

    public void addObserver(Observer observer) {
        notificationManager.addObserver(observer);
    }

    public void removeObserver(Observer observer) {
        notificationManager.removeObserver(observer);
    }


    public List<Transport> rechercherTransports(String startingPoint, String destination, LocalDateTime departureDate) {
        System.out.println("\nSearch transport from " + startingPoint + " to " + destination +
                " on " + departureDate.toLocalDate());

        List<Transport> results = availableTransports.stream()
                .filter(t -> t.getStatingPoint().equalsIgnoreCase(startingPoint))
                .filter(t -> t.getEndPoint().equalsIgnoreCase(destination))
                .filter(t -> t.getTimeDepart().toLocalDate().equals(departureDate.toLocalDate()))
                .filter(t -> t.getAvailableSeats() > 0)
                .collect(Collectors.toList());

        System.out.println(results.size() + " transport(s) found:");
        return results;
    }

    public Transport searchForTransportWithID(String id) {
        List<Transport> results = availableTransports.stream()
                .filter(t -> t.getId().equalsIgnoreCase(id))
                .filter(t -> t.getAvailableSeats() > 0)
                .toList();
        return results.isEmpty() ? null : results.getFirst();
    }


    public void createBooking(User user, Transport transport, List<Passenger> passengerList, TicketType ticketType, paymentMethod paymentMethod) {

        if (!users.contains(user)) {
            System.out.println("User not registered in the system");
            return;
        }

        double basePrice = transport.getBasePrice() * passengerList.size();
        double totalPrice = calculateTicketPrice(basePrice, ticketType);

        Booking booking = new Booking(passengerList.size(), totalPrice, passengerList, user.getId(), paymentMethod, transport);
        bookings.add(booking);
        transport.reserverSeat(passengerList.size());

        Notification notifReservation = new Notification(
                "Reservation confirmed",
                "Your reservation " + booking.getBookingNumber() +
                        " for " + transport.getStatingPoint() + " → " + transport.getEndPoint() +
                        " has been successfully created. Total price: " + totalPrice + "€",
                "CONFIRMATION"
        );
        notificationManager.notifyObserver(user, notifReservation);

        System.out.println("Reservation created: " + booking.getBookingNumber());
    }


    private double calculateTicketPrice(double prixBase, TicketType ticketType) {
        return switch (ticketType) {
            case BUSINESS -> prixBase * 1.5;
            case FIRST_CLASS -> prixBase * 2.0;
            default -> prixBase;
        };
    }


    public void addTransport(Transport transport) {
        if (transport == null) {
            System.out.println("The transport cannot be null");
            return;
        }

        availableTransports.add(transport);
        System.out.println("Transport added: " + transport.getStatingPoint() + " → " +
                transport.getEndPoint() + " (" + transport.getClass().getSimpleName() + ")");
    }


    public void saveUser(User user) {
        if (user == null) {
            System.out.println("The user cannot be null");
            return;
        }

        boolean emailExist = users.stream()
                .anyMatch(u -> u.getEmail().equalsIgnoreCase(user.getEmail()));

        if (emailExist) {
            System.out.println("A user with this email already exists");
            return;
        }

        users.add(user);

        notificationManager.addObserver(user);

        Notification notifBienvenue = new Notification(
                "Welcome to Kanoe!",
                "Welcome " + user.getFirstName() + "! Your account has been successfully created.",
                "INFO"
        );
        notificationManager.notifyObserver(user, notifBienvenue);

        System.out.println("User registered: " + user.getFirstName() + " " + user.getLastName() +
                " (" + user.getEmail() + ")");
    }


    // Getters
    public List<Transport> getAvailableTransports() {
        return new ArrayList<>(availableTransports);
    }

    public List<Booking> getBookings() {
        return new ArrayList<>(bookings);
    }

    public List<User> getUsers() {
        return new ArrayList<>(users);
    }

    public NotificationManager getNotificationManager() {
        return notificationManager;
    }
}