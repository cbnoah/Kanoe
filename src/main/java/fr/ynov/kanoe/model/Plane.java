package main.java.fr.ynov.kanoe.model;

import java.time.LocalDateTime;

public class Plane extends Transport {
    private final String planeNumber;
    private final boolean inFlightEntertainment;

    public Plane(Builder builder) {
        super(builder.id, builder.operator, builder.statingPoint, builder.endPoint,
                builder.timeDepart, builder.timeArriving, builder.capacity, builder.basePrice);
        this.planeNumber = builder.planeNumber;
        this.inFlightEntertainment = builder.inFlightEntertainment;
    }

    public String getPlaneNumber() {
        return planeNumber;
    }

    public boolean isInFlightEntertainment() {
        return inFlightEntertainment;
    }

    @Override
    public String getTypeTransport() {
        return "Avion";
    }

    public static class Builder {
        private String id;
        private String operator;
        private String statingPoint;
        private String endPoint;
        private LocalDateTime timeDepart;
        private LocalDateTime timeArriving;
        private int capacity;
        private double basePrice;
        private String planeNumber;
        private boolean inFlightEntertainment = false;

        public Builder setId(String id) {
            this.id = id;
            return this;
        }

        public Builder setOperator(String operator) {
            this.operator = operator;
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

        public Builder setPlaneNumber(String planeNumber) {
            this.planeNumber = planeNumber;
            return this;
        }

        public Builder setInFlightEntertainment(boolean inFlightEntertainment) {
            this.inFlightEntertainment = inFlightEntertainment;
            return this;
        }

        public Plane build() {
            if (id == null || operator == null) {
                throw new IllegalStateException("ID et compagnie sont obligatoires");
            }
            return new Plane(this);
        }
    }
}
