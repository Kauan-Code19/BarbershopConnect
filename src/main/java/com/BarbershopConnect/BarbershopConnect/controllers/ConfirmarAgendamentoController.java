package com.BarbershopConnect.BarbershopConnect.controllers;

import com.BarbershopConnect.BarbershopConnect.entities.Status;
import com.BarbershopConnect.BarbershopConnect.services.AgendamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/confirmar-agendamento")
public class ConfirmarAgendamentoController {

    @Autowired
    private AgendamentoService agendamentoService;

    @GetMapping("/{id}")
    @ResponseBody
    public Status confirmarAgendamento (@PathVariable Long id) {
        Boolean b = true;
        Status status = agendamentoService.confirmarAgendamento(id, b);

        return status;
    }
}
