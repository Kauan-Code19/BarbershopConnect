package com.BarbershopConnect.BarbershopConnect.controllers;

import com.BarbershopConnect.BarbershopConnect.dto.ClienteDTO;
import com.BarbershopConnect.BarbershopConnect.dto.ClienteResponseDTO;
import com.BarbershopConnect.BarbershopConnect.services.ClienteService;
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
@RequestMapping(value = "/cliente")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @PostMapping
    public ResponseEntity<ClienteResponseDTO> cadastro(@Valid @RequestBody ClienteDTO clienteDTO) {
        ClienteResponseDTO clienteResponseDTO = clienteService.cadastrar(clienteDTO);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(clienteResponseDTO.getId()).toUri();

        return ResponseEntity.created(uri).body(clienteResponseDTO);
    }
}
