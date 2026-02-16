package main.java.fr.ynov.kanoe.main;

import main.java.fr.ynov.kanoe.model.Reservation;
import main.java.fr.ynov.kanoe.service.ReservationService;

import java.util.Optional;

public class ExempleReservations {

    public static void main(String[] args) {
        // Création du service de réservation
        ReservationService reservationService = new ReservationService();

        System.out.println("=== DÉMONSTRATION : Gestion de plusieurs réservations ===\n");

        // 1. Créer plusieurs réservations
        System.out.println("1. Création de 3 réservations :");
        Reservation res1 = reservationService.creerReservation(2, 150.50);
        Reservation res2 = reservationService.creerReservation(4, 320.00);
        Reservation res3 = reservationService.creerReservation(1, 89.99);

        System.out.println("\n2. Affichage de toutes les réservations :");
        reservationService.afficherReservations();

        // 3. Confirmer une réservation
        System.out.println("\n3. Confirmation de la première réservation :");
        reservationService.confirmerReservation(res1.getNumeroReservation());

        // 4. Annuler une réservation
        System.out.println("\n4. Annulation de la deuxième réservation :");
        reservationService.annulerReservation(res2.getNumeroReservation());

        // 5. Rechercher une réservation spécifique
        System.out.println("\n5. Recherche de la réservation : " + res3.getNumeroReservation());
        Optional<Reservation> reservationTrouvee = reservationService.rechercherReservation(res3.getNumeroReservation());
        if (reservationTrouvee.isPresent()) {
            System.out.println("Réservation trouvée : " + reservationTrouvee.get());
        }

        // 6. Afficher le nombre total de réservations
        System.out.println("\n6. Nombre total de réservations : " + reservationService.getNombreReservations());

        // 7. Afficher toutes les réservations avec leurs statuts mis à jour
        System.out.println("\n7. État final de toutes les réservations :");
        reservationService.afficherReservations();

        // 8. Supprimer une réservation
        System.out.println("\n8. Suppression de la réservation " + res2.getNumeroReservation());
        boolean supprimee = reservationService.supprimerReservation(res2.getNumeroReservation());
        System.out.println("Réservation supprimée : " + supprimee);
        System.out.println("Nombre de réservations restantes : " + reservationService.getNombreReservations());

        reservationService.afficherReservations();
    }
}

