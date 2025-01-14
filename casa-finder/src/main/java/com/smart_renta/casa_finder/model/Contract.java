package com.smart_renta.casa_finder.model;

import java.time.LocalDateTime;
import java.time.ZoneId;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
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
    private LocalDateTime startDate;

    @Column()
    private LocalDateTime endDate;

    @Column()
    private String frequency;

    @Column()
    private String address;

    @Column()
    private String country;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String signature;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String fingerprint;

    @Column()
    private Boolean isActive;

    @Column()
    private Boolean accepted;
    private LocalDateTime acceptedDate;

    @Enumerated(EnumType.STRING)
    private ContractStatus status;
    
    private LocalDateTime creationDate = LocalDateTime.now(ZoneId.of("GMT-5"));

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
