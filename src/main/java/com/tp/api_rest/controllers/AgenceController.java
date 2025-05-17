package com.tp.api_rest.controllers;

import com.tp.api_rest.entities.Agence;
import com.tp.api_rest.repositories.AgenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/agences")
@CrossOrigin(origins = "*")
public class AgenceController {

    @Autowired
    private AgenceRepository agenceRepository;

    // Récupérer toutes les agences
    @GetMapping
    public List<Agence> getAllAgences() {
        return agenceRepository.findAll();
    }

    // Récupérer une agence par ID
    @GetMapping("/{id}")
    public ResponseEntity<Agence> getAgenceById(@PathVariable Long id) {
        return agenceRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Ajouter une nouvelle agence
    @PostMapping
    public Agence createAgence(@RequestBody Agence agence) {
        return agenceRepository.save(agence);
    }

    // Modifier une agence existante
    @PutMapping("/{id}")
    public ResponseEntity<Agence> updateAgence(@PathVariable Long id, @RequestBody Agence agenceDetails) {
        return agenceRepository.findById(id).map(agence -> {
            agence.setNom(agenceDetails.getNom());
            agence.setAdresse(agenceDetails.getAdresse());
            return ResponseEntity.ok(agenceRepository.save(agence));
        }).orElse(ResponseEntity.notFound().build());
    }

    // Supprimer une agence
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAgence(@PathVariable Long id) {
        return agenceRepository.findById(id).map(agence -> {
            agenceRepository.delete(agence);
            return ResponseEntity.ok().<Void>build();
        }).orElse(ResponseEntity.notFound().build());
    }
}
