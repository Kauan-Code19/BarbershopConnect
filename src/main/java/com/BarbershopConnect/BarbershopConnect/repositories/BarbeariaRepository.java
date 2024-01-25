package com.BarbershopConnect.BarbershopConnect.repositories;

import com.BarbershopConnect.BarbershopConnect.entities.Barbearia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Repository
public interface BarbeariaRepository extends JpaRepository<Barbearia, Long> {
    @Query("SELECT tdc.id FROM Barbearia b " + "JOIN b.tiposDoCorte tdc " + "WHERE b.id = :barbeariaId")
    Set<Long> findTipoDoCorteIdsByBarbeariaId(@Param("barbeariaId") Long barbeariaId);


    @Transactional
    @Modifying
    @Query(value = "DELETE FROM barbearia_tipo_do_corte WHERE barbearia_id = :barbeariaId AND tipo_do_corte_id = :tipoDoCorteId", nativeQuery = true)
    void removerAssociacaoBarbeariaTipoDoCorte(@Param("barbeariaId") Long barbeariaId, @Param("tipoDoCorteId") Long tipoDoCorteId);
}
