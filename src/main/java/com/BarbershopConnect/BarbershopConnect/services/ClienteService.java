package com.BarbershopConnect.BarbershopConnect.services;

import com.BarbershopConnect.BarbershopConnect.dto.ClienteDTO;
import com.BarbershopConnect.BarbershopConnect.dto.ClienteResponseDTO;
import com.BarbershopConnect.BarbershopConnect.entities.Cliente;
import com.BarbershopConnect.BarbershopConnect.repositories.ClienteRepository;
import com.BarbershopConnect.BarbershopConnect.services.exceptions.DatabaseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;

    @Autowired
    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @Transactional
    public ClienteResponseDTO cadastrar(ClienteDTO clienteDTO) {
        try {
            Cliente entity = new Cliente();

            entity.setNome(clienteDTO.getNome());
            entity.setEmail(clienteDTO.getEmail());
            entity.setSenha(clienteDTO.getSenha());
            entity.setEndereco(clienteDTO.getEndereco());
            entity.setContato(clienteDTO.getContato());

            entity = clienteRepository.save(entity);

            return new ClienteResponseDTO(entity);
        }catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Falha de integridade referencial");
        }
    }
}
