package com.excilys.formation.cdb.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.excilys.formation.cdb.dto.CompanyDTO;
import com.excilys.formation.cdb.dto.ComputerDTO;
import com.excilys.formation.cdb.dto.mapper.CompanyDTOMapper;
import com.excilys.formation.cdb.dto.mapper.ComputerDTOMapper;
import com.excilys.formation.cdb.exception.DateDiscontinuedException;
import com.excilys.formation.cdb.exception.EmptyDateException;
import com.excilys.formation.cdb.exception.NameException;
import com.excilys.formation.cdb.model.Company;
import com.excilys.formation.cdb.model.Computer;
import com.excilys.formation.cdb.service.CompanyService;
import com.excilys.formation.cdb.service.ComputerService;
import com.excilys.formation.cdb.validator.ComputerValidator;

@Controller
@RequestMapping("/editComputer")
public class EditComputerController {
    private static Logger logger = LoggerFactory.getLogger(EditComputerController.class);

    @Autowired
    private CompanyService companyService;

    @Autowired
    private ComputerService computerService;

    @GetMapping
    public String get(Model model, @RequestParam String id) {
	logger.info("GET editComputer");

	Computer computer = new Computer();
	ComputerDTO computerDTO = new ComputerDTO();
	if (id != null) {
	    computer = computerService.getById(Long.parseLong(id));
	}

	if (computer != null) {
	    computerDTO = ComputerDTOMapper.mapComputertoDTO(computer);
	} else {
	    logger.info("The computer does not exist");
	}

	List<Company> companies = companyService.getAll();
	List<CompanyDTO> companiesDTO = new ArrayList<CompanyDTO>();
	companies.stream().forEach(company -> companiesDTO.add(CompanyDTOMapper.mapCompanytoDTO(company)));

	model.addAttribute("ListCompanies", companiesDTO);
	model.addAttribute("computer", computerDTO);
	return "editComputer";

    }

    @PostMapping
    public String post(Model model, @RequestParam String id, @RequestParam String computerName,
	    @RequestParam String companyId, @RequestParam String introduced, @RequestParam String discontinued) {
	ComputerDTO computerDTO = new ComputerDTO();
	CompanyDTO companyDTO = new CompanyDTO();
	Computer computer = new Computer();

	Map<String, String> errors = new HashMap<String, String>();
	String result;

	try {
	    ComputerValidator.validatorName(computerName);
	    computerDTO.setName(computerName);
	} catch (NameException e) {
	    logger.error("computer name", e.getMessage());
	    errors.put("computerName", e.getMessage());
	}

	if (discontinued != null) {
	    try {
		ComputerValidator.validatorDate(introduced, discontinued);
	    } catch (DateDiscontinuedException e) {
		logger.error("error with discontinued date", e.getMessage());
		errors.put("dateDiscontinued", e.getMessage());
	    } catch (EmptyDateException e) {
		logger.error("error with introduced/discontinued date", e.getMessage());
		errors.put("dateIntroduced", e.getMessage());
	    }
	}

	if (errors.isEmpty()) {
	    computerDTO.setId(id);
	    computerDTO.setIntroduced(introduced);
	    computerDTO.setDiscontinued(discontinued);
	    companyDTO.setId(companyId);
	    computerDTO.setCompany(companyDTO);
	    computer = ComputerDTOMapper.mapDtoToComputer(computerDTO);
	    computerService.update(computer);
	    result = "Computer updated with success.";
	    logger.info("Update computer done");
	    return "redirect:/";
	} else {
	    result = "Fail to update this computer.";
	    logger.info("Update don't work");
	    model.addAttribute("errors", errors);
	    model.addAttribute("result", result);
	    return "editComputer";
	}
    }
}
