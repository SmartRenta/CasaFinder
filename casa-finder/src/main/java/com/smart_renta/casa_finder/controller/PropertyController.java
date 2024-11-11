package com.smart_renta.casa_finder.controller;

import com.smart_renta.casa_finder.model.Property;
import com.smart_renta.casa_finder.service.PropertyService;
import com.smart_renta.casa_finder.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/properties")
public class PropertyController {

    @Autowired
    private PropertyService propertyService;

    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping("/")
    public ResponseEntity<List<Property>> getAllProperties(@RequestHeader("Authorization") String token) {
        validateToken(token);
        List<Property> properties = propertyService.getAllProperties();
        return ResponseEntity.ok(properties);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Property> getPropertyById(@RequestHeader("Authorization") String token, @PathVariable Long id) {
        validateToken(token);
        return propertyService.getPropertyById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/")
    public ResponseEntity<Property> createProperty(@RequestHeader("Authorization") String token, @RequestBody Property property) {
        validateToken(token);
        Property createdProperty = propertyService.saveProperty(property);
        return ResponseEntity.ok(createdProperty);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Property> updateProperty(
            @RequestHeader("Authorization") String token,
            @PathVariable Long id,
            @RequestBody Property property) {
        validateToken(token);
        Property updatedProperty = propertyService.updateProperty(id, property);
        return ResponseEntity.ok(updatedProperty);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProperty(@RequestHeader("Authorization") String token, @PathVariable Long id) {
        validateToken(token);
        propertyService.deleteProperty(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<Property>> findPropertiesByLocation(
            @RequestHeader("Authorization") String token,
            @RequestParam String region,
            @RequestParam String province,
            @RequestParam String district) {
        validateToken(token);
        List<Property> properties = propertyService.findPropertiesByRegionAndProvinceAndDistrict(region, province, district);
        return ResponseEntity.ok(properties);
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