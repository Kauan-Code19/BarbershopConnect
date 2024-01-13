package com.BarbershopConnect.BarbershopConnect.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "cliente")
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Embedded
    private Endereco endereco;

    @Column(name = "contato", nullable = false)
    private String contato;

    @OneToMany(mappedBy = "cliente")
    private Set<Avaliacao> avaliacoes;

    @OneToOne(mappedBy = "cliente", cascade = CascadeType.ALL)
    private Agendamento agendamento;
}
