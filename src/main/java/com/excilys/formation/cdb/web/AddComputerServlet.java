package com.excilys.formation.cdb.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.formation.cdb.dto.CompanyDTO;
import com.excilys.formation.cdb.dto.ComputerDTO;
import com.excilys.formation.cdb.dto.mapper.CompanyDTOMapper;
import com.excilys.formation.cdb.dto.mapper.ComputerDTOMapper;
import com.excilys.formation.cdb.model.Company;
import com.excilys.formation.cdb.model.Computer;
import com.excilys.formation.cdb.service.CompanyService;
import com.excilys.formation.cdb.service.ComputerService;

/**
 * Servlet implementation class AddComputerServlet
 */
@WebServlet("/addComputer")
public class AddComputerServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final Logger logger = LoggerFactory.getLogger(AddComputerServlet.class);
    private static CompanyService companyService = CompanyService.getInstance();
    private static ComputerService computerService = ComputerService.getInstance();

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

	try {
	    request.getParameter("computerName");
	    computerDTO.setName(request.getParameter("computerName"));
	} catch (Exception e) {
	    logger.error("computer name", e.getMessage());
	}

	try {
	    request.getParameter("introduced");
	    computerDTO.setIntroduced(request.getParameter("introduced"));
	    request.getParameter("discontinued");
	    computerDTO.setDiscontinued(request.getParameter("discontinued"));

	} catch (Exception e) {
	    logger.error("error with introduced/discontinued", e.getMessage());
	}

	companyDTO.setId(request.getParameter("companyId"));
	computerDTO.setCompany(companyDTO);
	computer = ComputerDTOMapper.mapDtoToComputer(computerDTO);
	computerService.insert(computer);

	doGet(request, response);

    }

}
