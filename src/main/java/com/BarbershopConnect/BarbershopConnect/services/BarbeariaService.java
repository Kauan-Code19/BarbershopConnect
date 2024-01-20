package com.BarbershopConnect.BarbershopConnect.services;

import com.BarbershopConnect.BarbershopConnect.dto.BarbeariaDTO;
import com.BarbershopConnect.BarbershopConnect.dto.TipoDoCorteDTO;
import com.BarbershopConnect.BarbershopConnect.entities.Barbearia;
import com.BarbershopConnect.BarbershopConnect.entities.TipoDoCorte;
import com.BarbershopConnect.BarbershopConnect.repositories.BarbeariaRepository;
import com.BarbershopConnect.BarbershopConnect.repositories.TipoDoCorteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BarbeariaService {

    private final BarbeariaRepository barbeariaRepository;
    private final TipoDoCorteRepository tipoDoCorteRepository;

    @Autowired
    public BarbeariaService(BarbeariaRepository barbeariaRepository, TipoDoCorteRepository tipoDoCorteRepository) {
        this.barbeariaRepository = barbeariaRepository;
        this.tipoDoCorteRepository = tipoDoCorteRepository;
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
    public BarbeariaDTO adicionarTiposDoCorte (BarbeariaDTO barbeariaDTO) {
        Barbearia entity = barbeariaRepository.getReferenceById(barbeariaDTO.getId());

        for (TipoDoCorteDTO tipoDoCorteDTO : barbeariaDTO.getTiposDoCorte()) {
            TipoDoCorte tipoDoCorte = new TipoDoCorte();
            tipoDoCorte.setNome(tipoDoCorteDTO.getNome());
            tipoDoCorte.setDescricao(tipoDoCorteDTO.getDescricao());
            tipoDoCorte.setPreco(tipoDoCorteDTO.getPreco());

            tipoDoCorteRepository.save(tipoDoCorte);

            entity.getTiposDoCorte().add(tipoDoCorte);
        }

        barbeariaRepository.save(entity);

        return new BarbeariaDTO(entity);
    }
}
