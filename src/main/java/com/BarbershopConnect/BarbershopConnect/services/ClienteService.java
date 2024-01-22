package com.BarbershopConnect.BarbershopConnect.services;

import com.BarbershopConnect.BarbershopConnect.dto.ClienteDTO;
import com.BarbershopConnect.BarbershopConnect.entities.Cliente;
import com.BarbershopConnect.BarbershopConnect.repositories.ClienteRepository;
import com.BarbershopConnect.BarbershopConnect.services.exceptions.DatabaseException;
import com.BarbershopConnect.BarbershopConnect.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;

    @Autowired
    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @Transactional
    public ClienteDTO cadastrar(ClienteDTO clienteDTO) {
        Cliente entity = new Cliente();

        entity.setNome(clienteDTO.getNome());
        entity.setEmail(clienteDTO.getEmail());
        entity.setEndereco(clienteDTO.getEndereco());
        entity.setContato(clienteDTO.getContato());

        entity = clienteRepository.save(entity);

        return new ClienteDTO(entity);
    }

    @Transactional
    public  ClienteDTO atualizar (Long id, ClienteDTO clienteDTO) {
        try {
            Cliente entity = clienteRepository.getReferenceById(id);

            entity.setNome(clienteDTO.getNome());
            entity.setEmail(clienteDTO.getEmail());
            entity.setEndereco(clienteDTO.getEndereco());
            entity.setContato(clienteDTO.getContato());

            entity = clienteRepository.save(entity);

            return new ClienteDTO(entity);
        }catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Recurso não encontrado");
        }
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public void deletar (Long id) {
        if (!clienteRepository.existsById(id)) {
            throw new ResourceNotFoundException("Recurso não encontrado");
        }

        try {
            clienteRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Falha de integridade referencial");
        }
    }
}
