package com.BarbershopConnect.BarbershopConnect.dto;

import com.BarbershopConnect.BarbershopConnect.entities.Barbearia;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

@NoArgsConstructor
@Getter
public class HorarioDeFuncionamentoDTO {
    @NotNull
    @Min(value = 1, message = "O ID da barbearia deve ser maior que zero.")
    private Long barbeariaId;

    private Map<DayOfWeek, List<LocalTime>> horariosDeFuncionamento;

    public HorarioDeFuncionamentoDTO (Barbearia entity) {
        barbeariaId = entity.getId();
        horariosDeFuncionamento = entity.getHorariosDeFuncionamento();
    }
}
