package com.smart_renta.casa_finder.controller;

import com.smart_renta.casa_finder.dto.contract.ContractRequestDTO;
import com.smart_renta.casa_finder.model.Contract;
import com.smart_renta.casa_finder.model.User;
import com.smart_renta.casa_finder.model.Property;
import com.smart_renta.casa_finder.service.ContractService;
import com.smart_renta.casa_finder.service.UserService;
import com.smart_renta.casa_finder.service.PropertyService;
import com.smart_renta.casa_finder.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    private JwtUtil jwtUtil;

    @GetMapping("/")
    public ResponseEntity<List<Contract>> getAllContracts(@RequestHeader("Authorization") String token) {
        validateToken(token);
        List<Contract> contracts = contractService.getAllContracts();
        return ResponseEntity.ok(contracts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Contract> getContractById(@RequestHeader("Authorization") String token, @PathVariable Long id) {
        validateToken(token);
        return contractService.getContractById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/")
    public ResponseEntity<Contract> createContract(
            @RequestHeader("Authorization") String token,
            @RequestBody ContractRequestDTO contractDTO) {
        validateToken(token);

        User landlord = userService.findByEmail(contractDTO.getLandlordEmail());

        User tenant = userService.findById(contractDTO.getTenantId());

        Property property = propertyService.getPropertyById(contractDTO.getPropertyId());

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
        return ResponseEntity.ok(createdContract);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Contract> updateContract(
            @RequestHeader("Authorization") String token,
            @PathVariable Long id,
            @RequestBody Contract contract) {
        validateToken(token);
        Contract updatedContract = contractService.updateContract(id, contract);
        return ResponseEntity.ok(updatedContract);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContract(@RequestHeader("Authorization") String token, @PathVariable Long id) {
        validateToken(token);
        contractService.deleteContract(id);
        return ResponseEntity.noContent().build();
    }

    private void validateToken(String token) {
        if (!token.startsWith("Bearer ")) {
            throw new IllegalArgumentException("Invalid token format");
        }
        String jwt = token.substring(7);
        String username = jwtUtil.extractUsername(jwt);
        if (username == null || username.isEmpty()) {
            throw new IllegalArgumentException("Invalid token");
        }
    }
}
