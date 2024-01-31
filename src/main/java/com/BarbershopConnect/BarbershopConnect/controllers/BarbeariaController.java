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


    @PutMapping("/{idBarbearia}/barbeiro/{idBarbeiro}")
    public ResponseEntity<BarbeiroDTO> atualizarBarbeiro(@PathVariable Long idBarbearia, @Valid @RequestBody BarbeiroDTO barbeiroDTO, @PathVariable Long idBarbeiro) {
        barbeiroDTO = barbeariaService.atualizarBarbeiro(idBarbearia, barbeiroDTO, idBarbeiro);

        return ResponseEntity.ok(barbeiroDTO);
    }
}
