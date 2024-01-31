package com.BarbershopConnect.BarbershopConnect.services;

import com.BarbershopConnect.BarbershopConnect.dto.HorarioDeFuncionamentoDTO;
import com.BarbershopConnect.BarbershopConnect.entities.Barbearia;
import com.BarbershopConnect.BarbershopConnect.repositories.BarbeariaRepository;
import com.BarbershopConnect.BarbershopConnect.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
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
}
