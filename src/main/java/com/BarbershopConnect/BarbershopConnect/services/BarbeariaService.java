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
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

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
    public BarbeiroDTO buscarBarbeiro (Long idBarbearia, Long idBarbeiro) {
        try {
            Barbeiro barbeiro = barbeiroRepository.getReferenceById(idBarbeiro);

            if (!barbeiro.getBarbearia().getId().equals(idBarbearia)) {
                throw new ResourceNotFoundException("O barbeiro não pertence a essa barbearia");
            }

            return new BarbeiroDTO(barbeiro);
        }catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Recurso não encontrado");
        }
    }

    @Transactional(readOnly = true)
    public Page<BarbeiroDTO> listarBarbeiros (Long id, Pageable pageable) {
        try {
            Page<Barbeiro> barbeiros = barbeiroRepository.findByBarbeariaId(id, pageable);

            return barbeiros.map(BarbeiroDTO::new);
        }catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("Recurso não encontrado");
        }
    }
}
