package com.BarbershopConnect.BarbershopConnect.controllers;

import com.BarbershopConnect.BarbershopConnect.dto.BarbeariaDTO;
import com.BarbershopConnect.BarbershopConnect.dto.BarbeiroDTO;
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

    @PostMapping("/{id}/barbeiro")
    public ResponseEntity<BarbeiroDTO> cadastroBarbeiro(@PathVariable Long id, @Valid @RequestBody BarbeiroDTO barbeiroDTO) {
        barbeiroDTO = barbeariaService.cadastrarBarbeiro(id, barbeiroDTO);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("{/id}")
                .buildAndExpand(barbeiroDTO.getId()).toUri();

        return ResponseEntity.created(uri).body(barbeiroDTO);
    }
}
