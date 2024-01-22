package com.BarbershopConnect.BarbershopConnect.controllers;

import com.BarbershopConnect.BarbershopConnect.dto.BarbeariaDTO;
import com.BarbershopConnect.BarbershopConnect.dto.HorarioDeFuncionamentoDTO;
import com.BarbershopConnect.BarbershopConnect.services.BarbeariaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value = "/barbearia")
public class BarbeariaController {

    @Autowired
    private BarbeariaService barbeariaService;

    @PostMapping
    public ResponseEntity<BarbeariaDTO> cadastro(@Valid @RequestBody BarbeariaDTO barbeariaDTO) {
        barbeariaDTO = barbeariaService.cadastrar(barbeariaDTO);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("{/{id}")
                .buildAndExpand(barbeariaDTO.getId()).toUri();

        return ResponseEntity.created(uri).body(barbeariaDTO);
    }

    @PostMapping("/{id}/horario")
    public ResponseEntity<HorarioDeFuncionamentoDTO> horarios (@PathVariable Long id, @Valid @RequestBody HorarioDeFuncionamentoDTO horarioDeFuncionamentoDTO) {
        horarioDeFuncionamentoDTO = barbeariaService.definirHorarios(id, horarioDeFuncionamentoDTO);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("{/id}")
                .buildAndExpand(id).toUri();

        return ResponseEntity.created(uri).body(horarioDeFuncionamentoDTO);
    }

    @PutMapping("/{id}/horario")
    public ResponseEntity<HorarioDeFuncionamentoDTO> atualizacaoHorarios (@PathVariable Long id, @Valid @RequestBody HorarioDeFuncionamentoDTO horarioDeFuncionamentoDTO) {
        horarioDeFuncionamentoDTO = barbeariaService.atualizarHorarios(id, horarioDeFuncionamentoDTO);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("{/id}")
                .buildAndExpand(id).toUri();

        return ResponseEntity.ok(horarioDeFuncionamentoDTO);
    }
}
