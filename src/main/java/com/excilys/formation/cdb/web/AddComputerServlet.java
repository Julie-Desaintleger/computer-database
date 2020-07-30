package com.excilys.formation.cdb.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

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

/**
 * Servlet implementation class AddComputerServlet
 */
public class AddComputerServlet extends HttpServlet {
    private static final long serialVersionUID = 2L;
    private final Logger logger = LoggerFactory.getLogger(AddComputerServlet.class);

    @Autowired
    private static CompanyService companyService;

    @Autowired
    private static ComputerService computerService;

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
	List<Company> companies = companyService.getAll();
	List<CompanyDTO> companiesDTO = new ArrayList<CompanyDTO>();

	companies.stream().forEach(company -> companiesDTO.add(CompanyDTOMapper.mapCompanytoDTO(company)));

	request.setAttribute("ListCompanies", companiesDTO);
	request.getRequestDispatcher("/WEB-INF/views/addComputer.jsp").forward(request, response);

    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
	ComputerDTO computerDTO = new ComputerDTO();
	CompanyDTO companyDTO = new CompanyDTO();
	Computer computer = new Computer();

	Map<String, String> errors = new HashMap<String, String>();
	String result;

	try {
	    ComputerValidator.validatorName(request.getParameter("computerName"));
	    computerDTO.setName(request.getParameter("computerName"));
	} catch (NameException e) {
	    logger.error("computer name", e.getMessage());
	    errors.put("computerName", e.getMessage());
	}

	if (request.getParameter("discontinued") != null) {
	    try {
		ComputerValidator.validatorDate(request.getParameter("introduced"),
			request.getParameter("discontinued"));
	    } catch (DateDiscontinuedException e) {
		logger.error("error with discontinued date", e.getMessage());
		errors.put("dateDiscontinued", e.getMessage());
	    } catch (EmptyDateException e) {
		logger.error("error with introduced/discontinued date", e.getMessage());
		errors.put("dateIntroduced", e.getMessage());
	    }
	}

	if (errors.isEmpty()) {
	    computerDTO.setIntroduced(request.getParameter("introduced"));
	    computerDTO.setDiscontinued(request.getParameter("discontinued"));
	    companyDTO.setId(request.getParameter("companyId"));
	    computerDTO.setCompany(companyDTO);
	    computer = ComputerDTOMapper.mapDtoToComputer(computerDTO);
	    computerService.insert(computer);
	    result = "Computer added with success.";
	    logger.info("Insert computer done");
	} else {
	    result = "Fail to add this computer.";
	    logger.info("Insert don't work");

	}

	request.setAttribute("errors", errors);
	request.setAttribute("result", result);

	doGet(request, response);

    }

}
