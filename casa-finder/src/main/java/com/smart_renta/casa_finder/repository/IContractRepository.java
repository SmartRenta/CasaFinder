package com.smart_renta.casa_finder.repository;

import com.smart_renta.casa_finder.model.Contract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IContractRepository extends JpaRepository<Contract, Long> {
    
}
