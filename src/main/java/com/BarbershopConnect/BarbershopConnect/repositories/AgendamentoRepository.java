package com.BarbershopConnect.BarbershopConnect.repositories;

import com.BarbershopConnect.BarbershopConnect.entities.Agendamento;
import com.BarbershopConnect.BarbershopConnect.entities.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> {
    List<Agendamento> findByStatusAndDataHoraBefore(Status status, LocalDateTime dataHora);
    List<Agendamento> findByStatus(Status status);
    @Transactional
    @Modifying
    @Query("DELETE FROM Agendamento a WHERE a.id = :id")
    void deleteByIdCustom(@Param("id") Long id);
}
