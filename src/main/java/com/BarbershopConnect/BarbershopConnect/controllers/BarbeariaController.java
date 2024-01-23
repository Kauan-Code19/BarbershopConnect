package com.BarbershopConnect.BarbershopConnect.controllers;

import com.BarbershopConnect.BarbershopConnect.dto.BarbeariaDTO;
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

    @PutMapping("/{id}")
    public ResponseEntity<BarbeariaDTO> atualizacao (@PathVariable Long id, @Valid @RequestBody BarbeariaDTO barbeariaDTO) {
        barbeariaDTO =  barbeariaService.atualizar(id, barbeariaDTO);

        return ResponseEntity.ok(barbeariaDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> exclusao (@PathVariable Long id) {
        barbeariaService.deletar(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<BarbeariaDTO> buscar (@PathVariable Long id) {
        BarbeariaDTO barbeariaDTO = barbeariaService.buscar(id);

        return ResponseEntity.ok().body(barbeariaDTO);
    }

    @GetMapping
    public  ResponseEntity<Page<BarbeariaDTO>> listar (Pageable pageable) {
        Page<BarbeariaDTO> barbeariaDTOS = barbeariaService.listar(pageable);

        return ResponseEntity.ok().body(barbeariaDTOS);
    }
}
