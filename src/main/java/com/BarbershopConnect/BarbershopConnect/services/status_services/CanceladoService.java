package com.BarbershopConnect.BarbershopConnect.services.status_services;

import com.BarbershopConnect.BarbershopConnect.entities.Agendamento;
import com.BarbershopConnect.BarbershopConnect.entities.Status;
import com.BarbershopConnect.BarbershopConnect.repositories.AgendamentoRepository;
import com.BarbershopConnect.BarbershopConnect.services.interfaces.StatusAgendamentoService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Qualifier("cancelado")
public class CanceladoService implements StatusAgendamentoService {

    private final AgendamentoRepository agendamentoRepository;

    public CanceladoService(AgendamentoRepository agendamentoRepository) {
        this.agendamentoRepository = agendamentoRepository;
    }

    @Override
    public void setStatus(Long id, Optional<Boolean> b) {
        if (b.isPresent() && !b.get()) {
            Agendamento agendamento = agendamentoRepository.getReferenceById(id);
            agendamento.setStatus(Status.CANCELADO);
            agendamentoRepository.save(agendamento);
        }
    }
}
