package com.BarbershopConnect.BarbershopConnect.repositories;

import com.BarbershopConnect.BarbershopConnect.entities.Barbearia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BarbeariaRepository extends JpaRepository<Barbearia, Long> {
}
