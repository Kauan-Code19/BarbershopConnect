package com.BarbershopConnect.BarbershopConnect.services;

import com.BarbershopConnect.BarbershopConnect.dto.BarbeariaDTO;
import com.BarbershopConnect.BarbershopConnect.dto.TipoDoCorteDTO;
import com.BarbershopConnect.BarbershopConnect.dto.TipoDoCorteIdDTO;
import com.BarbershopConnect.BarbershopConnect.entities.Barbearia;
import com.BarbershopConnect.BarbershopConnect.entities.TipoDoCorte;
import com.BarbershopConnect.BarbershopConnect.repositories.BarbeariaRepository;
import com.BarbershopConnect.BarbershopConnect.repositories.TipoDoCorteRepository;
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

import java.util.Set;
import java.util.stream.Collectors;

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
    public Set<TipoDoCorteDTO> adicionarTiposDoCorte (Long id, Set<TipoDoCorteDTO> tipoDoCorteDTOS) {
        try {
            Barbearia entity = barbeariaRepository.getReferenceById(id);

            Set<TipoDoCorte> tiposDoCorteEntities = tipoDoCorteDTOS.stream().map(dto -> {
                TipoDoCorte tipoDoCorte = new TipoDoCorte();
                tipoDoCorte.setNome(dto.getNome());
                tipoDoCorte.setDescricao(dto.getDescricao());
                tipoDoCorte.setPreco(dto.getPreco());
                return tipoDoCorteRepository.save(tipoDoCorte);
            }).collect(Collectors.toSet());

            entity.getTiposDoCorte().addAll(tiposDoCorteEntities);
            barbeariaRepository.save(entity);

            return tiposDoCorteEntities.stream().map(TipoDoCorteDTO::new).collect(Collectors.toSet());
        }catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Recurso não encontrado");
        }
    }

    @Transactional
    public Set<TipoDoCorteDTO> atualizarTiposDoCorte (Set<TipoDoCorteDTO> tipoDoCorteDTOS) {
        try {
            Set<TipoDoCorte> tiposDoCorteEntities = tipoDoCorteDTOS.stream().map(dto -> {
                TipoDoCorte tipoDoCorte = tipoDoCorteRepository.getReferenceById(dto.getId());
                tipoDoCorte.setNome(dto.getNome());
                tipoDoCorte.setDescricao(dto.getDescricao());
                tipoDoCorte.setPreco(dto.getPreco());
                return tipoDoCorteRepository.save(tipoDoCorte);
            }).collect(Collectors.toSet());

            return tiposDoCorteEntities.stream().map(TipoDoCorteDTO::new).collect(Collectors.toSet());
        }catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Recurso não encontrado");
        }
    }

    @Transactional(readOnly = true)
    public Set<TipoDoCorteDTO> buscarTiposDoCorte (Long id) {
        try {
            Set<Long> tipoDoCorteIds = barbeariaRepository.findTipoDoCorteIdsByBarbeariaId(id);

            Set<TipoDoCorte> entities = tipoDoCorteIds.stream().map(tipoDoCorteRepository::getReferenceById)
                    .collect(Collectors.toSet());

            return entities.stream().map(TipoDoCorteDTO::new).collect(Collectors.toSet());
        }catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Recurso não encontrado");
        }
    }

    @Transactional(readOnly = true)
    public Page<TipoDoCorteDTO> listarTiposDoCorte (Pageable pageable) {
        Page<TipoDoCorte> tipoDoCortes = tipoDoCorteRepository.findAll(pageable);

        return tipoDoCortes.map(TipoDoCorteDTO::new);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public void excluir (Long id, TipoDoCorteIdDTO tipoDoCorteIdDTO) {
        if (!tipoDoCorteRepository.existsById(tipoDoCorteIdDTO.getId()) || !barbeariaRepository.existsById(id)) {
            throw new ResourceNotFoundException("Recurso não encontrado");
        }

        try {
            barbeariaRepository.removerAssociacaoBarbeariaTipoDoCorte(id, tipoDoCorteIdDTO.getId());
            tipoDoCorteRepository.deleteById(tipoDoCorteIdDTO.getId());
        }catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Falha de integridade referencial");
        }
    }
}
