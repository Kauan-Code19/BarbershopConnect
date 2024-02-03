package com.BarbershopConnect.BarbershopConnect.services;

import com.BarbershopConnect.BarbershopConnect.dto.*;
import com.BarbershopConnect.BarbershopConnect.entities.Barbearia;
import com.BarbershopConnect.BarbershopConnect.entities.Barbeiro;
import com.BarbershopConnect.BarbershopConnect.entities.TipoDoCorte;
import com.BarbershopConnect.BarbershopConnect.repositories.BarbeariaRepository;
import com.BarbershopConnect.BarbershopConnect.repositories.BarbeiroRepository;
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
    private final BarbeiroRepository barbeiroRepository;
    private final TipoDoCorteRepository tipoDoCorteRepository;

    @Autowired
    public BarbeariaService(BarbeariaRepository barbeariaRepository, BarbeiroRepository barbeiroRepository, TipoDoCorteRepository tipoDoCorteRepository) {
        this.barbeariaRepository = barbeariaRepository;
        this.barbeiroRepository = barbeiroRepository;
        this.tipoDoCorteRepository = tipoDoCorteRepository;
    }

    @Transactional
    public BarbeariaResponseDTO cadastrar (BarbeariaDTO barbeariaDTO) {
        try {
            Barbearia entity = new Barbearia();

            entity.setNome(barbeariaDTO.getNome());
            entity.setEmail(barbeariaDTO.getEmail());
            entity.setSenha(barbeariaDTO.getSenha());
            entity.setEndereco(barbeariaDTO.getEndereco());
            entity.setContato(barbeariaDTO.getContato());

            entity = barbeariaRepository.save(entity);

            return new BarbeariaResponseDTO(entity);
        }catch (DataIntegrityViolationException e) {
                throw new DatabaseException("Falha de integridade referencial");
        }
    }

    @Transactional
    public BarbeariaResponseDTO atualizar (Long id, BarbeariaDTO barbeariaDTO) {
        try {
            Barbearia entity = barbeariaRepository.getReferenceById(id);

            entity.setNome(barbeariaDTO.getNome());
            entity.setEmail(barbeariaDTO.getEmail());
            entity.setSenha(barbeariaDTO.getSenha());
            entity.setEndereco(barbeariaDTO.getEndereco());
            entity.setContato(barbeariaDTO.getContato());

            entity = barbeariaRepository.save(entity);

            return new BarbeariaResponseDTO(entity);
        }catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Recurso não Encontrado");
        }
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public void deletar (Long id) {
        if (!barbeariaRepository.existsById(id)) {
            throw new ResourceNotFoundException("Recurso não encontrado");
        }

        try {
            barbeariaRepository.deleteById(id);
        }catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Falha de integridade referencial");
        }
    }

    @Transactional(readOnly = true)
    public BarbeariaResponseDTO buscar (Long id) {
        try {
            Barbearia barbearia = barbeariaRepository.getReferenceById(id);

            return new BarbeariaResponseDTO(barbearia);
        }catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Recurso não encontrado");
        }
    }

    @Transactional(readOnly = true)
    public Page<BarbeariaResponseDTO> listar (Pageable pageable) {
        Page<Barbearia> barbearia = barbeariaRepository.findAll(pageable);

        return barbearia.map(BarbeariaResponseDTO::new);
    }

    @Transactional
    public BarbeiroDTO cadastrarBarbeiro (Long id, BarbeiroDTO barbeiroDTO) {
        if (!barbeariaRepository.existsById(id)) {
            throw new ResourceNotFoundException("Recurso não encontrado");
        }

        Barbeiro entity = new Barbeiro();
        Barbearia barbearia = barbeariaRepository.getReferenceById(id);

        entity.setNome(barbeiroDTO.getNome());
        entity.setDescricao(barbeiroDTO.getDescricao());
        entity.setContato(barbeiroDTO.getContato());
        entity.setBarbearia(barbearia);

        entity = barbeiroRepository.save(entity);

        return  new BarbeiroDTO(entity);
    }

    @Transactional
    public BarbeiroDTO atualizarBarbeiro (Long idBarbearia, BarbeiroDTO barbeiroDTO, Long idBarbeiro) {
        try {
            Barbeiro entity = barbeiroRepository.getReferenceById(idBarbeiro);
            Barbearia barbearia = barbeariaRepository.getReferenceById(idBarbearia);

            if (!entity.getBarbearia().getId().equals(idBarbearia)) {
                throw new ResourceNotFoundException("Barbeiro não encontrado");
            }

            entity = barbeiroRepository.save(entity);

            return new BarbeiroDTO(entity);
        }catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Recurso não encontrado");
        }
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public void deletarBarbeiro (Long id) {
        if (!barbeiroRepository.existsById(id)) {
            throw new ResourceNotFoundException("Recurso não encontrado");
        }

        try {
            barbeiroRepository.deleteById(id);
        }catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Falha de integridade referencial");
        }
    }

    @Transactional(readOnly = true)
    public BarbeiroDTO buscarBarbeiro (Long idBarbearia, Long idBarbeiro) {
        try {
            Barbeiro barbeiro = barbeiroRepository.getReferenceById(idBarbeiro);

            if (!barbeiro.getBarbearia().getId().equals(idBarbearia)) {
                throw new ResourceNotFoundException("O barbeiro não pertence a essa barbearia");
            }

            return new BarbeiroDTO(barbeiro);
        }catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Recurso não encontrado");
        }
    }

    @Transactional(readOnly = true)
    public Page<BarbeiroDTO> listarBarbeiros (Long id, Pageable pageable) {
        try {
            Page<Barbeiro> barbeiros = barbeiroRepository.findByBarbeariaId(id, pageable);

            return barbeiros.map(BarbeiroDTO::new);
        }catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("Recurso não encontrado");
        }
    }

    @Transactional
    public HorarioDeFuncionamentoDTO definirHorarios (Long id, HorarioDeFuncionamentoDTO horarioDeFuncionamentoDTO) {
        try {
            Barbearia entity = barbeariaRepository.getReferenceById(id);

            entity.setHorariosDeFuncionamento(horarioDeFuncionamentoDTO.getHorariosDeFuncionamento());

            entity = barbeariaRepository.save(entity);

            return new HorarioDeFuncionamentoDTO(entity);
        }catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Recurso não encontrado");
        }
    }

    @Transactional
    public HorarioDeFuncionamentoDTO atualizarHorarios (Long id, HorarioDeFuncionamentoDTO horarioDeFuncionamentoDTO) {
        try {
            Barbearia entity = barbeariaRepository.getReferenceById(id);

            entity.setHorariosDeFuncionamento(horarioDeFuncionamentoDTO.getHorariosDeFuncionamento());

            entity = barbeariaRepository.save(entity);

            return new HorarioDeFuncionamentoDTO(entity);
        }catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Recurso não encontrado");
        }
    }

    @Transactional(readOnly = true)
    public HorarioDeFuncionamentoDTO buscarHorarioDeFuncionamento (Long id) {
        try {
            Barbearia barbearia = barbeariaRepository.getReferenceById(id);

            return new HorarioDeFuncionamentoDTO(barbearia);
        }catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Recurso não encontrado");
        }
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