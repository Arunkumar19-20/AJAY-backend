package com.pmajay.service;

import com.pmajay.model.Agency;
import com.pmajay.repository.AgencyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AgencyService {

    @Autowired
    private AgencyRepository agencyRepository;

    public List<Agency> getAllAgencies() {
        return agencyRepository.findAll();
    }

    public Agency getAgencyById(Long id) {
        return agencyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Agency not found with id: " + id));
    }

    public Agency createAgency(Agency agency) {
        return agencyRepository.save(agency);
    }

    public Agency updateAgency(Long id, Agency agencyDetails) {
        Agency agency = getAgencyById(id);
        agency.setName(agencyDetails.getName());
        agency.setType(agencyDetails.getType());
        agency.setComponent(agencyDetails.getComponent());
        agency.setDistrict(agencyDetails.getDistrict());
        agency.setStateId(agencyDetails.getStateId());
        agency.setNodalOfficer(agencyDetails.getNodalOfficer());
        agency.setContact(agencyDetails.getContact());
        return agencyRepository.save(agency);
    }

    public void deleteAgency(Long id) {
        agencyRepository.deleteById(id);
    }

    public List<Agency> getAgenciesByState(Long stateId) {
        return agencyRepository.findByStateId(stateId);
    }

    public List<Agency> getAgenciesByComponent(Agency.Component component) {
        return agencyRepository.findByComponent(component);
    }
}
