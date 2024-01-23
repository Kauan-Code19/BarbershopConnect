package com.BarbershopConnect.BarbershopConnect.controllers;

import com.BarbershopConnect.BarbershopConnect.dto.BarbeariaDTO;
import com.BarbershopConnect.BarbershopConnect.dto.BarbeiroDTO;
import com.BarbershopConnect.BarbershopConnect.services.BarbeariaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @PostMapping("/barbeiro")
    public ResponseEntity<BarbeiroDTO> cadastroBarbeiro(@Valid @RequestBody BarbeiroDTO barbeiroDTO) {
        barbeiroDTO = barbeariaService.cadastrarBarbeiro(barbeiroDTO);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("{/id}")
                .buildAndExpand(barbeiroDTO.getId()).toUri();

        return ResponseEntity.created(uri).body(barbeiroDTO);
    }

    @PutMapping("/barbeiro/{id}")
    public ResponseEntity<BarbeiroDTO> atualizarBarbeiro(@PathVariable Long id, @Valid @RequestBody BarbeiroDTO barbeiroDTO) {
        barbeiroDTO = barbeariaService.atualizarBarbeiro(id, barbeiroDTO);

        return ResponseEntity.ok(barbeiroDTO);
    }

    @DeleteMapping("/barbeiro/{id}")
    public ResponseEntity<Void> exclusaoBarbeiro(@PathVariable Long id) {
        barbeariaService.deletarBarbeiro(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/barbeiro/{id}")
    public ResponseEntity<BarbeiroDTO> buscarBarbeiro (@PathVariable Long id) {
        BarbeiroDTO barbeiroDTO = barbeariaService.buscarBarbeiro(id);

        return ResponseEntity.ok().body(barbeiroDTO);
    }

    @GetMapping("/barbeiro")
    public ResponseEntity<Page<BarbeiroDTO>> listarBarbeiro (Pageable pageable) {
        Page<BarbeiroDTO> barbeiroDTOS = barbeariaService.listarBarbeiros(pageable);

        return ResponseEntity.ok().body(barbeiroDTOS);
    }
}
