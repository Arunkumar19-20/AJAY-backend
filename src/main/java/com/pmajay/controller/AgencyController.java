package com.pmajay.controller;

import com.pmajay.model.Agency;
import com.pmajay.service.AgencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/agencies")
public class AgencyController {

    @Autowired
    private AgencyService agencyService;

    @GetMapping
    public ResponseEntity<List<Agency>> getAllAgencies() {
        return ResponseEntity.ok(agencyService.getAllAgencies());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Agency> getAgencyById(@PathVariable Long id) {
        return ResponseEntity.ok(agencyService.getAgencyById(id));
    }

    @PostMapping
    public ResponseEntity<Agency> createAgency(@RequestBody Agency agency) {
        return ResponseEntity.ok(agencyService.createAgency(agency));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Agency> updateAgency(@PathVariable Long id, @RequestBody Agency agency) {
        return ResponseEntity.ok(agencyService.updateAgency(id, agency));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAgency(@PathVariable Long id) {
        agencyService.deleteAgency(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/state/{stateId}")
    public ResponseEntity<List<Agency>> getAgenciesByState(@PathVariable Long stateId) {
        return ResponseEntity.ok(agencyService.getAgenciesByState(stateId));
    }

    @GetMapping("/component/{component}")
    public ResponseEntity<List<Agency>> getAgenciesByComponent(@PathVariable Agency.Component component) {
        return ResponseEntity.ok(agencyService.getAgenciesByComponent(component));
    }
}
