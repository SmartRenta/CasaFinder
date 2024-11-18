package com.smart_renta.casa_finder.controller;

import com.smart_renta.casa_finder.dto.contract.ContractRequestDTO;
import com.smart_renta.casa_finder.model.Contract;
import com.smart_renta.casa_finder.model.User;
import com.smart_renta.casa_finder.model.Property;
import com.smart_renta.casa_finder.service.ContractService;
import com.smart_renta.casa_finder.service.NotificationService;
import com.smart_renta.casa_finder.service.UserService;
import com.smart_renta.casa_finder.service.PropertyService;
import com.smart_renta.casa_finder.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/v1/contracts")
public class ContractController {

    @Autowired
    private ContractService contractService;

    @Autowired
    private UserService userService;

    @Autowired
    private PropertyService propertyService;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private AuthController authController;

    @GetMapping("/")
    public ResponseEntity<List<Contract>> getAllContracts(@RequestHeader("Authorization") String token) {
        authController.validateToken(token);
        List<Contract> contracts = contractService.getAllContracts();
        return ResponseEntity.ok(contracts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Contract> getContractById(@RequestHeader("Authorization") String token, @PathVariable Long id) {
        authController.validateToken(token);
        return contractService.getContractById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping("/")
    public ResponseEntity<Contract> createContract(
            @RequestHeader("Authorization") String token,
            @RequestBody ContractRequestDTO contractDTO) {
        authController.validateToken(token);

        User landlord = userService.findByEmail(contractDTO.getLandlordEmail());

        User tenant = userService.findById(contractDTO.getTenantId());

        Property property = propertyService.getPropertyById(contractDTO.getPropertyId())
                .orElseThrow(() -> new NoSuchElementException("Propiedad no encontrada"));

        Contract contract = new Contract();
        contract.setLandlord(landlord);
        contract.setTenant(tenant);
        contract.setStartDate(contractDTO.getStartDate());
        contract.setEndDate(contractDTO.getEndDate());
        contract.setFrequency(contractDTO.getFrequency());
        contract.setCountry(contractDTO.getCountry());
        contract.setSignature(contractDTO.getSignature());
        contract.setFingerprint(contractDTO.getFingerprint());
        contract.setCreditcard(contractDTO.getCreditcard());
        contract.setAddress(contractDTO.getAddress());
        contract.setExpirationDate(contractDTO.getExpirationDate());
        contract.setCvv(contractDTO.getCvv());
        contract.setIsActive(contractDTO.getIsActive());
        contract.setProperty(property);

        Contract createdContract = contractService.saveContract(contract);

        if(createdContract.getId() > 0){
            notificationService.saveLandlordContractRequest(tenant, property, landlord, createdContract);
        }

        return ResponseEntity.ok(createdContract);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Contract> updateContract(
            @RequestHeader("Authorization") String token,
            @PathVariable Long id,
            @RequestBody Contract contract) {
        authController.validateToken(token);
        Contract updatedContract = contractService.updateContract(id, contract);
        return ResponseEntity.ok(updatedContract);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContract(@RequestHeader("Authorization") String token, @PathVariable Long id) {
        authController.validateToken(token);
        contractService.deleteContract(id);
        return ResponseEntity.noContent().build();
    }
}
