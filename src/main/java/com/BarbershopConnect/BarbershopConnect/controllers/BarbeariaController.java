package com.BarbershopConnect.BarbershopConnect.controllers;

import com.BarbershopConnect.BarbershopConnect.dto.*;
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
    public ResponseEntity<BarbeariaResponseDTO> cadastro(@Valid @RequestBody BarbeariaDTO barbeariaDTO) {
        BarbeariaResponseDTO barbeariaResponseDTO = barbeariaService.cadastrar(barbeariaDTO);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("{/{id}")
                .buildAndExpand(barbeariaResponseDTO.getId()).toUri();

        return ResponseEntity.created(uri).body(barbeariaResponseDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BarbeariaResponseDTO> atualizacao (@PathVariable Long id, @Valid @RequestBody BarbeariaDTO barbeariaDTO) {
        BarbeariaResponseDTO barbeariaResponseDTO =  barbeariaService.atualizar(id, barbeariaDTO);

        return ResponseEntity.ok(barbeariaResponseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> exclusao (@PathVariable Long id) {
        barbeariaService.deletar(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<BarbeariaResponseDTO> buscar (@PathVariable Long id) {
        BarbeariaResponseDTO barbeariaResponseDTO = barbeariaService.buscar(id);

        return ResponseEntity.ok().body(barbeariaResponseDTO);
    }

    @GetMapping
    public  ResponseEntity<Page<BarbeariaResponseDTO>> listar (Pageable pageable) {
        Page<BarbeariaResponseDTO> barbeariaResponseDTOS = barbeariaService.listar(pageable);

        return ResponseEntity.ok().body(barbeariaResponseDTOS);
    }

    @PostMapping("/{idBarbearia}/barbeiro")
    public ResponseEntity<BarbeiroDTO> cadastroBarbeiro(@PathVariable Long idBarbearia, @Valid @RequestBody BarbeiroDTO barbeiroDTO) {
        barbeiroDTO = barbeariaService.cadastrarBarbeiro(idBarbearia, barbeiroDTO);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("{/id}")
                .buildAndExpand(barbeiroDTO.getId()).toUri();

        return ResponseEntity.created(uri).body(barbeiroDTO);
    }

    @PutMapping("/{idBarbearia}/barbeiro/{idBarbeiro}")
    public ResponseEntity<BarbeiroDTO> atualizarBarbeiro(@PathVariable Long idBarbearia, @Valid @RequestBody BarbeiroDTO barbeiroDTO, @PathVariable Long idBarbeiro) {
        barbeiroDTO = barbeariaService.atualizarBarbeiro(idBarbearia, barbeiroDTO, idBarbeiro);

        return ResponseEntity.ok(barbeiroDTO);
    }

    @DeleteMapping("/{idBarbearia}/barbeiro/{idBarbeiro}")
    public ResponseEntity<Void> exclusaoBarbeiro(@PathVariable Long idBarbeiro) {
        barbeariaService.deletarBarbeiro(idBarbeiro);

        return ResponseEntity.noContent().build();
    }

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

    @PostMapping("/{id}/horario")
    public ResponseEntity<HorarioDeFuncionamentoDTO> horarios (@PathVariable Long id, @Valid @RequestBody HorarioDeFuncionamentoDTO horarioDeFuncionamentoDTO) {
        horarioDeFuncionamentoDTO = barbeariaService.definirHorarios(id, horarioDeFuncionamentoDTO);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("{/id}")
                .buildAndExpand(id).toUri();

        return ResponseEntity.created(uri).body(horarioDeFuncionamentoDTO);
    }

    @PutMapping("/{id}/horario")
    public ResponseEntity<HorarioDeFuncionamentoDTO> atualizacaoHorarios (@PathVariable Long id, @Valid @RequestBody HorarioDeFuncionamentoDTO horarioDeFuncionamentoDTO) {
        horarioDeFuncionamentoDTO = barbeariaService.atualizarHorarios(id, horarioDeFuncionamentoDTO);

        return ResponseEntity.ok(horarioDeFuncionamentoDTO);
    }

    @GetMapping("/{id}/horario")
    public ResponseEntity<HorarioDeFuncionamentoDTO> buscarHorarioDeFuncionamento (@PathVariable Long id) {
        HorarioDeFuncionamentoDTO horarioDeFuncionamentoDTO = barbeariaService.buscarHorarioDeFuncionamento(id);

        return ResponseEntity.ok().body(horarioDeFuncionamentoDTO);
    }

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