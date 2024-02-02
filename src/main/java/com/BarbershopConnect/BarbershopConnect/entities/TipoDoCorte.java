package com.BarbershopConnect.BarbershopConnect.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
@Entity
@Table(name = "tipoDoCorte")
public class TipoDoCorte {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "preco", nullable = false)
    private Double preco;

    @OneToMany(mappedBy = "tipoDoCorte")
    private Set<Agendamento> agendamentos = new HashSet<>();

    @ManyToMany(mappedBy = "tiposDoCorte")
    private Set<Barbearia> barbearias = new HashSet<>();
}
