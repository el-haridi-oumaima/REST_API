package com.tp.api_rest.controllers;

import com.tp.api_rest.entities.Reservation;
import com.tp.api_rest.entities.Agence;
import com.tp.api_rest.entities.Employe;
import com.tp.api_rest.entities.Vehicule;
import com.tp.api_rest.repositories.ReservationRepository;
import com.tp.api_rest.repositories.AgenceRepository;
import com.tp.api_rest.repositories.EmployeRepository;
import com.tp.api_rest.repositories.VehiculeRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reservations")
@CrossOrigin(origins = "*")
public class ReservationController {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private EmployeRepository employeRepository;

    @Autowired
    private VehiculeRepository vehiculeRepository;

    @Autowired
    private AgenceRepository agenceRepository;

    // Récupérer toutes les réservations
    @GetMapping
    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    // Récupérer une réservation par ID
    @GetMapping("/{id}")
    public ResponseEntity<Reservation> getReservationById(@PathVariable Long id) {
        return reservationRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Ajouter une nouvelle réservation
    @PostMapping
    public ResponseEntity<?> createReservation(@RequestBody Reservation reservation) {
        // Vérifier que Employe, Vehicule et Agence existent
        Long employeId = reservation.getEmploye().getId();
        Long vehiculeId = reservation.getVehicule().getId();
        Long agenceId = reservation.getAgence().getId();

        Employe employe = employeRepository.findById(employeId).orElse(null);
        Vehicule vehicule = vehiculeRepository.findById(vehiculeId).orElse(null);
        Agence agence = agenceRepository.findById(agenceId).orElse(null);

        if (employe == null || vehicule == null || agence == null) {
            return ResponseEntity.badRequest().body("Employe, Vehicule ou Agence non trouvés");
        }

        reservation.setEmploye(employe);
        reservation.setVehicule(vehicule);
        reservation.setAgence(agence);

        Reservation savedReservation = reservationRepository.save(reservation);
        return ResponseEntity.ok(savedReservation);
    }

    // Modifier une réservation existante
    @PutMapping("/{id}")
    public ResponseEntity<?> updateReservation(@PathVariable Long id, @RequestBody Reservation reservationDetails) {
        return reservationRepository.findById(id).map(reservation -> {

            Long employeId = reservationDetails.getEmploye().getId();
            Long vehiculeId = reservationDetails.getVehicule().getId();
            Long agenceId = reservationDetails.getAgence().getId();

            Employe employe = employeRepository.findById(employeId).orElse(null);
            Vehicule vehicule = vehiculeRepository.findById(vehiculeId).orElse(null);
            Agence agence = agenceRepository.findById(agenceId).orElse(null);

            if (employe == null || vehicule == null || agence == null) {
                return ResponseEntity.badRequest().body("Employe, Vehicule ou Agence non trouvés");
            }

            reservation.setEmploye(employe);
            reservation.setVehicule(vehicule);
            reservation.setAgence(agence);
            reservation.setDateDebut(reservationDetails.getDateDebut());
            reservation.setDateFin(reservationDetails.getDateFin());

            Reservation updated = reservationRepository.save(reservation);
            return ResponseEntity.ok(updated);

        }).orElse(ResponseEntity.notFound().build());
    }

    // Supprimer une réservation
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        return reservationRepository.findById(id).map(reservation -> {
            reservationRepository.delete(reservation);
            return ResponseEntity.ok().<Void>build();
        }).orElse(ResponseEntity.notFound().build());
    }
}
