package com.excilys.formation.cdb.dto.mapper;

import com.excilys.formation.cdb.dto.CompanyDTO;
import com.excilys.formation.cdb.model.Company;

public class CompanyDTOMapper {

    public static Company mapDTOtoCompany(CompanyDTO companyDTO) {
	return new Company(Long.valueOf(companyDTO.getId()), companyDTO.getName());
    }

    public static CompanyDTO mapCompanytoDTO(Company company) {
	return new CompanyDTO(String.valueOf(company.getId()), company.getName());
    }
}