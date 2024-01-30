package com.BarbershopConnect.BarbershopConnect.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
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

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "senha", nullable = false)
    private String senha;

    @Embedded
    private Endereco endereco;

    @Column(name = "contato", nullable = false)
    private String contato;

    @OneToMany(mappedBy = "cliente")
    private Set<Avaliacao> avaliacoes = new HashSet<>();

    @OneToOne(mappedBy = "cliente", cascade = CascadeType.ALL)
    private Agendamento agendamento;
}
