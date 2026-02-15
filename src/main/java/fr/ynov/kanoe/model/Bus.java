package main.java.fr.ynov.kanoe.model;

import java.lang.module.ModuleDescriptor;
import java.time.LocalDateTime;

public class Bus extends Transport {
    private final String busNumber;

    private final boolean airConditioning;

    private Bus(Builder builder) {
        super(builder.id, builder.opertator, builder.statingPoint, builder.endPoint,
                builder.timeDepart, builder.timeArriving, builder.capacity, builder.basePrice);
        this.busNumber = builder.busNumber;
        this.airConditioning = builder.airConditioning;
    }

    public String getBusNumber() {
        return busNumber;
    }

    public boolean isAirConditioning() {
        return airConditioning;
    }

    @Override
    public String getTypeTransport() {
        return "Bus";
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
        private String busNumber;
        private boolean airConditioning = false;

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

        public Builder setBusNumber(String busNumber) {
            this.busNumber = busNumber;
            return this;
        }

        public Builder setAirConditioning(boolean airConditioning) {
            this.airConditioning = airConditioning;
            return this;
        }

        public Bus build() {
            // Validation si nécessaire
            if (id == null || opertator == null) {
                throw new IllegalStateException("ID and Operator must be provided");
            }
            return new Bus(this);
        }
    }
}
