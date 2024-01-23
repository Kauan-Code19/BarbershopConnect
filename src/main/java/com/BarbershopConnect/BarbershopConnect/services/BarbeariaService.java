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

    @Transactional
    public BarbeariaDTO cadastrar (BarbeariaDTO barbeariaDTO) {
        Barbearia entity = new Barbearia();

        entity.setNome(barbeariaDTO.getNome());
        entity.setEmail(barbeariaDTO.getEmail());
        entity.setEndereco(barbeariaDTO.getEndereco());
        entity.setContato(barbeariaDTO.getContato());

        entity = barbeariaRepository.save(entity);

        return new BarbeariaDTO(entity);
    }

    @Transactional
    public BarbeiroDTO cadastrarBarbeiro (BarbeiroDTO barbeiroDTO) {
        Barbeiro entity = new Barbeiro();
        Barbearia barbearia = barbeariaRepository.getReferenceById(barbeiroDTO.getBarbearia().getId());

        entity.setNome(barbeiroDTO.getNome());
        entity.setDescricao(barbeiroDTO.getDescricao());
        entity.setContato(barbeiroDTO.getContato());
        entity.setBarbearia(barbearia);

        entity = barbeiroRepository.save(entity);

        return  new BarbeiroDTO(entity);
    }

    @Transactional
    public BarbeiroDTO atualizarBarbeiro (Long id, BarbeiroDTO barbeiroDTO) {
        try {
            Barbeiro entity = barbeiroRepository.getReferenceById(id);
            Barbearia barbearia = barbeariaRepository.getReferenceById(barbeiroDTO.getBarbearia().getId());

            entity.setNome(barbeiroDTO.getNome());
            entity.setDescricao(barbeiroDTO.getDescricao());
            entity.setContato(barbeiroDTO.getContato());
            entity.setBarbearia(barbearia);

            entity = barbeiroRepository.save(entity);

            return new BarbeiroDTO(entity);
        }catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Recurso não encontrado");
        }
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public void deletarBarbeiro (Long id) {
        if (!barbeiroRepository.existsById(id))
            throw new ResourceNotFoundException("Recurso não encontrado");
        try {
            barbeiroRepository.deleteById(id);
        }catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Falha de integridade referencial");
        }
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
