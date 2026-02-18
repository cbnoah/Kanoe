package main.java.fr.ynov.kanoe.model;

public class Passenger {
    protected String lastName;
    protected String firstName;
    protected int age;
    protected String passportNumber;

    public Passenger(String lastName, String firstName, int age, String passportNumber) {
        if (lastName == null || lastName.trim().isEmpty()) {
        throw new IllegalArgumentException("The last name cannot be empty");
    }
    if (firstName == null || firstName.trim().isEmpty()) {
        throw new IllegalArgumentException("The first name cannot be empty");
    }
    if (age == 0) {
        throw new IllegalArgumentException("The birth date cannot be null");
    }
    if (passportNumber == null || passportNumber.trim().isEmpty()) {
        throw new IllegalArgumentException("The passport number cannot be empty");
    }
    
        this.lastName = lastName;
        this.firstName = firstName;
        this.age = age;
        this.passportNumber = passportNumber;
    }

    public String getFullName() {
        return firstName + " " + lastName ;
    }

    public int getBornDate() {
        return age;
    }

    public String getPasseportNumber() {
        return passportNumber;
    }

    @Override
    public String toString() {
        return "Passenger{" +
                "lastName='" + lastName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", age=" + age +
                ", passportNumber='" + passportNumber + '\'' +
                '}';
    }
}   