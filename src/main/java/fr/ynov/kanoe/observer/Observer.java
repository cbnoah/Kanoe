package main.java.fr.ynov.kanoe.observer;
import main.java.fr.ynov.kanoe.service.Notification;

public interface Observer {
        void receiveNotification(Notification notification);
}
