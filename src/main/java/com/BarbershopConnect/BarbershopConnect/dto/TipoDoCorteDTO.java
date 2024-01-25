package com.BarbershopConnect.BarbershopConnect.dto;

import com.BarbershopConnect.BarbershopConnect.entities.TipoDoCorte;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class TipoDoCorteDTO {
    private Long id;

    @Size(min = 3, max = 80, message = "O Campo precisa ter de 3 a 80 caracteres")
    @NotBlank(message = "Campo Requerido")
    private String nome;

    @Size(min = 10, max = 200, message = "A descrição deve ter entre 10 e 200 caracteres.")
    private String descricao;

    @NotNull(message = "O preço não pode ser nulo.")
    @Min(value = 0, message = "O preço deve ser maior ou igual a zero.")
    private Double preco;

    public TipoDoCorteDTO (TipoDoCorte entity) {
        id = entity.getId();
        nome = entity.getNome();
        descricao = entity.getDescricao();
        preco = entity.getPreco();
    }
}
