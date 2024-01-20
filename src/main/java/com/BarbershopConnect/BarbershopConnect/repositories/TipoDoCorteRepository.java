package com.BarbershopConnect.BarbershopConnect.repositories;

import com.BarbershopConnect.BarbershopConnect.entities.TipoDoCorte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoDoCorteRepository extends JpaRepository<TipoDoCorte, Long> {
}
