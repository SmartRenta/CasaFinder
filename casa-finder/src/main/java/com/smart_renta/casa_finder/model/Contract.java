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

    @Column()
    private Date startDate;

    @Column()
    private Date endDate;

    @Column()
    private String frequency;

    @Column()
    private String country;

    @Column()
    private String signature;

    @Column()
    private String fingerprint;

    @Column()
    private String creditcard;

    @Column()
    private String address;

    @Column()
    private Date expirationDate;

    @Column()
    private Number cvv;

    @Column()
    private Boolean isActive;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "property_id")
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
