package com.smart_renta.casa_finder.repository;

 import com.smart_renta.casa_finder.model.SmartContract;
import org.springframework.data.jpa.repository.JpaRepository;
 import org.springframework.stereotype.Repository;

 import java.util.List;

@Repository
public interface ISmartContractRepository extends JpaRepository<SmartContract, Long> {

  List<SmartContract>  findByContractTenantId(Long tenantId);
  List<SmartContract> findSmartContractByContractId(Long contractId);

}