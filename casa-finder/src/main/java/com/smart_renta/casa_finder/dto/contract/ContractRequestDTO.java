package com.smart_renta.casa_finder.dto.contract;

import java.util.Date;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

public class ContractRequestDTO {

    @NotNull(message = "Tenant ID is mandatory")
    @Getter
    @Setter
    private Long landlordId;

    @NotNull(message = "Tenant ID is mandatory")
    @Getter
    @Setter
    private Long tenantId;

    @NotNull(message = "Start date is mandatory")
    @Getter
    @Setter
    private Date startDate;

    @NotNull(message = "End date is mandatory")
    @Getter
    @Setter
    private Date endDate;

    @NotBlank(message = "Frequency is mandatory")
    @Getter
    @Setter
    private String frequency;

    @NotBlank(message = "Address is mandatory")
    @Getter
    @Setter
    private String address;

    @NotBlank(message = "Country is mandatory")
    @Getter
    @Setter
    private String country;

    @NotBlank(message = "Signature is mandatory")
    @Size(max = 10485760, message = "Signature is too large") 
    @Getter
    @Setter
    private String signature;

    @NotBlank(message = "Fingerprint is mandatory")
    @Size(max = 10485760, message = "Fingerprint is too large") 
    @Getter
    @Setter
    private String fingerprint;

    @NotNull(message = "Active status is mandatory")
    @Getter
    @Setter
    private Boolean isActive;

    @NotNull(message = "Property ID is mandatory")
    @Getter
    @Setter
    private Long propertyId;
}
