<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html>
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
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
				<a href="addComputer?lang=fr">fr</a> | <a href="addComputer?lang=en">en</a>

			</div>
		</div>
	</header>

	<section id="main">
		<div class="container">
			<div class="row">
				<div class="col-xs-8 col-xs-offset-2 box">
					<h1>
						<spring:message code="button.addComputer" />
					</h1>
					<form action="addComputer" method="POST">
						<fieldset>
							<div class="form-group">
								<label for="computerName"><spring:message
										code="title.computer.name" /></label> <input type="text"
									class="form-control" id="computerName" name="computerName"
									value="<c:out value="${param.computerName}"/>"
									placeholder="<spring:message code="title.computer.name" />">
								<p class="error">${errors['computerName']}</p>
							</div>
							<div class="form-group">
								<label for="introduced"><spring:message
										code="title.computer.introduced" /></label> <input type="date"
									class="form-control" id="introduced" name="introduced"
									value="<c:out value="${param.introduced}"/>"
									placeholder="<spring:message code="title.computer.introduced" /> YYYY-MM-DD">
								<p class="error">${errors['dateIntroduced']}</p>
							</div>
							<div class="form-group">
								<label for="discontinued"><spring:message
										code="title.computer.discontinued" /></label> <input type="date"
									class="form-control" id="discontinued" name="discontinued"
									value="<c:out value="${param.discontinued}"/>"
									placeholder="<spring:message code="title.computer.discontinued" /> YYYY-MM-DD">
								<p class="error">${errors['dateDiscontinued']}</p>
							</div>
							<div class="form-group">
								<label for="companyId"><spring:message
										code="title.company" /></label> <select class="form-control"
									id="companyId" name="companyId">
									<option value="0" selected="selected">--</option>
									<c:forEach var="company" items="${ListCompanies}"
										varStatus="status">
										<option value="${company.id}"><c:out
												value="${company.name}" /></option>
									</c:forEach>
								</select>
							</div>
						</fieldset>
						<div class="actions pull-right">
							<input type="submit"
								value="<spring:message code="button.addComputer" />"
								class="btn btn-primary">
							<spring:message code="word.or" />
							<a href="listComputers" class="btn btn-default"><spring:message
									code="button.cancel" /></a>
						</div>
						<p class="${empty errors ? 'success' : 'error'}">${result}</p>
					</form>
				</div>
			</div>
		</div>
	</section>
</body>
</html>