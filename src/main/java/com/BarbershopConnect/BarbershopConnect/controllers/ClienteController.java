package com.BarbershopConnect.BarbershopConnect.controllers;

import com.BarbershopConnect.BarbershopConnect.dto.ClienteDTO;
import com.BarbershopConnect.BarbershopConnect.services.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/cliente")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @PostMapping
    public ClienteDTO cadastro(@RequestBody ClienteDTO clienteDTO) {
        return clienteService.cadastrar(clienteDTO);
    }
}
