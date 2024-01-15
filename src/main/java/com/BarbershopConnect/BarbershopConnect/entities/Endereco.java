package com.BarbershopConnect.BarbershopConnect.entities;

import jakarta.persistence.Embeddable;
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
    private String logradouro;
    private String numero;
    private String cidade;
    private String estado;
    private String cep;
}
