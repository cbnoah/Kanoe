package main.java.fr.ynov.kanoe.model;
import java.time.LocalDate;
import javax.management.Notification;

public class Passenger {
    protected String lastName;
    protected String firstName;
    protected LocalDate bornDate;
    protected String passportNumber;
    protected String seatNumber;

    public Passenger(String lastName, String firstName, LocalDate bornDate, String passportNumber, String seatNumber) {
        if (lastName == null || lastName.trim().isEmpty()) {
        throw new IllegalArgumentException("The last name cannot be empty");
    }
    if (firstName == null || firstName.trim().isEmpty()) {
        throw new IllegalArgumentException("The first name cannot be empty");
    }
    if (bornDate == null) {
        throw new IllegalArgumentException("The birth date cannot be null");
    }
    if (bornDate.isAfter(LocalDate.now())) {
        throw new IllegalArgumentException("The birth date cannot be in the future");
    }
    if (passportNumber == null || passportNumber.trim().isEmpty()) {
        throw new IllegalArgumentException("The passport number cannot be empty");
    }
    
        this.lastName = lastName;
        this.firstName = firstName;
        this.bornDate = bornDate;
        this.passportNumber = passportNumber;
        this.seatNumber = seatNumber;
    }

    public String getFullName() {
        return firstName + " " + lastName ;
    }

    public LocalDate getBornDate() {
        return bornDate;
    }

    public String getPasseportNumber() {
        return passportNumber;
    }

    public String getSeatNumber() {
        return seatNumber;
    }


    public void notification(Notification notification) {
    if (notification == null) {
        throw new IllegalArgumentException("The notification cannot be null");
    }
    System.out.println("Notification for " + firstName + " " + lastName + ": " + notification);
}   

        @Override
    public String toString() {
        return "Passenger{" +
                "lastName='" + lastName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", bornDate=" + bornDate +
                ", passportNumber='" + passportNumber + '\'' +
                ", seatNumber='" + seatNumber + '\'' +
                '}';
    }
}   