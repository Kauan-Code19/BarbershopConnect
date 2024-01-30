package com.BarbershopConnect.BarbershopConnect.services;

import com.BarbershopConnect.BarbershopConnect.dto.BarbeariaDTO;
import com.BarbershopConnect.BarbershopConnect.dto.BarbeiroDTO;
import com.BarbershopConnect.BarbershopConnect.entities.Barbearia;
import com.BarbershopConnect.BarbershopConnect.entities.Barbeiro;
import com.BarbershopConnect.BarbershopConnect.repositories.BarbeariaRepository;
import com.BarbershopConnect.BarbershopConnect.repositories.BarbeiroRepository;
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
}
