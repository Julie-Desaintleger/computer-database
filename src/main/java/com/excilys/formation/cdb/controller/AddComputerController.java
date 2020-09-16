package com.excilys.formation.cdb.controller;

import java.util.ArrayList;
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
import com.excilys.formation.cdb.model.Company;
import com.excilys.formation.cdb.model.Computer;
import com.excilys.formation.cdb.service.CompanyService;
import com.excilys.formation.cdb.service.ComputerService;
import com.excilys.formation.cdb.validator.ComputerValidator;

@Controller
@RequestMapping("/addComputer")
public class AddComputerController {
    private static Logger logger = LoggerFactory.getLogger(AddComputerController.class);

    @Autowired
    private CompanyService companyService;

    @Autowired
    private ComputerService computerService;

    @GetMapping
    public String getComputer(Model model) {
	logger.info("GET addComputer");

	List<Company> companies = companyService.getAll();
	List<CompanyDTO> companiesDTO = new ArrayList<CompanyDTO>();

	companies.stream().forEach(company -> companiesDTO.add(CompanyDTOMapper.mapCompanytoDTO(company)));

	model.addAttribute("ListCompanies", companiesDTO);
	return "addComputer";
    }

    @PostMapping
    public String post(Model model, @RequestParam String computerName, @RequestParam String companyId,
	    @RequestParam String introduced, @RequestParam String discontinued) {
	ComputerDTO computerDTO = new ComputerDTO();
	CompanyDTO companyDTO = new CompanyDTO();
	Computer computer = new Computer();

	Map<String, String> errors = ComputerValidator.getErrors();
	String result;

	if (errors.isEmpty()) {
	    computerDTO.setName(computerName);
	    computerDTO.setIntroduced(introduced);
	    computerDTO.setDiscontinued(discontinued);
	    companyDTO.setId(companyId);
	    computerDTO.setCompany(companyDTO);

	    if (ComputerValidator.validateComputer(computerDTO)) {
		logger.debug("Computer DTO is valid: {}", computerDTO);
		computer = ComputerDTOMapper.mapDtoToComputer(computerDTO);
		computerService.insert(computer);
		result = "Computer added with success.";
		logger.info("Insert computer done");
		errors.clear();
		return "redirect:/";
	    } else {
		result = "Fail to add this computer.";
		logger.info("Insert don't work");
		model.addAttribute("errors", errors);
		model.addAttribute("result", result);
		logger.debug("errors values " + errors.values().toString());
		return "addComputer";
	    }
	}

	errors.clear();
	return "addComputer";
    }
}
