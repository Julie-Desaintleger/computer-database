package com.excilys.formation.cdb.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.excilys.formation.cdb.dto.ComputerDTO;
import com.excilys.formation.cdb.dto.DashboardDTO;
import com.excilys.formation.cdb.dto.mapper.ComputerDTOMapper;
import com.excilys.formation.cdb.model.Computer;
import com.excilys.formation.cdb.model.Page;
import com.excilys.formation.cdb.service.ComputerService;

@Controller
@RequestMapping("/listComputers")
public class ListComputersController {
    private static Logger logger = LoggerFactory.getLogger(ListComputersController.class);

    @Autowired
    private ComputerService computerService;

    @GetMapping
    public String getListComputers(Model model, DashboardDTO dashboardDTO) {
	logger.info("GET listComputers");

	Page page = new Page();

	if (dashboardDTO.getLinesNb() != null) {
	    int linesNb = Integer.parseInt(dashboardDTO.getLinesNb());
	    page.setRows(linesNb);
	}

	int total = computerService.countComputers(dashboardDTO.getSearch());
	int nbPages = page.getTotalPages(total);

	if (dashboardDTO.getPageNb() != null) {
	    int pageAsked = Integer.parseInt(dashboardDTO.getPageNb());
	    if (pageAsked > 0 & pageAsked <= nbPages) {
		page.setCurrentPage(pageAsked);
	    }
	}

	page.calculFirstLine();

	List<Computer> computers = computerService.getComputerBySearchOrdered(page, dashboardDTO.getSearch(),
		dashboardDTO.getOrder());
	List<ComputerDTO> computersDTO = computers.stream()
		.map(computer -> ComputerDTOMapper.mapComputertoDTO(computer)).collect(Collectors.toList());

	model.addAttribute("nbComputers", total);
	model.addAttribute("currentPage", page.getCurrentPage());
	model.addAttribute("totalPages", nbPages);
	model.addAttribute("lineNumber", page.getRows());
	model.addAttribute("search", dashboardDTO.getSearch());
	model.addAttribute("order", dashboardDTO.getOrder());
	model.addAttribute("ListComputers", computersDTO);

	return "listComputers";
    }

    @PostMapping
    public String post(@RequestParam(name = "selection", required = false) String selection) {
	logger.info("POST listComputers");
	String[] idToDelete = selection.split(",");
	for (int i = 0; i < idToDelete.length; i++) {
	    Long id = Long.valueOf(idToDelete[i]);
	    computerService.delete(id);
	    logger.info("Computers deleted");
	}
	return "redirect:/";
    }

}
