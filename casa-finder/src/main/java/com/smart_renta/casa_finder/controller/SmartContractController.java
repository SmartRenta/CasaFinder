package com.smart_renta.casa_finder.controller;

import com.smart_renta.casa_finder.model.SmartContract;
import com.smart_renta.casa_finder.service.SmartContractService;
import com.smart_renta.casa_finder.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/smart-contracts")
public class SmartContractController {

    @Autowired
    private SmartContractService smartContractService;

    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping("/")
    public ResponseEntity<List<SmartContract>> getAllSmartContracts(@RequestHeader("Authorization") String token) {
        validateToken(token);
        List<SmartContract> smartContracts = smartContractService.findAll();
        return ResponseEntity.ok(smartContracts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SmartContract> getSmartContractById(@RequestHeader("Authorization") String token, @PathVariable Long id) {
        validateToken(token);
        SmartContract smartContract = smartContractService.findById(id);
        if (smartContract == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(smartContract);
    }

    @PostMapping("/")
    public ResponseEntity<SmartContract> createSmartContract(@RequestHeader("Authorization") String token, @RequestBody SmartContract smartContract) {
        validateToken(token);
        SmartContract createdSmartContract = smartContractService.save(smartContract);
        return ResponseEntity.ok(createdSmartContract);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SmartContract> updateSmartContract(
            @RequestHeader("Authorization") String token,
            @PathVariable Long id,
            @RequestBody SmartContract smartContract) {
        validateToken(token);
        smartContract.setId(id);
        SmartContract updatedSmartContract = smartContractService.update(smartContract);
        return ResponseEntity.ok(updatedSmartContract);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSmartContract(@RequestHeader("Authorization") String token, @PathVariable Long id) {
        validateToken(token);
        smartContractService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/tenant/{tenantId}")
    public ResponseEntity<List<SmartContract>> getSmartContractsByTenantId(
            @RequestHeader("Authorization") String token,
            @PathVariable Long tenantId) {
        validateToken(token);
        List<SmartContract> smartContracts = smartContractService.getSmartContractsByTenantId(tenantId);
        if (smartContracts.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(smartContracts);
    }


    @GetMapping("/contract/{contractId}")
    public ResponseEntity<List<SmartContract>> getSmartContractsByContractId(
            @RequestHeader("Authorization") String token,
            @PathVariable Long contractId) {
        validateToken(token);
        List<SmartContract> smartContracts = smartContractService.findSmartContractByContractId(contractId);
        if (smartContracts.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(smartContracts);
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
