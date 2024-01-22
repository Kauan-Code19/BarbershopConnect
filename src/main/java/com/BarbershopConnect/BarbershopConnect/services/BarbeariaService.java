package com.BarbershopConnect.BarbershopConnect.services;

import com.BarbershopConnect.BarbershopConnect.dto.BarbeariaDTO;
import com.BarbershopConnect.BarbershopConnect.dto.BarbeiroDTO;
import com.BarbershopConnect.BarbershopConnect.entities.Barbearia;
import com.BarbershopConnect.BarbershopConnect.entities.Barbeiro;
import com.BarbershopConnect.BarbershopConnect.repositories.BarbeariaRepository;
import com.BarbershopConnect.BarbershopConnect.repositories.BarbeiroRepository;
import com.BarbershopConnect.BarbershopConnect.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
}
