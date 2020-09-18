package com.excilys.cdb.model;

public class Page {
    private Long firstLine;
    private Long rows;
    private Long currentPage;
    private Long totalPage;
    private static final Long DEFAULT_PAGE = 1L;
    private static final Long MAX_LINES = 10L;
    private String search;
    private String orderBy;

    public Page() {
	this.firstLine = 0L;
	this.rows = MAX_LINES;
	this.currentPage = DEFAULT_PAGE;
	this.search = "";
	this.orderBy = "computer.id";
    }

    public Long getFirstLine() {
	return firstLine;
    }

    public void setFirstLine(Long line) {
	this.firstLine = line;
    }

    public Long getRows() {
	return rows;
    }

    public void setRows(Long totalRows) {
	this.rows = totalRows;
    }

    public Long getCurrentPage() {
	return currentPage;
    }

    public void setCurrentPage(Long currentPage) {
	this.currentPage = currentPage;
    }

    public Long getTotalPage() {
	return totalPage;
    }

    public void setTotalPage(Long totalPage) {
	this.totalPage = totalPage;
    }

    public String getSearch() {
	return search;
    }

    public void setSearch(String search) {
	this.search = search;
    }

    public String getOrderBy() {
	return orderBy;
    }

    public void setOrderBy(String orderBy) {
	this.orderBy = orderBy;
    }

    /**
     * Donne le numéro de la 1ere ligne de la page courante
     */
    public void calculFirstLine() {
	this.firstLine = (getCurrentPage() - 1) * getRows();
    }

    /**
     * Teste si la page courante possède une page précédente.
     * 
     * @return false si c'est la première page, sinon true
     */
    public boolean hasPrevious() {
	if (getCurrentPage() == 1) {
	    return false;
	} else {
	    return true;
	}
    }

    /**
     * Passage à la page précédente, uniquement si elle existe.
     */
    public void getPreviousPage() {
	if (this.hasPrevious()) {
	    this.currentPage--;
	    this.firstLine -= getRows();
	}
    }

    /**
     * Passage à la page suivante
     * 
     * @param total
     */
    public void getNextPage(int total) {
	if (this.hasNext(total)) {
	    this.currentPage++;
	    this.firstLine += getRows();
	}
    }

    /**
     * Teste si la page courante est la dernière
     * 
     * @param total
     * @return
     */
    public boolean hasNext(int totalPage) {
	if (this.totalPage > getCurrentPage()) {
	    return true;
	}
	return false;
    }

    /**
     * Calcule le nombre total de pages nécéssaires pour afficher toutes les entrées
     * 
     * @param total le nombre total d'entrées dans la base de données
     * @return le nombre de pages nécéssaires pour toutes les afficher
     */
    public Long getTotalPages(Long total) {
	if (total % getRows() == 0) {
	    this.totalPage = total / getRows();
	    return this.totalPage;
	}
	this.totalPage = total / getRows() + 1;
	return this.totalPage;
    }

}
