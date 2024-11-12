package com.smart_renta.casa_finder.model;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "smartcontracts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SmartContract {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "Contract_id", nullable = false)
    private Contract contract;

    @Column(nullable = false, length = 250)
    private String contractCode;

    @Column(nullable = false, length = 50)
    private String executionStatus;

    @Column(nullable = false, length = 250)
    private String deployedUrl;

}
