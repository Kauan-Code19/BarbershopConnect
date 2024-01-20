package com.BarbershopConnect.BarbershopConnect.controllers;

import com.BarbershopConnect.BarbershopConnect.dto.BarbeariaDTO;
import com.BarbershopConnect.BarbershopConnect.services.BarbeariaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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

    @PostMapping("/tiposDoCorte")
    public ResponseEntity<BarbeariaDTO> definirTiposDoCorte(@Valid @RequestBody BarbeariaDTO barbeariaDTO) {
        barbeariaDTO = barbeariaService.adicionarTiposDoCorte(barbeariaDTO);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("{/{id}")
                .buildAndExpand(barbeariaDTO.getId()).toUri();

        return ResponseEntity.created(uri).body(barbeariaDTO);
    }
}
