package com.smart_renta.casa_finder.repository;

import com.smart_renta.casa_finder.model.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IPropertyRepository extends JpaRepository<Property, Long> {
    List<Property> findPropertiesByRegionAndProvinceAndDistrict(String region, String province, String district);
    List<Property> findPropertiesByLandlordId(Long landlordId);
}
