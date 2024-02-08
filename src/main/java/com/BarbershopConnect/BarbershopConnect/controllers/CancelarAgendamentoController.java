package com.BarbershopConnect.BarbershopConnect.controllers;

import com.BarbershopConnect.BarbershopConnect.entities.Status;
import com.BarbershopConnect.BarbershopConnect.services.AgendamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/cancelar-agendamento")
public class CancelarAgendamentoController {

    @Autowired
    private AgendamentoService agendamentoService;

    @GetMapping("/{id}")
    @ResponseBody
    public Status cancelarAgendamento(@PathVariable Long id) {
        Boolean b = false;
        Status status = agendamentoService.cancelarAgendamento(id, b);

        return status;
    }
}
