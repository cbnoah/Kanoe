package main.java.fr.ynov.kanoe.model;
import main.java.fr.ynov.kanoe.observer.Observer;
import main.java.fr.ynov.kanoe.service.Notification;

public class User implements Observer {
    protected String lastName;
    protected String firstName;
    protected String email;
    protected String phone;
    protected String password;
    protected int id;
    
    public User(String lastName, String firstName, String email, String phone, String password, int id) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.id = id;
    }

    
    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }
    
    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public int getId() {
        return id;
    }
    
    public boolean verifyPassword(String password) {
        return this.password.equals(password);
    }
    
    
    @Override
    public String toString() {
        return "Last name='" + lastName + '\'' +
                ", First name='" + firstName + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone;
    }

        @Override
    public void receiveNotification(Notification notification) {
        System.out.println("Notification for " + firstName + " " + lastName + ": " + notification);
    }

}
