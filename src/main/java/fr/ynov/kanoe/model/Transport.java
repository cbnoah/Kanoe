package main.java.fr.ynov.kanoe.model;

import java.time.LocalDateTime;

public abstract class Transport {
    private final String id;

    public String getOpertator() {
        return opertator;
    }

    public String getStatingPoint() {
        return statingPoint;
    }

    public String getEndPoint() {
        return endPoint;
    }

    public LocalDateTime getTimeDepart() {
        return timeDepart;
    }

    public LocalDateTime getTimeArriving() {
        return timeArriving;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    private String opertator;
    private String statingPoint;
    private String endPoint;
    private LocalDateTime timeDepart;
    private LocalDateTime timeArriving;
    private int capacity;
    private int availableSeats;
    private double basePrice;


    public Transport(String id, String opertator, String statingPoint, String endPoint,
                     LocalDateTime timeDepart, LocalDateTime timeArriving,
                     int capacity, double basePrice) {
        this.id = id;
        this.opertator = opertator;
        this.statingPoint = statingPoint;
        this.endPoint = endPoint;
        this.timeDepart = timeDepart;
        this.timeArriving = timeArriving;
        this.capacity = capacity;
        this.availableSeats = capacity;
        this.basePrice = basePrice;
    }

    public double getBasePrice() {
        return basePrice;
    }

    public String getId() {
        return id;
    }

    public String getTypeTransport() {
        return "Transport";
    }

    public boolean verifyDisponibilite(Integer ammount) {
        if (ammount == null) {
            return availableSeats >= 0;
        }
        return availableSeats - ammount >= 0;
    }

    public boolean reserverSeat(int ammount) {
        if (verifyDisponibilite(ammount)) {
            availableSeats -= ammount;
            return true;
        }
        return false;
    }
}
