package com.terry.economicsimulator.repository;

import com.terry.economicsimulator.model.company.Company;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Integer> {
    
}
