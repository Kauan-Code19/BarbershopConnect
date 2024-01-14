package com.BarbershopConnect.BarbershopConnect.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "barbeiro")
public class Barbeiro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "contato", nullable = false)
    private String contato;

    @OneToMany(mappedBy = "barbeiro")
    private Set<Avaliacao> avaliacoes;

    @ManyToOne
    @JoinColumn(name = "barbeariaId")
    private Barbearia barbearia;
}
