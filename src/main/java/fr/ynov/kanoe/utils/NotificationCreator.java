package main.java.fr.ynov.kanoe.utils;

import main.java.fr.ynov.kanoe.model.Booking;
import main.java.fr.ynov.kanoe.model.User;
import main.java.fr.ynov.kanoe.service.Notification;
import main.java.fr.ynov.kanoe.service.BookingSystem;

import java.util.List;

public class NotificationCreator {
    private final BookingSystem system;

    public NotificationCreator(BookingSystem system) {
        this.system = system;
    }

    public void createNotification(String title, String message, String scope) {
        for (User user: getUserById(getUserIdForScopedUsers(scope)))
        {
            system.getNotificationManager().notifyObserver(user, new Notification(title, message, scope));
        }
    }

    public int getUserIdForScopedUsers(String scope) {
        for (Booking booking : system.getReservations()) {
            if (booking.getTransport().getId().equals(scope)) {
                return booking.getUserId();
            }
        }
        return 0;
    }

    public List<User> getUserById(int userId) {
        return system.getUtilisateurs().stream()
                .filter(u -> u.getId() == userId)
                .toList();
    }
}
