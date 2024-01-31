package com.BarbershopConnect.BarbershopConnect.services;

import com.BarbershopConnect.BarbershopConnect.dto.TipoDoCorteDTO;
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
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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
    public Page<TipoDoCorteDTO> buscarTiposDoCorte (Long id, Pageable pageable) {
        try {
            Set<Long> tipoDoCorteIds = barbeariaRepository.findTipoDoCorteIdsByBarbeariaId(id);

            List<TipoDoCorte> entities = tipoDoCorteIds.stream().map(tipoDoCorteRepository::getReferenceById)
                    .toList();

            Page<TipoDoCorte> pageEntities = new PageImpl<>(entities, pageable, entities.size());

            return pageEntities.map(TipoDoCorteDTO::new);
        }catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Recurso não encontrado");
        }
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public void excluir (Long idBarbearia, Long idTipoDoCorte) {
        if (!tipoDoCorteRepository.existsById(idTipoDoCorte) || !barbeariaRepository.existsById(idBarbearia)) {
            throw new ResourceNotFoundException("Recurso não encontrado");
        }

        try {
            barbeariaRepository.removerAssociacaoBarbeariaTipoDoCorte(idBarbearia, idTipoDoCorte);
            tipoDoCorteRepository.deleteById(idTipoDoCorte);
        }catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Falha de integridade referencial");
        }
    }
}
