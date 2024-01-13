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
@Table(name = "agendamento")
public class Agendamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToOne
    @MapsId
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "barbeariaId")
    private Barbearia barbearia;

    @ManyToOne
    @JoinColumn(name = "tipoDoCorteId")
    private TipoDoCorte tipoDoCorte;

    @Column(name = "dataHora", nullable = false)
    private LocalDateTime dataHora;

    @Enumerated(EnumType.STRING)
    private Status status;

    @OneToOne(mappedBy = "agendamento")
    private Pagamento pagamento;
}
