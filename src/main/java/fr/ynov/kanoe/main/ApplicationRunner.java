package main.java.fr.ynov.kanoe.main;

import main.java.fr.ynov.kanoe.model.Avion;
import main.java.fr.ynov.kanoe.model.Bus;
import main.java.fr.ynov.kanoe.model.Train;
import main.java.fr.ynov.kanoe.service.SystemeReservation;

import java.time.LocalDateTime;

import static main.java.fr.ynov.kanoe.cli.GuestAccountCLI.displayMainMenu;

public class ApplicationRunner {
    public static void main(String[] args) {
        SystemeReservation system = new SystemeReservation();
        // Creating some dummy data for testing
        system.ajouterTransport(new Avion.Builder()
                .setId("AV001")
                .setOperator("Air France")
                .setStatingPoint("Paris")
                .setEndPoint("New York")
                .setTimeDepart(LocalDateTime.parse("2024-07-01T10:00:00"))
                .setTimeArriving(LocalDateTime.parse("2024-07-01T14:00:00"))
                .setCapacity(400)
                .setBasePrice(500.0)
                .setPlaneNumber("AF001")
                .setInFlightEntertainment(true)
                .build());

        system.ajouterTransport(new Train.Builder()
                .setId("TR001")
                .setOperator("SNCF")
                .setStatingPoint("Paris")
                .setEndPoint("Lyon")
                .setTimeDepart(LocalDateTime.parse("2024-07-01T08:00:00"))
                .setTimeArriving(LocalDateTime.parse("2024-07-01T10:00:00"))
                .setCapacity(300)
                .setBasePrice(100.0)
                .setTrainNumber("SNCF001")
                .setHasWifi(true)
                .setHasDiningCar(true)
                .build());

        system.ajouterTransport(new Bus.Builder()
                .setId("BS001")
                .setOperator("FlixBus")
                .setStatingPoint("Paris")
                .setEndPoint("Brussels")
                .setTimeDepart(LocalDateTime.parse("2024-07-01T09:00:00"))
                .setTimeArriving(LocalDateTime.parse("2024-07-01T12:00:00"))
                .setCapacity(50)
                .setBasePrice(30.0)
                .setBusNumber("FLX001")
                .setAirConditioning(true)
                .build());

        // Display the main menu
        displayMainMenu(system);
    }
}