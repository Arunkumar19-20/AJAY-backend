package com.pmajay.repository;

import com.pmajay.model.Agency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AgencyRepository extends JpaRepository<Agency, Long> {
    List<Agency> findByStateId(Long stateId);
    List<Agency> findByComponent(Agency.Component component);
    long countByComponent(Agency.Component component);
}
