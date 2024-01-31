package com.BarbershopConnect.BarbershopConnect.repositories;

import com.BarbershopConnect.BarbershopConnect.entities.Barbeiro;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BarbeiroRepository extends JpaRepository<Barbeiro, Long> {
    Page<Barbeiro> findByBarbeariaId (Long barbeariaId, Pageable pageable);
}
