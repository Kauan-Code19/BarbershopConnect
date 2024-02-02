package com.BarbershopConnect.BarbershopConnect.services;

import com.BarbershopConnect.BarbershopConnect.dto.ClienteDTO;
import com.BarbershopConnect.BarbershopConnect.dto.ClienteResponseDTO;
import com.BarbershopConnect.BarbershopConnect.entities.Cliente;
import com.BarbershopConnect.BarbershopConnect.repositories.ClienteRepository;
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
  

    @Transactional
    public  ClienteResponseDTO atualizar (Long id, ClienteDTO clienteDTO) {
        try {
            Cliente entity = clienteRepository.getReferenceById(id);

            entity.setNome(clienteDTO.getNome());
            entity.setEmail(clienteDTO.getEmail());
            entity.setSenha(clienteDTO.getSenha());
            entity.setEndereco(clienteDTO.getEndereco());
            entity.setContato(clienteDTO.getContato());

            entity = clienteRepository.save(entity);

            return new ClienteResponseDTO(entity);
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

    @Transactional(readOnly = true)
    public ClienteResponseDTO buscar(Long id) {
        try {
            Cliente cliente = clienteRepository.getReferenceById(id);

            return new ClienteResponseDTO(cliente);
        }catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Recurso não encontrado");
        }
    }

    @Transactional(readOnly = true)
    public Page<ClienteResponseDTO> listar(Pageable pageable) {
        Page<Cliente> cliente = clienteRepository.findAll(pageable);

        return cliente.map(ClienteResponseDTO::new);
    }
}