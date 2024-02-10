package com.BarbershopConnect.BarbershopConnect.dto;

import com.BarbershopConnect.BarbershopConnect.entities.Agendamento;
import com.BarbershopConnect.BarbershopConnect.entities.Status;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
public class AgendamentoResponseDTO {
    private Long id;

    private ClienteResponseDTO cliente;

    private BarbeariaResponseDTO barbearia;

    private TipoDoCorteDTO tipoDoCorte;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime localDateTime;

    private Status status;

    public AgendamentoResponseDTO (Agendamento entity) {
        id = entity.getId();
        cliente = new ClienteResponseDTO(entity.getCliente());
        barbearia = new BarbeariaResponseDTO(entity.getBarbearia());
        tipoDoCorte = new TipoDoCorteDTO(entity.getTipoDoCorte());
        localDateTime = entity.getDataHora();
        status = entity.getStatus();
    }
}
