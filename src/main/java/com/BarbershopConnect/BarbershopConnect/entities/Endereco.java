package com.BarbershopConnect.BarbershopConnect.entities;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Embeddable
public class Endereco {
    @NotBlank(message = "O Campo não pode estar em branco.")
    private String logradouro;

    @Size(min = 1, message = "O Campo deve ter no minimo 1 caractere.")
    private String numero;

    @NotBlank(message = "O Campo não pode estar em branco.")
    @Size(min = 2, max = 43, message = "O Campo deve ter no minimo 2 a 43 caracteres")
    private String cidade;

    @NotBlank(message = "O Campo não pode estar em branco.")
    @Size(min = 5, max = 13, message = "O Campo deve ter no minimo 5 a 13 caracteres")
    private String estado;

    @NotBlank(message = "O Campo não pode estar em branco.")
    @Pattern(regexp = "\\d{5}-\\d{3}", message = "O CEP deve estar no formato 12345-678.")
    private String cep;
}
