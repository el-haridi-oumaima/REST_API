package com.tp.api_rest.controllers;

import com.tp.api_rest.entities.Employe;
import com.tp.api_rest.repositories.EmployeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employes")
@CrossOrigin(origins = "*") // optionnel pour permettre les requêtes depuis Postman ou un frontend
public class EmployeController {

    @Autowired
    private EmployeRepository employeRepository;

    // GET all
    @GetMapping
    public List<Employe> getAllEmployes() {
        return employeRepository.findAll();
    }

    // GET by ID
    @GetMapping("/{id}")
    public ResponseEntity<Employe> getEmployeById(@PathVariable Long id) {
        return employeRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST
    @PostMapping
    public Employe createEmploye(@RequestBody Employe employe) {
        return employeRepository.save(employe);
    }

    // PUT
    @PutMapping("/{id}")
    public ResponseEntity<Employe> updateEmploye(@PathVariable Long id, @RequestBody Employe employeDetails) {
        return employeRepository.findById(id).map(employe -> {
            employe.setNom(employeDetails.getNom());
            employe.setPrenom(employeDetails.getPrenom());
            employe.setDateNaissance(employeDetails.getDateNaissance());
            employe.setSalaire(employeDetails.getSalaire());
            employeRepository.save(employe);
            return ResponseEntity.ok(employe);
        }).orElse(ResponseEntity.notFound().build());
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmploye(@PathVariable Long id) {
        return employeRepository.findById(id).map(employe -> {
            employeRepository.delete(employe);
            return ResponseEntity.ok().<Void>build();
        }).orElse(ResponseEntity.notFound().build());
    }
}
