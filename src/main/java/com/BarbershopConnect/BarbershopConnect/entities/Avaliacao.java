package com.BarbershopConnect.BarbershopConnect.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "avaliacao")
public class Avaliacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "barbeariaId")
    private Long barbeariaId;

    @Column(name = "barbeiroId")
    private Long barbeiroId;

    @ManyToOne
    @JoinColumn(name = "clienteId")
    private Cliente cliente;

    @Column(name = "pontuacao", nullable = false)
    private Integer pontuacao;

    @Column(name = "comentario")
    private String comentario;

    @Column(name = "dataAvaliacao")
    private LocalDateTime dataAvaliacao;

    @Enumerated(EnumType.STRING)
    private Avaliador avaliador;
}
