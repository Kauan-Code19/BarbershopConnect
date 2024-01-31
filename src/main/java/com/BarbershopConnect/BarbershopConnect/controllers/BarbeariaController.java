package com.BarbershopConnect.BarbershopConnect.controllers;

import com.BarbershopConnect.BarbershopConnect.dto.TipoDoCorteDTO;
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


    @PostMapping("/{id}/tipos-de-corte")
    public ResponseEntity<Set<TipoDoCorteDTO>> definirTiposDoCorte(@PathVariable Long id, @Valid @RequestBody Set<TipoDoCorteDTO> tipoDoCorteDTOS) {
         Set<TipoDoCorteDTO> tipoDoCorteDTO = barbeariaService.adicionarTiposDoCorte(id, tipoDoCorteDTOS);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("{/{id}")
                .buildAndExpand(id).toUri();

        return ResponseEntity.created(uri).body(tipoDoCorteDTO);
    }

    @PutMapping("/{id}/tipos-de-corte")
    public ResponseEntity<Set<TipoDoCorteDTO>> atualizarTiposDoCorte(@Valid @RequestBody Set<TipoDoCorteDTO> tipoDoCorteDTOS) {
        Set<TipoDoCorteDTO> tipoDoCorteDTO = barbeariaService.atualizarTiposDoCorte(tipoDoCorteDTOS);

        return ResponseEntity.ok().body(tipoDoCorteDTO);
    }

    @GetMapping("/{id}/tipos-de-corte")
    public ResponseEntity<Page<TipoDoCorteDTO>> buscarTiposDoCorte (@PathVariable Long id, Pageable pageable) {
        Page<TipoDoCorteDTO> tipoDoCorteDTO = barbeariaService.buscarTiposDoCorte(id, pageable);

        return ResponseEntity.ok().body(tipoDoCorteDTO);
    }

    @DeleteMapping("/{idBarbearia}/tipos-de-corte/{idTipoDoCorte}")
    public ResponseEntity<Void> exclusao (@PathVariable Long idBarbearia, @PathVariable Long idTipoDoCorte) {
        barbeariaService.excluir(idBarbearia, idTipoDoCorte);

        return ResponseEntity.noContent().build();
    }
}
