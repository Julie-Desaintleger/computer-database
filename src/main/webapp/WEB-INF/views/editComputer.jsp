<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
		</div>
	</header>
	<section id="main">
		<div class="container">
			<div class="row">
				<div class="col-xs-8 col-xs-offset-2 box">
					<div class="label label-default pull-right">id: ${computer.id}</div>
					<h1>Edit Computer</h1>

					<form action="editComputer" method="POST">
						<input type="hidden" value="${computer.id}" id="id" name = "id"/>
						<!-- TODO: Change this value with the computer id -->
						<fieldset>
							<div class="form-group">
								<label for="computerName">Computer name</label>
								<input type="text" class="form-control" id="computerName" name = "computerName" value ="${computer.name}" placeholder="Computer name">
								<p class="error">${errors['computerName']}</p>
							</div>
							<div class="form-group">
								<input type="date" class="form-control" id="introduced" name = "introduced" value = "${computer.introduced}" placeholder="Introduced date YYYY-MM-DD">
								<p class="error">${errors['dateIntroduced']}</p>

							</div>
							<div class="form-group">
								<input type="date" class="form-control" id="discontinued" name = "discontinued" value ="${computer.discontinued}" placeholder="Discontinued date YYYY-MM-DD">
								<p class="error">${errors['dateDiscontinued']}</p>

							</div>
							<div class="form-group">
								<label for="companyId">Company</label>
                                <select class="form-control" id="companyId" name="companyId" >
                                    <option value="0" selected="selected">--</option>
                                     	<c:forEach var="company" items="${ListCompanies}" varStatus="status">
                                			<option value="${company.id}"><c:out value="${company.name}"/></option>
                                		</c:forEach>
                                </select>
							</div>
						</fieldset>
						<div class="actions pull-right">
							<input type="submit" value="Edit" class="btn btn-primary">
							or <a href="listComputers" class="btn btn-default">Cancel</a>
						</div>
						<p class="${empty errors ? 'success' : 'error'}">${result}</p>
					</form>
				</div>
			</div>
		</div>
	</section>
</body>
</html>