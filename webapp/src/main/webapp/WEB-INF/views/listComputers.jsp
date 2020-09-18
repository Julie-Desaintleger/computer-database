<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html>
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta charset="utf-8">
<!-- Bootstrap -->
<link href="./static/css/bootstrap.min.css" rel="stylesheet"
	media="screen">
<link href="./static/css/font-awesome.css" rel="stylesheet"
	media="screen">
<link href="./static/css/main.css" rel="stylesheet" media="screen">
</head>
<body>
	<header class="navbar navbar-inverse navbar-fixed-top">
		<div class="container">
			<a class="navbar-brand" href="listComputers"> Application -
				Computer Database </a>
			<div class="pull-right align-self-center">
				<a
					href="listComputers?pageNumber=${currentPage}&search=${search}&order=${order}&lang=fr">fr</a>
				| <a
					href="listComputers?pageNumber=${currentPage}&search=${search}&order=${order}&lang=en">en</a>

			</div>
		</div>
	</header>

	<section id="main">
		<h1 id="homeTitle">
			<c:out value="${nbComputers}" />
			<spring:message code="search.number" />
		</h1>
		<div id="actions" class="form-horizontal">
			<div class="pull-left">
				<form id="searchForm" action="#" method="GET" class="form-inline">
					<input type="search" id="searchbox" name="search"
						class="form-control" value="<c:out value="${param.search}"/>"
						placeholder='<spring:message code="button.search" />' /> <input
						type="submit" id="searchsubmit"
						value="<spring:message code="button.search" />"
						class="btn btn-primary" />
				</form>
			</div>
			<div class="pull-right">
				<a class="btn btn-success" id="addComputer" href="addComputer"><spring:message
						code="button.addComputer" /></a> <a class="btn btn-default"
					id="editComputer" href="#" onclick="$.fn.toggleEditMode();"><spring:message
						code="button.edit" /></a>
			</div>
		</div>

		<form id="deleteForm" action="#" method="POST">
			<input type="hidden" name="selection" value="">
		</form>
		<div class="container" style="margin-top: 10px;">

			<table class="table table-striped table-bordered">
				<thead>
					<tr>
						<!-- Variable declarations for passing labels as parameters -->
						<!-- Table header for Computer Name -->

						<th class="editMode" style="width: 60px; height: 22px;"><input
							type="checkbox" id="selectall" /> <span
							style="vertical-align: top;"> - <a href="#"
								id="deleteSelected" onclick="$.fn.deleteSelected();"> <i
									class="fa fa-trash-o fa-lg"></i>
							</a>
						</span></th>
						<!-- Variable declarations for passing labels as parameters -->
						<th><spring:message code="title.computer.id" /></th>
						<th><spring:message code="title.computer.name" /><a
							href="listComputers?order=computer.name ASC&search=${search}"><i
								class="glyphicon glyphicon-sort-by-alphabet"></i></a> <a
							href="listComputers?order=computer.name DESC&search=${search}"><i
								class="glyphicon glyphicon-sort-by-alphabet-alt"></i></a></th>

						<th><spring:message code="title.computer.introduced" /><a
							href="listComputers?order=introduced ASC&search=${search}"><i
								class="glyphicon glyphicon-sort-by-attributes"></i></a> <a
							href="listComputers?order=introduced DESC&search=${search}"><i
								class="glyphicon glyphicon-sort-by-attributes-alt"></i></a></th>
						<!-- Table header for Discontinued Date -->
						<th><spring:message code="title.computer.discontinued" /><a
							href="listComputers?order=discontinued ASC&search=${search}"><i
								class="glyphicon glyphicon-sort-by-attributes"></i></a> <a
							href="listComputers?order=discontinued DESC&search=${search}"><i
								class="glyphicon glyphicon-sort-by-attributes-alt"></i></a></th>
						<!-- Table header for Company -->
						<th><spring:message code="title.company" /><a
							href="listComputers?order=company.name ASC&search=${search}"><i
								class="glyphicon glyphicon-sort-by-alphabet"></i></a> <a
							href="listComputers?order=company.name DESC&search=${search}"><i
								class="glyphicon glyphicon-sort-by-alphabet-alt"></i></a></th>

					</tr>
				</thead>
				<!-- Browse attribute computers -->
				<tbody id="results">
					<c:forEach var="computer" items="${ListComputers}"
						varStatus="status">
						<tr>
							<td class="editMode"><input type="checkbox" name="cb"
								class="cb" value="${computer.id}"></td>
							<td><c:out value="${computer.id}" /></td>
							<td><a href="editComputer?id=${computer.id}" onclick=""><c:out
										value="${computer.name}" /></a></td>
							<td><c:out value="${computer.introduced}" /></td>
							<td><c:out value="${computer.discontinued}" /></td>
							<td><c:out value="${computer.company.name}" /></td>

						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</section>

	<footer class="navbar-fixed-bottom">
		<div class="container text-center">
			<ul class="pagination">
				<%--For displaying Previous link except for the 1st page --%>
				<li><c:if test="${currentPage != 1}">
						<button
							onclick="window.location.href='listComputers?pageNumber=${currentPage - 1}&search=${search}&order=${order}'"
							aria-label="Previous">
							<span aria-hidden="true">&laquo;</span>
						</button>
					</c:if> <c:forEach begin="0" end="10" var="i">
						<c:if test="${currentPage+i<=totalPages}">
							<c:choose>
								<c:when test="${i==0}">
									<button type="button" class="btn active"
										onclick="window.location.href='listComputers?pageNumber=${currentPage}&search=${search}&order=${order}'">
										<c:out value="${currentPage}"></c:out>
									</button>
								</c:when>
								<c:otherwise>
									<button type="button" class="btn btn-default"
										onclick="window.location.href='listComputers?pageNumber=${currentPage+i}&search=${search}&order=${order}'">
										<c:out value="${currentPage+i}"></c:out>
									</button>
								</c:otherwise>
							</c:choose>
						</c:if>
					</c:forEach> <%--For displaying Next link except for the last page --%> <c:if
						test="${currentPage != totalPages}">
						<button
							onclick="window.location.href='listComputers?pageNumber=${currentPage + 1}&search=${search}&order=${order}'"
							aria-label="Next">
							<span aria-hidden="true">&raquo;</span>
						</button>
					</c:if></li>
			</ul>

			<div class="btn-group btn-group-sm pull-right" role="group">
				<c:choose>
					<c:when test="${ lineNumber eq 10 }">
						<button type="button" class="btn active"
							onclick="window.location.href='listComputers?lineNumber=10&search=${search}&order=${order}'">10</button>
					</c:when>
					<c:otherwise>
						<button type="button" class="btn btn-default"
							onclick="window.location.href='listComputers?lineNumber=10&search=${search}&order=${order}'">10</button>
					</c:otherwise>
				</c:choose>
				<c:choose>
					<c:when test="${ lineNumber eq 50 }">
						<button type="button" class="btn active"
							onclick="window.location.href='listComputers?lineNumber=50&search=${search}&order=${order}'">50</button>
					</c:when>
					<c:otherwise>
						<button type="button" class="btn btn-default"
							onclick="window.location.href='listComputers?lineNumber=50&search=${search}&order=${order}'">50</button>
					</c:otherwise>
				</c:choose>
				<c:choose>
					<c:when test="${ lineNumber eq 100 }">
						<button type="button" class="btn active"
							onclick="window.location.href='listComputers?lineNumber=100&search=${search}&order=${order}'">100</button>
					</c:when>
					<c:otherwise>
						<button type="button" class="btn btn-default"
							onclick="window.location.href='listComputers?lineNumber=100&search=${search}&order=${order}'">100</button>
					</c:otherwise>
				</c:choose>
			</div>
		</div>
	</footer>
	<script src="./static/js/jquery.min.js"></script>
	<script src="./static/js/bootstrap.min.js"></script>
	<script src="./static/js/dashboard.js"></script>

</body>
</html>