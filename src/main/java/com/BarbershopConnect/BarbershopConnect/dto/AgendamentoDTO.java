package com.BarbershopConnect.BarbershopConnect.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;
import java.time.LocalTime;

@NoArgsConstructor
@Getter
public class AgendamentoDTO {
    private Long id;

    private Long idBarbearia;

    private Long idTipoDoCorte;

    @NotNull(message = "O dia da semana não pode ser nulo")
    private DayOfWeek diaDaSemana;

    @NotNull(message = "O horário escolhido não pode ser nulo")
    private LocalTime horarioEscolhido;
}
