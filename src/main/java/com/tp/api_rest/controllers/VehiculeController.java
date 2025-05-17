package com.tp.api_rest.controllers;

import com.tp.api_rest.entities.Vehicule;
import com.tp.api_rest.repositories.VehiculeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vehicules")
@CrossOrigin(origins = "*")
public class VehiculeController {

    @Autowired
    private VehiculeRepository vehiculeRepository;

    // Récupérer tous les véhicules
    @GetMapping
    public List<Vehicule> getAllVehicules() {
        return vehiculeRepository.findAll();
    }

    // Récupérer un véhicule par ID
    @GetMapping("/{id}")
    public ResponseEntity<Vehicule> getVehiculeById(@PathVariable Long id) {
        return vehiculeRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Ajouter un nouveau véhicule
    @PostMapping
    public Vehicule createVehicule(@RequestBody Vehicule vehicule) {
        return vehiculeRepository.save(vehicule);
    }

    // Modifier un véhicule existant
    @PutMapping("/{id}")
    public ResponseEntity<Vehicule> updateVehicule(@PathVariable Long id, @RequestBody Vehicule vehiculeDetails) {
        return vehiculeRepository.findById(id).map(vehicule -> {
            vehicule.setModele(vehiculeDetails.getModele());
            vehicule.setType(vehiculeDetails.getType());
            vehicule.setNombrePlaces(vehiculeDetails.getNombrePlaces());
            vehicule.setPrix(vehiculeDetails.getPrix());
            return ResponseEntity.ok(vehiculeRepository.save(vehicule));
        }).orElse(ResponseEntity.notFound().build());
    }

    // Supprimer un véhicule
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVehicule(@PathVariable Long id) {
        return vehiculeRepository.findById(id).map(vehicule -> {
            vehiculeRepository.delete(vehicule);
            return ResponseEntity.ok().<Void>build();
        }).orElse(ResponseEntity.notFound().build());
    }
}
