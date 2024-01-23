package com.BarbershopConnect.BarbershopConnect.controllers;

import com.BarbershopConnect.BarbershopConnect.dto.ClienteDTO;
import com.BarbershopConnect.BarbershopConnect.services.ClienteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value = "/cliente")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @PostMapping
    public ResponseEntity<ClienteDTO> cadastro(@Valid @RequestBody ClienteDTO clienteDTO) {
        clienteDTO = clienteService.cadastrar(clienteDTO);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(clienteDTO.getId()).toUri();

        return ResponseEntity.created(uri).body(clienteDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClienteDTO> atualizacao (@PathVariable Long id, @Valid @RequestBody ClienteDTO clienteDTO) {
        clienteDTO = clienteService.atualizar(id, clienteDTO);

        return ResponseEntity.ok(clienteDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> exclusao (@PathVariable Long id) {
        clienteService.deletar(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteDTO> buscar (@PathVariable Long id) {
        ClienteDTO clienteDTO = clienteService.buscar(id);

        return ResponseEntity.ok().body(clienteDTO);
    }

    @GetMapping
    public ResponseEntity<Page<ClienteDTO>> listar (Pageable pageable) {
        Page<ClienteDTO> clienteDTOS = clienteService.listar(pageable);

        return ResponseEntity.ok().body(clienteDTOS);
    }
}
