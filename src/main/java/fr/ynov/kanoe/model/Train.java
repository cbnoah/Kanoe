package main.java.fr.ynov.kanoe.model;

import java.time.LocalDateTime;

public class Train extends Transport {
    private final String trainNumber;
    private final boolean hasWifi;
    private final boolean hasDiningCar;

    public Train(Builder builder) {
        super(builder.id, builder.opertator, builder.statingPoint, builder.endPoint,
                builder.timeDepart, builder.timeArriving, builder.capacity, builder.basePrice);
        this.trainNumber = builder.trainNumber;
        this.hasWifi = builder.hasWifi;
        this.hasDiningCar = builder.hasDiningCar;
    }

    public String getTrainNumber() {
        return trainNumber;
    }

    public boolean isHasWifi() {
        return hasWifi;
    }

    public boolean isHasDiningCar() {
        return hasDiningCar;
    }

    @Override
    public String getTypeTransport() {
        return "Train";
    }

    public static class Builder {
        private String id;
        private String opertator;
        private String statingPoint;
        private String endPoint;
        private LocalDateTime timeDepart;
        private LocalDateTime timeArriving;
        private int capacity;
        private double basePrice;
        private String trainNumber;
        private boolean hasWifi = false;
        private boolean hasDiningCar = false;

        public Builder setId(String id) {
            this.id = id;
            return this;
        }

        public Builder setOpertator(String opertator) {
            this.opertator = opertator;
            return this;
        }

        public Builder setStatingPoint(String statingPoint) {
            this.statingPoint = statingPoint;
            return this;
        }

        public Builder setEndPoint(String endPoint) {
            this.endPoint = endPoint;
            return this;
        }

        public Builder setTimeDepart(LocalDateTime timeDepart) {
            this.timeDepart = timeDepart;
            return this;
        }

        public Builder setTimeArriving(LocalDateTime timeArriving) {
            this.timeArriving = timeArriving;
            return this;
        }

        public Builder setCapacity(int capacity) {
            this.capacity = capacity;
            return this;
        }

        public Builder setBasePrice(double basePrice) {
            this.basePrice = basePrice;
            return this;
        }

        public Builder setTrainNumber(String trainNumber) {
            this.trainNumber = trainNumber;
            return this;
        }

        public Builder setHasWifi(boolean hasWifi) {
            this.hasWifi = hasWifi;
            return this;
        }

        public Builder setHasDiningCar(boolean hasDiningCar) {
            this.hasDiningCar = hasDiningCar;
            return this;
        }

        public Train build() {
            // Validation si nécessaire
            if (id == null || opertator == null) {
                throw new IllegalStateException("ID and Operator must be provided");
            }
            return new Train(this);
        }
    }
}
