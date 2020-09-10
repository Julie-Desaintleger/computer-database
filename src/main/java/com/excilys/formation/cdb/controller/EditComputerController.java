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
	logger.info("POST editComputer");

	ComputerDTO computerDTO = new ComputerDTO();
	CompanyDTO companyDTO = new CompanyDTO();
	Computer computer = new Computer();
	Map<String, String> errors = ComputerValidator.getErrors();
	String result;

	if (errors.isEmpty()) {
	    computerDTO.setId(id);
	    computerDTO.setName(computerName);
	    computerDTO.setIntroduced(introduced);
	    computerDTO.setDiscontinued(discontinued);
	    companyDTO.setId(companyId);
	    computerDTO.setCompany(companyDTO);

	    logger.debug(computerDTO.toString());

	    if (ComputerValidator.validateComputer(computerDTO)) {
		computer = ComputerDTOMapper.mapDtoToComputer(computerDTO);
		computerService.update(computer);
		result = "Computer updated with success.";
		logger.info("Update computer done");
		model.addAttribute("result", result);
		return "redirect:/";
	    } else {
		logger.debug("if failed to update :\n" + computerDTO.toString());
		result = "Fail to update this computer.";
		logger.info("Update don't work");
		model.addAttribute("errors", errors);
		model.addAttribute("result", result);
	    }
	}
	errors.clear();
	return null;
    }
}
