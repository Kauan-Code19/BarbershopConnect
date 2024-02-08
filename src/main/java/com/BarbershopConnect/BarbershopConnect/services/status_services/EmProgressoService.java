package com.BarbershopConnect.BarbershopConnect.services.status_services;

import com.BarbershopConnect.BarbershopConnect.entities.Agendamento;
import com.BarbershopConnect.BarbershopConnect.entities.Status;
import com.BarbershopConnect.BarbershopConnect.repositories.AgendamentoRepository;
import com.BarbershopConnect.BarbershopConnect.services.interfaces.StatusAgendamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Primary
public class EmProgressoService implements StatusAgendamentoService {

    private final AgendamentoRepository agendamentoRepository;

    @Autowired
    public EmProgressoService(AgendamentoRepository agendamentoRepository) {
        this.agendamentoRepository = agendamentoRepository;
    }

    @Override
    public void setStatus(Long id, Optional<Boolean> b) {
        if (agendamentoRepository.existsById(id) && b.isEmpty()) {
            Agendamento agendamento = agendamentoRepository.getReferenceById(id);
            agendamento.setStatus(Status.EM_PROGRESSO);
        }
    }
}
