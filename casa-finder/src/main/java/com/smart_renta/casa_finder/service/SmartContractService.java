package com.smart_renta.casa_finder.service;

import com.smart_renta.casa_finder.model.SmartContract;
import com.smart_renta.casa_finder.repository.ISmartContractRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SmartContractService {

    @Autowired
    private ISmartContractRepository smartContractRepository;

    public SmartContract save(SmartContract smartContract) {
        return smartContractRepository.save(smartContract);
    }

    public SmartContract update(SmartContract smartContract) {
        if (!smartContractRepository.existsById(smartContract.getId())) {
            throw new IllegalArgumentException("El SmartContract no existe.");
        }
        return smartContractRepository.save(smartContract);
    }

    public SmartContract findById(Long id) {
        return smartContractRepository.findById(id).orElse(null);
    }

    public void deleteById(Long id) {
        if (!smartContractRepository.existsById(id)) {
            throw new IllegalArgumentException("El SmartContract no existe.");
        }
        smartContractRepository.deleteById(id);
    }

    public List<SmartContract> getSmartContractsByTenantId(Long tenantId) {
        return smartContractRepository.findByContractTenantId(tenantId);
    }

    public List<SmartContract> findSmartContractByContractId(Long contractId) {
        return smartContractRepository.findSmartContractByContractId(contractId);
    }


    public List<SmartContract> findAll() {
        return smartContractRepository.findAll();
    }
}
