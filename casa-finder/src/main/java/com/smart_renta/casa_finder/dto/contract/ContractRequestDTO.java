package com.smart_renta.casa_finder.dto.contract;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import java.sql.Date;

public class ContractRequestDTO {

    @NotBlank(message = "Landlord email is mandatory")
    @Getter
    @Setter
    private String landlordEmail;

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

    @NotBlank(message = "Country is mandatory")
    @Getter
    @Setter
    private String country;

    @NotBlank(message = "Signature is mandatory")
    @Getter
    @Setter
    private String signature;

    @NotBlank(message = "Fingerprint is mandatory")
    @Getter
    @Setter
    private String fingerprint;

    @NotBlank(message = "Credit card is mandatory")
    @Getter
    @Setter
    private String creditcard;

    @NotBlank(message = "Address is mandatory")
    @Getter
    @Setter
    private String address;

    @NotNull(message = "Expiration date is mandatory")
    @Getter
    @Setter
    private Date expirationDate;

    @NotNull(message = "CVV is mandatory")
    @Getter
    @Setter
    private Number cvv;

    @NotNull(message = "Active status is mandatory")
    @Getter
    @Setter
    private Boolean isActive;

    @NotNull(message = "Property ID is mandatory")
    @Getter
    @Setter
    private Long propertyId;
}
