package com.BarbershopConnect.BarbershopConnect.services;

import com.BarbershopConnect.BarbershopConnect.dto.BarbeariaDTO;
import com.BarbershopConnect.BarbershopConnect.entities.Barbearia;
import com.BarbershopConnect.BarbershopConnect.repositories.BarbeariaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BarbeariaService {

    private final BarbeariaRepository barbeariaRepository;

    @Autowired
    public BarbeariaService(BarbeariaRepository barbeariaRepository) {
        this.barbeariaRepository = barbeariaRepository;
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
}
