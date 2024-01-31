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


    @GetMapping("/{idBarbearia}/barbeiro/{idBarbeiro}")
    public ResponseEntity<BarbeiroDTO> buscarBarbeiro (@PathVariable Long idBarbearia, @PathVariable Long idBarbeiro) {
        BarbeiroDTO barbeiroDTO = barbeariaService.buscarBarbeiro(idBarbearia, idBarbeiro);

        return ResponseEntity.ok().body(barbeiroDTO);
    }

    @GetMapping("/{id}/barbeiro")
    public ResponseEntity<Page<BarbeiroDTO>> listarBarbeiro (@PathVariable Long id, Pageable pageable) {
        Page<BarbeiroDTO> barbeiroDTOS = barbeariaService.listarBarbeiros(id, pageable);

        return ResponseEntity.ok().body(barbeiroDTOS);
    }
}
