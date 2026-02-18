package main.java.fr.ynov.kanoe.utils;

import main.java.fr.ynov.kanoe.model.Reservation;
import main.java.fr.ynov.kanoe.model.Users;
import main.java.fr.ynov.kanoe.service.Notification;
import main.java.fr.ynov.kanoe.service.SystemeReservation;

import java.util.List;

public class NotificationCreator {
    private final SystemeReservation system;

    public NotificationCreator(SystemeReservation system) {
        this.system = system;
    }

    public void createNotification(String title, String message, String scope) {
        for (Users user: getUserById(getUserIdForScopedUsers(scope)))
        {
            system.getNotificationManager().notifyObserver(user, new Notification(title, message, scope));
        }
    }

    public int getUserIdForScopedUsers(String scope) {
        for (Reservation reservation: system.getReservations()) {
            if (reservation.getTransport().getId().equals(scope)) {
                return reservation.getUserId();
            }
        }
        return 0;
    }

    public List<Users> getUserById(int userId) {
        return system.getUtilisateurs().stream()
                .filter(u -> u.getId() == userId)
                .toList();
    }
}
