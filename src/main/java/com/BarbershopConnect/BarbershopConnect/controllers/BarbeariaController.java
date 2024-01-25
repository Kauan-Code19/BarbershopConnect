package com.BarbershopConnect.BarbershopConnect.controllers;

import com.BarbershopConnect.BarbershopConnect.dto.BarbeariaDTO;
import com.BarbershopConnect.BarbershopConnect.dto.TipoDoCorteDTO;
import com.BarbershopConnect.BarbershopConnect.dto.TipoDoCorteIdDTO;
import com.BarbershopConnect.BarbershopConnect.services.BarbeariaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Set;

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

    @PostMapping("/{id}/tiposDoCorte")
    public ResponseEntity<Set<TipoDoCorteDTO>> definirTiposDoCorte(@PathVariable Long id, @Valid @RequestBody Set<TipoDoCorteDTO> tipoDoCorteDTOS) {
         Set<TipoDoCorteDTO> tipoDoCorteDTO = barbeariaService.adicionarTiposDoCorte(id, tipoDoCorteDTOS);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("{/{id}")
                .buildAndExpand(id).toUri();

        return ResponseEntity.created(uri).body(tipoDoCorteDTO);
    }

    @PutMapping("/{id}/tiposDoCorte")
    public ResponseEntity<Set<TipoDoCorteDTO>> atualizarTiposDoCorte(@Valid @RequestBody Set<TipoDoCorteDTO> tipoDoCorteDTOS) {
        Set<TipoDoCorteDTO> tipoDoCorteDTO = barbeariaService.atualizarTiposDoCorte(tipoDoCorteDTOS);

        return ResponseEntity.ok().body(tipoDoCorteDTO);
    }

    @GetMapping("/{id}/tiposDoCorte")
    public ResponseEntity<Set<TipoDoCorteDTO>> buscarTiposDoCorte (@PathVariable Long id) {
        Set<TipoDoCorteDTO> tipoDoCorteDTO = barbeariaService.buscarTiposDoCorte(id);

        return ResponseEntity.ok().body(tipoDoCorteDTO);
    }

    @GetMapping("/tiposDoCorte")
    public ResponseEntity<Page<TipoDoCorteDTO>> listarTiposDoCorte (Pageable pageable) {
        Page<TipoDoCorteDTO> tipoDoCorteDTO = barbeariaService.listarTiposDoCorte(pageable);

        return ResponseEntity.ok().body(tipoDoCorteDTO);
    }

    @DeleteMapping("/{id}/tiposDoCorte")
    public ResponseEntity<Void> exclusao (@PathVariable Long id, @RequestBody TipoDoCorteIdDTO tipoDoCorteIdDTO) {
        barbeariaService.excluir(id, tipoDoCorteIdDTO);

        return ResponseEntity.noContent().build();
    }
}
