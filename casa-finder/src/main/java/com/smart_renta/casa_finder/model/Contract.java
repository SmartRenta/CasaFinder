package com.smart_renta.casa_finder.model;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "contracts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Contract {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "Landlord_User_id", nullable = false)
    private User landlord;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "Tenant_User_id", nullable = false)
    private User tenant;

    @Column(nullable = false)
    private Date startDate;

    @Column(nullable = false)
    private Date endDate;

    @Column(nullable = false)
    private String frequency;

    @Column(nullable = false)
    private String country;

    @Column(nullable = false)
    private String signature;

    @Column(nullable = false)
    private String fingerprint;

    @Column(nullable = false)
    private String creditcard;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private Date expirationDate;

    @Column(nullable = false)
    private Number cvv;

    @Column(nullable = false)
    private Boolean isActive;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "property_id", nullable = false)
    private Property property;

    @PrePersist
    private void ensureUserType() {
        if (this.landlord != null && this.landlord.getUserType() != UserType.LANDLORD) {
            throw new IllegalStateException("El contrato debe estar asociado a un usuario de tipo LANDLORD.");
        }
        if (this.tenant != null && this.tenant.getUserType() != UserType.TENANT) {
            throw new IllegalStateException("El contrato debe estar asociado a un usuario de tipo TENANT.");
        }
    }
}
