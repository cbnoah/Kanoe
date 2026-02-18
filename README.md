# Kanoe

Kanoe is a small Java project that models a reservation system for different transports (train, bus, plane). It contains domain models, services for reservation and payments, and a simple notification mechanism using an Observer pattern.

## Table of contents

- About
- Features
- Project structure
- Build & run
- CLI (planned)
- Contributing
- Authors

## About

This repository contains a prototype of a transport reservation system. The goal is to provide a clear, object-oriented Java implementation with separated services for reservations, payments and notifications. The codebase is intended for learning and as a base for further features.

## Features

- Models for users, transports and reservations
- Reservation system with search, create and management of transports and users
- Notification system using a `NotificationManager` and an `Observer` interface
- Basic price calculation and payment service stubs

## Project structure

The important source folders are under `src/main/java/fr/ynov/kanoe/` and include:

- `model/` — domain objects (Users, Transport, Reservation, etc.)
- `service/` — services such as `SystemeReservation`, `Payment`, `Notification`
- `enums/` — project enums (ticket types, statuses, methods)
- `observer/` — simple observer/notification manager

## Build & run

Recommended: open the project in your IDE (IntelliJ IDEA, Eclipse) and run the `ApplicationRunner` (or the main class provided in `fr.ynov.kanoe.main`).

Command-line (simple):

1. Compile all sources:

```bash
cd '/[project path]/Kanoe'
mkdir -p out
javac -d out $(find src/main/java -name "*.java")
```

2. Run the main class (replace with the actual fully-qualified main class name if different):

```bash
java -cp out fr.ynov.kanoe.main.ApplicationRunner
```

Note: If you use Maven or Gradle, prefer using your build tool (e.g., `mvn compile` / `gradle build`) and the IDE integration.

## CLI (planned)

We plan to add a command-line interface (CLI) to allow quick interactions with the reservation system (search, book, list transports, manage users). Placeholder for planned CLI commands and usage examples will be added here when the feature is implemented.

Planned CLI features (examples):

- `kanoe search --from <city> --to <city> --date <yyyy-mm-dd>`
- `kanoe book --transport-id <id> --user <email> --seats <n>`
- `kanoe users add --first <first> --last <last> --email <email>`

## Contributing

Contributions are welcome. Create a branch named `feature/<your-feature>` or `fix/<issue>` and open a pull request against `develop`.

Please follow the existing Java package structure and include small, focused commits. Use the Conventional Commits format for clear changelogs.

Suggested commit message for documentation changes:

```
docs: add English README with CLI placeholder
```

## Authors

This project was created by:

- Noah CHARRIN--BOURRAT
- Raphaël BONNET
- Mathieu BERIAC

---

