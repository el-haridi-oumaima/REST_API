package com.tp.api_rest.repositories;

import com.tp.api_rest.entities.Employe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeRepository extends JpaRepository<Employe, Long> {
    // pour les méthodes personnalisées
}
