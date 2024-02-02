package com.BarbershopConnect.BarbershopConnect.dto;

import com.BarbershopConnect.BarbershopConnect.entities.Barbeiro;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class BarbeiroDTO {
    private Long id;

    @Size(min = 3, max = 80, message = "O Campo precisa ter de 3 a 80 caracteres")
    @NotBlank(message = "Campo Requerido")
    private String nome;

    @Size(min = 10, max = 200, message = "A descrição deve ter entre 10 e 200 caracteres.")
    private String descricao;

    @Pattern(regexp = "^\\(?(\\d{2})\\)?[-.\\s]?\\d{4,5}[-.\\s]?\\d{4}$", message = "Formato de número de telefone inválido.")
    private String contato;

    private BarbeariaResponseDTO barbearia;

    public BarbeiroDTO(Barbeiro entity) {
        id = entity.getId();
        nome = entity.getNome();
        descricao = entity.getDescricao();
        contato = entity.getContato();
        barbearia = new BarbeariaResponseDTO(entity.getBarbearia());
    }
}