package com.BarbershopConnect.BarbershopConnect.services.interfaces;

import com.BarbershopConnect.BarbershopConnect.entities.Status;

import java.util.Optional;

public interface StatusAgendamentoService {
    void setStatus(Long id, Optional<Boolean> b);
}
