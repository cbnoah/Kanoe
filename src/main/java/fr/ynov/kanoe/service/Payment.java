package main.java.fr.ynov.kanoe.service;

import main.java.fr.ynov.kanoe.enums.paymentMethod;
import main.java.fr.ynov.kanoe.enums.StatusPayment;

import java.time.LocalDateTime;

public class Payment {
    protected final int idTransaction;
    protected final double amount;
    protected final LocalDateTime datePayment;
    protected final paymentMethod method;

    protected StatusPayment status;

    public Payment(int idTransaction, double amount, LocalDateTime datePayment, paymentMethod method) {
        this.idTransaction = idTransaction;
        this.amount = amount;
        this.datePayment = datePayment;
        this.method = method;
        this.status = StatusPayment.PENDING;
    }

    public int getIdTransaction() {
        return idTransaction;
    }

    public double getAmount() {
        return amount;
    }

    public LocalDateTime getDatePayment() {
        return datePayment;
    }

    public paymentMethod getMethod() {
        return method;
    }

    public StatusPayment getStatus() {
        return status;
    }

    public void cancelPayment() {
        if (this.status == StatusPayment.SUCCESS || this.status == StatusPayment.PENDING) {
            this.status = StatusPayment.CANCELED;
        }
    }

    public void processPayment() {
        if (this.status == StatusPayment.PENDING) {
            if (Math.random() > 0.2) {
                this.status = StatusPayment.SUCCESS;
            } else {
                this.status = StatusPayment.CANCELED;
            }
        }
    }
}
