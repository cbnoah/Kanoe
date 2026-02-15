package main.java.fr.ynov.kanoe.model;

import java.time.LocalDateTime;

public class Avion extends Transport {
    private final String planeNumber;
    private final boolean inFlightEntertainment;

    public Avion(Builder builder) {
        super(builder.id, builder.opertator, builder.statingPoint, builder.endPoint,
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
        private String opertator;
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

        public Builder setPlaneNumber(String planeNumber) {
            this.planeNumber = planeNumber;
            return this;
        }

        public Builder setInFlightEntertainment(String terminal) {
            this.inFlightEntertainment = inFlightEntertainment;
            return this;
        }

        public Avion build() {
            if (id == null || opertator == null) {
                throw new IllegalStateException("ID et compagnie sont obligatoires");
            }
            return new Avion(this);
        }
    }
}
