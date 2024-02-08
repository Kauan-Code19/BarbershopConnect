package com.BarbershopConnect.BarbershopConnect.controllers;

import com.BarbershopConnect.BarbershopConnect.dto.AgendamentoDTO;
import com.BarbershopConnect.BarbershopConnect.dto.AgendamentoResponseDTO;
import com.BarbershopConnect.BarbershopConnect.dto.ClienteDTO;
import com.BarbershopConnect.BarbershopConnect.dto.ClienteResponseDTO;
import com.BarbershopConnect.BarbershopConnect.services.AgendamentoService;
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
    @Autowired
    private AgendamentoService agendamentoService;

    @PostMapping
    public ResponseEntity<ClienteResponseDTO> cadastro(@Valid @RequestBody ClienteDTO clienteDTO) {
        ClienteResponseDTO clienteResponseDTO = clienteService.cadastrar(clienteDTO);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(clienteResponseDTO.getId()).toUri();

        return ResponseEntity.created(uri).body(clienteResponseDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClienteResponseDTO> atualizacao (@PathVariable Long id, @Valid @RequestBody ClienteDTO clienteDTO) {
        ClienteResponseDTO clienteResponseDTO = clienteService.atualizar(id, clienteDTO);

        return ResponseEntity.ok(clienteResponseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> exclusao (@PathVariable Long id) {
        clienteService.deletar(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteResponseDTO> buscar (@PathVariable Long id) {
        ClienteResponseDTO clienteResponseDTO = clienteService.buscar(id);

        return ResponseEntity.ok().body(clienteResponseDTO);
    }

    @GetMapping
    public ResponseEntity<Page<ClienteResponseDTO>> listar (Pageable pageable) {
        Page<ClienteResponseDTO> clienteResponseDTOS = clienteService.listar(pageable);

        return ResponseEntity.ok().body(clienteResponseDTOS);
    }

    @PostMapping("/{id}/agendamento")
    public ResponseEntity<AgendamentoResponseDTO> agendar (@PathVariable Long id, @Valid @RequestBody AgendamentoDTO agendamentoDTO) {
        AgendamentoResponseDTO agendamentoResponseDTO = agendamentoService.agendar(id, agendamentoDTO);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(agendamentoDTO.getId()).toUri();

        return ResponseEntity.created(uri).body(agendamentoResponseDTO);
    }

    @PutMapping("/{id}/agendamento")
    public ResponseEntity<AgendamentoResponseDTO> atualizarAgendamento (@PathVariable Long id, @Valid @RequestBody AgendamentoDTO agendamentoDTO) {
        AgendamentoResponseDTO agendamentoResponseDTO = agendamentoService.atualizarAgendamento(id, agendamentoDTO);

        return ResponseEntity.ok(agendamentoResponseDTO);
    }

    @GetMapping("/{id}/agendamento")
    public ResponseEntity<AgendamentoResponseDTO> buscarAgendamento (@PathVariable Long id) {
        AgendamentoResponseDTO agendamentoResponseDTO = agendamentoService.buscarAgendamento(id);

        return ResponseEntity.ok().body(agendamentoResponseDTO);
    }
}