package com.BarbershopConnect.BarbershopConnect.dto;

import com.BarbershopConnect.BarbershopConnect.entities.Barbearia;
import com.BarbershopConnect.BarbershopConnect.entities.Endereco;
import jakarta.persistence.Embedded;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class BarbeariaDTO {
    private Long id;

    @Size(min = 3, max = 80, message = "O Campo precisa ter de 3 a 80 caracteres")
    @NotBlank(message = "Campo Requerido")
    private String nome;

    @Email
    private String email;

    @Valid
    @Embedded
    private Endereco endereco;

    @Pattern(regexp = "^\\(?(\\d{2})\\)?[-.\\s]?\\d{4,5}[-.\\s]?\\d{4}$", message = "Formato de número de telefone inválido.")
    private String contato;

    public BarbeariaDTO(Barbearia entity) {
        id = entity.getId();
        nome = entity.getNome();
        email = entity.getEmail();
        endereco = entity.getEndereco();
        contato = entity.getContato();
    }
}
