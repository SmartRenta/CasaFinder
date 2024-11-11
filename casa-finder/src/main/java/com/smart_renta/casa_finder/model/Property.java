package com.smart_renta.casa_finder.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "properties")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Property {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private Double price;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Currency currency;

    @Column(nullable = false)
    private Integer timePeriod = 1;

    @Column(nullable = false)
    private Integer floors;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    private Integer parking;

    @Column(nullable = false)
    private Integer rooms;

    @Column(nullable = false)
    private Integer bathrooms;

    @Column(nullable = false, length = 500)
    private String description;

    @ElementCollection
    @Column(nullable = false)
    private List<String> features = new ArrayList<>();

    @ElementCollection
    @Column(nullable = false)
    private List<String> includes = new ArrayList<>();

    @ElementCollection
    @Column(nullable = false)
    private List<String> images = new ArrayList<>();

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User landlord;

    @Column(nullable = false)
    private String region;

    @Column(nullable = false)
    private String province;

    @Column(nullable = false)
    private String district;

    @Column(nullable = false)
    private String address;

    @PrePersist
    private void ensureLandlordUserType() {
        if (this.landlord != null && this.landlord.getUserType() != UserType.LANDLORD) {
            throw new IllegalStateException("La propiedad debe estar asociada a un usuario de tipo LANDLORD.");
        }
    }
}
