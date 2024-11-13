package com.smart_renta.casa_finder.service;

import com.smart_renta.casa_finder.model.Property;
import com.smart_renta.casa_finder.repository.IPropertyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PropertyService {

    @Autowired
    private IPropertyRepository propertyRepository;

    public Property saveProperty(Property property) {
        return propertyRepository.save(property);
    }

    public Optional<Property> getPropertyById(Long id) {
        return propertyRepository.findById(id);
    }

    @Transactional
    public Property updateProperty(Long id, Property updatedProperty) {
        Optional<Property> existingPropertyOpt = propertyRepository.findById(id);

        if (existingPropertyOpt.isPresent()) {
            Property existingProperty = existingPropertyOpt.get();

            existingProperty.setTitle(updatedProperty.getTitle());
            existingProperty.setPrice(updatedProperty.getPrice());
            existingProperty.setCurrency(updatedProperty.getCurrency());
            existingProperty.setTimePeriod(updatedProperty.getTimePeriod());
            existingProperty.setFloors(updatedProperty.getFloors());
            existingProperty.setType(updatedProperty.getType());
            existingProperty.setParking(updatedProperty.getParking());
            existingProperty.setRooms(updatedProperty.getRooms());
            existingProperty.setBathrooms(updatedProperty.getBathrooms());
            existingProperty.setDescription(updatedProperty.getDescription());
            existingProperty.setFeatures(updatedProperty.getFeatures());
            existingProperty.setIncludes(updatedProperty.getIncludes());
            existingProperty.setImages(updatedProperty.getImages());
            existingProperty.setRegion(updatedProperty.getRegion());
            existingProperty.setProvince(updatedProperty.getProvince());
            existingProperty.setDistrict(updatedProperty.getDistrict());
            existingProperty.setAddress(updatedProperty.getAddress());
            existingProperty.setLandlord(updatedProperty.getLandlord());
            return propertyRepository.save(existingProperty);
        } else {
            throw new RuntimeException("Property not found with id: " + id);
        }
    }

    public void deleteProperty(Long id) {
        if (propertyRepository.existsById(id)) {
            propertyRepository.deleteById(id);
        } else {
            throw new RuntimeException("Property not found with id: " + id);
        }
    }

    public List<Property> findPropertiesByRegionAndProvinceAndDistrict(String region, String province, String district) {
        return propertyRepository.findPropertiesByRegionAndProvinceAndDistrict(region, province, district);
    }

    public List<Property> findPropertiesByLandlordId(Long landlordId){
        return propertyRepository.findPropertiesByLandlordId(landlordId);
    }

    public List<Property> getAllProperties() {
        return propertyRepository.findAll();
    }
}
