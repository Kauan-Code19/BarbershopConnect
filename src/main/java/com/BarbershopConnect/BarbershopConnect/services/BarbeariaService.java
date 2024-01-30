package com.BarbershopConnect.BarbershopConnect.services;

import com.BarbershopConnect.BarbershopConnect.dto.BarbeariaDTO;
import com.BarbershopConnect.BarbershopConnect.dto.BarbeariaResponseDTO;
import com.BarbershopConnect.BarbershopConnect.entities.Barbearia;
import com.BarbershopConnect.BarbershopConnect.repositories.BarbeariaRepository;
import com.BarbershopConnect.BarbershopConnect.services.exceptions.DatabaseException;
import com.BarbershopConnect.BarbershopConnect.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BarbeariaService {

    private final BarbeariaRepository barbeariaRepository;

    @Autowired
    public BarbeariaService(BarbeariaRepository barbeariaRepository) {
        this.barbeariaRepository = barbeariaRepository;
    }

    @Transactional
    public BarbeariaResponseDTO cadastrar (BarbeariaDTO barbeariaDTO) {
        try {
            Barbearia entity = new Barbearia();

            entity.setNome(barbeariaDTO.getNome());
            entity.setEmail(barbeariaDTO.getEmail());
            entity.setSenha(barbeariaDTO.getSenha());
            entity.setEndereco(barbeariaDTO.getEndereco());
            entity.setContato(barbeariaDTO.getContato());

            entity = barbeariaRepository.save(entity);

            return new BarbeariaResponseDTO(entity);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Falha de integridade referencial");
        }
    }

    @Transactional
    public BarbeariaResponseDTO atualizar (Long id, BarbeariaDTO barbeariaDTO) {
        try {
            Barbearia entity = barbeariaRepository.getReferenceById(id);

            entity.setNome(barbeariaDTO.getNome());
            entity.setEmail(barbeariaDTO.getEmail());
            entity.setSenha(barbeariaDTO.getSenha());
            entity.setEndereco(barbeariaDTO.getEndereco());
            entity.setContato(barbeariaDTO.getContato());

            entity = barbeariaRepository.save(entity);

            return new BarbeariaResponseDTO(entity);
        }catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Recurso não Encontrado");
        }
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public void deletar (Long id) {
        if (!barbeariaRepository.existsById(id)) {
            throw new ResourceNotFoundException("Recurso não encontrado");
        }

        try {
            barbeariaRepository.deleteById(id);
        }catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Falha de integridade referencial");
        }
    }
}
