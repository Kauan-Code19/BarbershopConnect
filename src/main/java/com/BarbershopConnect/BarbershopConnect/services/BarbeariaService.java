package com.BarbershopConnect.BarbershopConnect.services;

import com.BarbershopConnect.BarbershopConnect.dto.BarbeariaDTO;
import com.BarbershopConnect.BarbershopConnect.dto.BarbeiroDTO;
import com.BarbershopConnect.BarbershopConnect.entities.Barbearia;
import com.BarbershopConnect.BarbershopConnect.entities.Barbeiro;
import com.BarbershopConnect.BarbershopConnect.repositories.BarbeariaRepository;
import com.BarbershopConnect.BarbershopConnect.repositories.BarbeiroRepository;
import com.BarbershopConnect.BarbershopConnect.services.exceptions.DatabaseException;
import com.BarbershopConnect.BarbershopConnect.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BarbeariaService {

    private final BarbeariaRepository barbeariaRepository;
    private final BarbeiroRepository barbeiroRepository;

    @Autowired
    public BarbeariaService(BarbeariaRepository barbeariaRepository, BarbeiroRepository barbeiroRepository) {
        this.barbeariaRepository = barbeariaRepository;
        this.barbeiroRepository = barbeiroRepository;
    }

    @Transactional(readOnly = true)
    public BarbeiroDTO buscarBarbeiro (Long id) {
        try {
            Barbeiro barbeiro = barbeiroRepository.getReferenceById(id);

            return new BarbeiroDTO(barbeiro);
        }catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Recurso não encontrado");
        }
    }

    @Transactional(readOnly = true)
    public Page<BarbeiroDTO> listarBarbeiros (Pageable pageable) {
        Page<Barbeiro> barbeiros = barbeiroRepository.findAll(pageable);

        return barbeiros.map(BarbeiroDTO::new);
    }
}
