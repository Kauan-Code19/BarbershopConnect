package com.BarbershopConnect.BarbershopConnect.services.status_services;

import com.BarbershopConnect.BarbershopConnect.entities.Agendamento;
import com.BarbershopConnect.BarbershopConnect.entities.Status;
import com.BarbershopConnect.BarbershopConnect.repositories.AgendamentoRepository;
import com.BarbershopConnect.BarbershopConnect.services.interfaces.StatusAgendamentoService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Qualifier("concluido")
public class ConcluidoService implements StatusAgendamentoService {

    private final AgendamentoRepository agendamentoRepository;

    public ConcluidoService(AgendamentoRepository agendamentoRepository) {
        this.agendamentoRepository = agendamentoRepository;
    }

    @Override
    public void setStatus(Long id, Optional<Boolean> b) {
        Agendamento agendamento = agendamentoRepository.getReferenceById(id);

        if (agendamento.getStatus() == Status.AGENDADO && agendamento.getDataHora().isBefore(LocalDateTime.now())) {
            agendamento.setStatus(Status.CONCLUIDO);
            agendamentoRepository.save(agendamento);
        }
    }
}
