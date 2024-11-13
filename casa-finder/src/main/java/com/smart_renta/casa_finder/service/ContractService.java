package com.smart_renta.casa_finder.service;

import com.smart_renta.casa_finder.model.Contract;
import com.smart_renta.casa_finder.repository.IContractRepository;

import jakarta.persistence.Column;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ContractService {

    @Autowired
    private IContractRepository contractRepository;

    public Contract saveContract(Contract contract) {
        return contractRepository.save(contract);
    }

    public Optional<Contract> getContractById(Long id) {
        return contractRepository.findById(id);
    }

    @Transactional
    public Contract updateContract(Long id, Contract updatedContract) {
        Optional<Contract> existingContractOpt = contractRepository.findById(id);

        if (existingContractOpt.isPresent()) {
            Contract existingContract = existingContractOpt.get();

            existingContract.setLandlord(updatedContract.getLandlord());
            existingContract.setTenant(updatedContract.getTenant());
            existingContract.setStartDate(updatedContract.getStartDate());
            existingContract.setEndDate(updatedContract.getEndDate());
            existingContract.setFrequency(updatedContract.getFrequency());
            existingContract.setIsActive(updatedContract.getIsActive());
            existingContract.setProperty(updatedContract.getProperty()); 
            existingContract.setCountry(updatedContract.getCountry()); 
            existingContract.setSignature(updatedContract.getSignature()); 
            existingContract.setFingerprint(updatedContract.getFingerprint()); 
            existingContract.setCreditcard(updatedContract.getCreditcard()); 
            existingContract.setExpirationDate(updatedContract.getExpirationDate()); 
            existingContract.setCvv(updatedContract.getCvv());    

            return contractRepository.save(existingContract);
        } else {
            throw new RuntimeException("Contract not found with id: " + id);
        }
    }

    public void deleteContract(Long id) {
        if (contractRepository.existsById(id)) {
            contractRepository.deleteById(id);
        } else {
            throw new RuntimeException("Contract not found with id: " + id);
        }
    }

    public List<Contract> getAllContracts() {
        return contractRepository.findAll();
    }
}
