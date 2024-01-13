package com.BarbershopConnect.BarbershopConnect.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "pagamento")
public class Pagamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToOne
    @MapsId
    private Agendamento agendamento;

    @Enumerated(EnumType.STRING)
    private TipoPagamento tipoPagamento;

    @Column(name = "pago")
    private Boolean pago;
}
