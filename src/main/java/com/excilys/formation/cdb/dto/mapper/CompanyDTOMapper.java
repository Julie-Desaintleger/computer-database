package com.excilys.formation.cdb.dto.mapper;

import com.excilys.formation.cdb.dto.CompanyDTO;
import com.excilys.formation.cdb.model.Company;

public class CompanyDTOMapper {

    public static Company mapDTOtoCompany(CompanyDTO companyDTO) {
	Company company = new Company();
	company.setId(Long.valueOf(companyDTO.getId()));
	company.setName(companyDTO.getName());
	return company;
    }

    public static CompanyDTO mapCompanytoDTO(Company company) {
	return new CompanyDTO(String.valueOf(company.getId()), company.getName());
    }
}