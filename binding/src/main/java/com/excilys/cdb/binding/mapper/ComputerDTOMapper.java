package com.excilys.cdb.binding.mapper;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.cdb.binding.dto.CompanyDTO;
import com.excilys.cdb.binding.dto.ComputerDTO;
import com.excilys.cdb.core.model.Company;
import com.excilys.cdb.core.model.Computer;

public class ComputerDTOMapper {
    private static Logger logger = LoggerFactory.getLogger(ComputerDTOMapper.class);

    public static Computer mapDtoToComputer(ComputerDTO computerDTO) {
	Computer computer = new Computer();
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	try {
	    Long id = null;
	    if (computerDTO.getId() != null) {
		id = Long.valueOf(computerDTO.getId());
	    }
	    String name = computerDTO.getName();
	    LocalDate introduced = null;
	    if (!computerDTO.getIntroduced().equals(null) && !computerDTO.getIntroduced().isEmpty()) {
		introduced = LocalDate.parse(computerDTO.getIntroduced(), formatter);
	    }
	    LocalDate discontinued = null;
	    if (!computerDTO.getDiscontinued().equals(null) && !computerDTO.getDiscontinued().isEmpty()) {
		discontinued = LocalDate.parse(computerDTO.getDiscontinued(), formatter);
	    }
	    Long company_id = null;
	    String company_name = null;
	    if (computerDTO.getCompany() != null && !computerDTO.getCompany().getId().isEmpty()) {
		company_id = Long.valueOf(computerDTO.getCompany().getId());
		company_name = computerDTO.getCompany().getName();
		computer.setCompany(new Company.Builder().setId(company_id).setName(company_name).build());
	    }
	    computer.setId(id);
	    computer.setName(name);
	    computer.setIntroduced(introduced);
	    computer.setDiscontinued(discontinued);
	} catch (Exception e) {
	    e.printStackTrace();
	    logger.error("Erreur -> Mapping ComputerDTO to Computer", e.getMessage());
	}
	return computer;
    }

    public static ComputerDTO mapComputertoDTO(Computer computer) {
	ComputerDTO computerDTO = new ComputerDTO();
	if (computer.getId() != 0L) {
	    computerDTO.setId(String.valueOf(computer.getId()));
	}
	if (computer.getName() != null) {
	    computerDTO.setName(computer.getName());
	}
	if (computer.getIntroduced() != null) {
	    computerDTO.setIntroduced(computer.getIntroduced().toString());
	}
	if (computer.getDiscontinued() != null) {
	    computerDTO.setDiscontinued(computer.getDiscontinued().toString());
	}
	if (computer.getCompany() != null && computer.getCompany().getId() != null) {
	    CompanyDTO companyDTO = new CompanyDTO.Builder().setId(computer.getCompany().getId().toString())
		    .setName(computer.getCompany().getName()).build();
	    computerDTO.setCompany(companyDTO);
	}
	return computerDTO;
    }
}
