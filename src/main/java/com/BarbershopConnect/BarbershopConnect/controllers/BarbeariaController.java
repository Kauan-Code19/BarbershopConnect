package com.BarbershopConnect.BarbershopConnect.controllers;

import com.BarbershopConnect.BarbershopConnect.dto.BarbeariaDTO;
import com.BarbershopConnect.BarbershopConnect.dto.BarbeariaResponseDTO;
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
    public ResponseEntity<BarbeariaResponseDTO> cadastro(@Valid @RequestBody BarbeariaDTO barbeariaDTO) {
        BarbeariaResponseDTO barbeariaResponseDTO = barbeariaService.cadastrar(barbeariaDTO);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("{/{id}")
                .buildAndExpand(barbeariaResponseDTO.getId()).toUri();

        return ResponseEntity.created(uri).body(barbeariaResponseDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BarbeariaResponseDTO> atualizacao (@PathVariable Long id, @Valid @RequestBody BarbeariaDTO barbeariaDTO) {
        BarbeariaResponseDTO barbeariaResponseDTO =  barbeariaService.atualizar(id, barbeariaDTO);

        return ResponseEntity.ok(barbeariaResponseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> exclusao (@PathVariable Long id) {
        barbeariaService.deletar(id);

        return ResponseEntity.noContent().build();
    }
}
