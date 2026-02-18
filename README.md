# Kanoe

***

## Introduction

The goal of this project is to build a **Java CLI application** simulating a transport booking system. Users can browse available transports (planes, trains, buses), create an account, log in, book tickets with multiple passengers, manage their reservations, and receive notifications. The application demonstrates advanced **Object-Oriented Programming** concepts such as the **Builder**, **Observer**, and **Facade** design patterns.

## Requirements
***

In order to run the application, you need to install the **Java Development Kit (JDK)** version **21** or higher.<br>
You can download it for free from the official website.<br>
<a href="https://www.oracle.com/java/technologies/downloads/">Link to the download page for the JDK</a>

## How to use the application
***

To launch the application, you need to compile and run the `ApplicationRunner.java` file.

* Go to a command prompt and navigate to the `/Kanoe` folder.<br>
  Once inside, use these commands:

  ```
  javac -d out src/main/java/fr/ynov/kanoe/**/*.java
  java -cp out main.java.fr.ynov.kanoe.main.ApplicationRunner
  ```

* Wait for the application to start. When you see the main menu:<br>
  ```
  === Main menu, Welcome to Kanoe ! ===
  1. Login
  2. Sign in
  3. Show available transports
  4. Leave
  ```
  You are ready to use the application.

* A default test user is pre-registered:<br>
  **Email:** `a` — **Password:** `a`

(this account is for testing purposes, you can create your own account by selecting option 2 in the main menu)

## Features
***

### Guest Menu

The guest menu allows unregistered visitors to:
* **Login** to an existing account
* **Sign up** to create a new account
* **Browse available transports** (planes, trains, buses)

### User Menu

Once logged in, a user can:
* **View available transports** with details (route, departure/arrival times, price, available seats)
* **Book a transport** — select a transport, add passengers, choose a ticket type (Economy, Business, First Class) and a payment method (Credit Card, Debit Card, PayPal, Bank Transfer)
* **Manage reservations** — view all personal bookings, confirm or cancel a pending reservation
* **Create a notification** — send a scoped notification to users who booked a specific transport

### Design Patterns Used

* **Builder Pattern** — Used to construct `Plane`, `Train`, and `Bus` objects with a fluent API
* **Observer Pattern** — `NotificationManager` notifies registered `User` observers when events occur (booking confirmation, welcome message, custom notifications)
* **Facade Pattern** — `BookingSystem` acts as a unified facade, centralizing access to transports, bookings, users, and notifications behind a simple high-level API

### SOLID Principles

* **Single Responsibility Principle (SRP)** — Each class has a single, well-defined responsibility: `Payment` handles payment processing, `NotificationManager` manages observer subscriptions and dispatching, `BookingHandling` manages a single booking's lifecycle, and the CLI classes (`GuestAccountCLI`, `UserConnectedCLI`) handle only user interaction.
* **Open/Closed Principle (OCP)** — The `Transport` abstract class allows the system to be extended with new transport types (e.g. `Plane`, `Train`, `Bus`) without modifying existing code. Adding a new transport only requires creating a new subclass.
* **Liskov Substitution Principle (LSP)** — `Plane`, `Train`, and `Bus` are used interchangeably wherever a `Transport` is expected (in `BookingSystem`, CLI layers, bookings) without altering the correctness of the program.
* **Interface Segregation Principle (ISP)** — The `Observer` interface is minimal and focused, exposing only a single method `receiveNotification()`. Implementing classes are not forced to depend on methods they don't use.
* **Dependency Inversion Principle (DIP)** — `NotificationManager` depends on the `Observer` abstraction (interface), not on the concrete `User` class. This decouples the notification system from the user model.

## Project Structure
***

````
/Kanoe
│── src/main/java/fr/ynov/kanoe/
│   ├── /cli                        # Command-Line Interface layer
│   │   ├── GuestAccountCLI.java    # Main menu for guests (login, sign up, browse)
│   │   ├── UserConnectedCLI.java   # Menu for logged-in users (book, manage, notify)
│   │   ├── BookingHandling.java    # Manage a single booking (confirm / cancel)
│   ├── /enums                      # Enumerations
│   │   ├── TicketType.java         # ECO, BUSINESS, FIRST_CLASS
│   │   ├── StatusReservation.java  # PENDING, SUCCESS, CANCELED
│   │   ├── StatusPayment.java      # PENDING, SUCCESS, CANCELED
│   │   ├── paymentMethod.java      # CREDIT_CARD, DEBIT_CARD, PAYPAL, BANK_TRANSFER
│   ├── /main                       # Application entry point
│   │   ├── ApplicationRunner.java  # Main class — initializes data and launches the CLI
│   ├── /model                      # Domain models
│   │   ├── Transport.java          # Abstract base class for all transports
│   │   ├── Plane.java              # Plane model (Builder pattern)
│   │   ├── Train.java              # Train model (Builder pattern)
│   │   ├── Bus.java                # Bus model (Builder pattern)
│   │   ├── User.java               # User account (implements Observer)
│   │   ├── Passenger.java          # Passenger info for a booking
│   │   ├── Booking.java            # Reservation with payment processing
│   ├── /observer                   # Observer pattern implementation
│   │   ├── Observer.java           # Observer interface
│   │   ├── NotificationManager.java# Manages observers and dispatches notifications
│   ├── /service                    # Business logic / services
│   │   ├── BookingSystem.java      # Facade — central system for all operations
│   │   ├── Payment.java            # Payment processing with simulated success/failure
│   │   ├── Notification.java       # Notification model (title, description, scope)
│   ├── /utils                      # Utility classes
│   │   ├── ScannerUtils.java       # Safe integer input reading
│   │   ├── NotificationCreator.java# Creates and dispatches scoped notifications
│── README.md                       # Project documentation
````

Key Components:<br>

* **cli/** → Handles all user interactions through the command line (guest menu, user menu, booking management).
* **model/** → Defines the domain objects:
    * `Transport.java` (abstract), `Plane.java`, `Train.java`, `Bus.java`: Transport hierarchy with Builder pattern.
    * `User.java`: User account implementing the Observer interface.
    * `Booking.java`: Reservation with integrated payment processing.
    * `Passenger.java`: Passenger information attached to a booking.
* **service/** → Core business logic:
    * `BookingSystem.java`: Facade centralizing transport, booking, user, and notification management.
    * `Payment.java`: Simulates payment processing (80% success rate).
    * `Notification.java`: Notification data model.
* **observer/** → Observer pattern for the notification system.
* **enums/** → Status and type enumerations for reservations, payments, and tickets.
* **utils/** → Helper classes for input handling and notification creation.

## About the project
***

### Versions

* 1.0 — Release **18/02/2025**

### Technologies Used

<img src="https://skillicons.dev/icons?i=java" alt="Java logo">

### Authors

* **Noah CHARRIN--BOURRAT**
* **Raphaël BONNET**
* **Mathieu BERIAC**
