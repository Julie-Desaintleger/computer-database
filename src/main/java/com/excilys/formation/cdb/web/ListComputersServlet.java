package com.excilys.formation.cdb.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.excilys.formation.cdb.dto.ComputerDTO;
import com.excilys.formation.cdb.dto.mapper.ComputerDTOMapper;
import com.excilys.formation.cdb.model.Computer;
import com.excilys.formation.cdb.model.Page;
import com.excilys.formation.cdb.service.ComputerService;

/**
 * Servlet implementation class for dashboard : list of computers
 */
public class ListComputersServlet extends HttpServlet {
    private static final long serialVersionUID = 2L;
    private static Page page = new Page();
    private static Logger logger = LoggerFactory.getLogger(ListComputersServlet.class);

    @Autowired
    public ComputerService computerService;

    @Override
    public void init(ServletConfig config) throws ServletException {
	super.init(config);
	SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, config.getServletContext());
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
	List<Computer> computers = new ArrayList<Computer>();
	List<ComputerDTO> computersDto = new ArrayList<ComputerDTO>();

	String inputSearch = request.getParameter("search");
	String order = request.getParameter("order");
	int nbComputers = computerService.countComputers(inputSearch);
	int nbPages = page.getTotalPage();

	if (request.getParameter("pageNumber") != null) {
	    int pageAsked = Integer.parseInt(request.getParameter("pageNumber"));
	    if (pageAsked > 0 & pageAsked <= nbPages) {
		page.setCurrentPage(pageAsked);
		page.setFirstLine(page.getRows() * (page.getCurrentPage() - 1) + 1);
	    }
	}

	if (request.getParameter("lineNumber") != null) {
	    int lineAsked = Integer.parseInt(request.getParameter("lineNumber"));
	    page.setRows(lineAsked);
	    page.setCurrentPage(1);
	    page.setFirstLine(0);
	    nbPages = page.getTotalPages(nbComputers);
	}

	computers = computerService.getComputerBySearchOrdered(page, inputSearch, order);
	computers.stream().forEach(computer -> computersDto.add(ComputerDTOMapper.mapComputertoDTO(computer)));

	request.setAttribute("nbComputers", nbComputers);
	request.setAttribute("currentPage", page.getCurrentPage());
	request.setAttribute("totalPages", nbPages);
	request.setAttribute("lineNumber", page.getRows());
	request.setAttribute("search", inputSearch);
	request.setAttribute("order", order);
	request.setAttribute("ListComputers", computersDto);
	this.getServletContext().getRequestDispatcher("/WEB-INF/views/listComputers.jsp").forward(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
	if (request.getParameter("selection") != null) {
	    String[] idToDelete = request.getParameter("selection").split(",");
	    for (int i = 0; i < idToDelete.length; i++) {
		Long id = Long.valueOf(idToDelete[i]);
		computerService.delete(id);
		logger.info("Computers deleted");
	    }
	}
	doGet(request, response);
    }

}
