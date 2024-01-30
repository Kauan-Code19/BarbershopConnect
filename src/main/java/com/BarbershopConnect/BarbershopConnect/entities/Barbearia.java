package com.BarbershopConnect.BarbershopConnect.entities;


import jakarta.persistence.*;
import lombok.*;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
@Entity
@Table(name = "barbearia")
public class Barbearia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
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

    @OneToMany(mappedBy = "barbearia")
    private Set<Barbeiro> barbeiros;

    @ManyToMany
    @JoinTable(name = "barbeariaTipoDoCorte", joinColumns = @JoinColumn(name = "barbeariaId"),
            inverseJoinColumns = @JoinColumn(name = "TipoDoCorteId"))
    private Set<TipoDoCorte> tiposDoCorte;

    @ElementCollection
    @CollectionTable(name = "horariosFuncionamento", joinColumns = @JoinColumn(name = "barbeariaId"))
    @MapKeyEnumerated(EnumType.STRING)
    private Map<DayOfWeek, List<LocalTime>> horariosDeFuncionamento;

    @OneToMany(mappedBy = "barbearia")
    private Set<Agendamento> agendamentos;

    @OneToMany(mappedBy = "barbearia")
    private Set<Avaliacao> avaliacoes;
}
