package main.java.fr.ynov.kanoe.observer;

import main.java.fr.ynov.kanoe.service.Notification;
import java.util.ArrayList;
import java.util.List;

public class NotificationManager {
    private List<Observer> observers = new ArrayList<>();

    public void addObserver(Observer observer) {
        if (observer == null) {
            throw new IllegalArgumentException("The observer cannot be null");
        }
        if (!observers.contains(observer)) {
            observers.add(observer);
        }
    }

    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    public void notifyObservers(Notification notification) {
        if (notification == null) {
            throw new IllegalArgumentException("The notification cannot be null");
        }
        for (Observer observer : observers) {
            observer.receiveNotification(notification);
        }
    }

    public void notifyObserver(Observer observer, Notification notification) {
        if (observer == null) {
            throw new IllegalArgumentException("The observer cannot be null");
        }
        if (notification == null) {
            throw new IllegalArgumentException("The notification cannot be null");
        }
        observer.receiveNotification(notification);
    }
}