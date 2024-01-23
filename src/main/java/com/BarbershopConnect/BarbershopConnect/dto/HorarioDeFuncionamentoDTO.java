package com.BarbershopConnect.BarbershopConnect.dto;

import com.BarbershopConnect.BarbershopConnect.entities.Barbearia;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@NoArgsConstructor
@Getter
public class HorarioDeFuncionamentoDTO {

    private Map<DayOfWeek, List<LocalTime>> horariosDeFuncionamento = new HashMap<>();

    public HorarioDeFuncionamentoDTO (Barbearia entity) {
        horariosDeFuncionamento = entity.getHorariosDeFuncionamento();
    }
}
