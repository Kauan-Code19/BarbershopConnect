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
@Table(name = "barbearia")
public class Barbearia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "email")
    private String email;

    @Embedded
    private Endereco endereco;

    @Column(name = "contato")
    private String contato;

//    private Set<Barbeiro> barbeiros;

//    private Set<TipoDoCorte> tiposDoCorte;

//    private HorarioDeFuncionamento horariosDeFuncionamento;

    @OneToMany(mappedBy = "barbearia")
    private Set<Agendamento> agendamentos;

    @OneToMany(mappedBy = "barbearia")
    private Set<Avaliacao> avaliacoes;
}
