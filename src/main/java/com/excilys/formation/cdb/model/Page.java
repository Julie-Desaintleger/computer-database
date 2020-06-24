package com.excilys.formation.cdb.model;

public class Page {
    private int firstLine;
    private int rows;
    private int currentPage;
    private int totalPage;

    public Page() {
	this.firstLine = 0;
	this.rows = 10;
	this.currentPage = 1;
    }

    public int getFirstLine() {
	return firstLine;
    }

    public void setFirstLine(int line) {
	this.firstLine = line;
    }

    public int getRows() {
	return rows;
    }

    public void setRows(int totalRows) {
	this.rows = totalRows;
    }

    public int getCurrentPage() {
	return currentPage;
    }

    public void setCurrentPage(int currentPage) {
	this.currentPage = currentPage;
    }

    public int getTotalPage() {
	return totalPage;
    }

    public void setTotalPage(int totalPage) {
	this.totalPage = totalPage;
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
     * @param nbTotal le nombre total d'entrées dans la base de données
     * @return le nombre de pages nécéssaires pour toutes les afficher
     */
    public int getTotalPages(int nbTotal) {
	if (nbTotal % getRows() == 0) {
	    this.totalPage = nbTotal / getRows();
	    return this.totalPage;
	}
	this.totalPage = nbTotal / getRows() + 1;
	return this.totalPage;
    }

}
