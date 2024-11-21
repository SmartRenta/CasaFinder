package com.smart_renta.casa_finder.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "smart_contracts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SmartContract {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 255)
    private String contractAddress;

    @Column(nullable = false, length = 255)
    private String landlordAddress;

    @Column(nullable = false, length = 255)
    private String tenantAddress;

    @Column(nullable = false)
    private Double paymentAmountETH;

    @Column(nullable = false)
    private Boolean payed = false;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "contract_id", nullable = false)
    private Contract contract;
}
