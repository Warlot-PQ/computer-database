<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="jslt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="myLib" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<spring:message code="title.dashboard" var="i18nDashboardTitle" />
<spring:message code="computer.filterBy.name" var="i18nFilterByName" />
<spring:message code="computer.name" var="i18nComputerName" />
<spring:message code="computer.introduced" var="i18nComputerIntroduced" />
<spring:message code="computer.discontinued" var="i18nComputerDiscontinued" />
<spring:message code="computer.company" var="i18nComputerCompany" />
<spring:message code="button.add" var="i18nButtonAdd" />
<spring:message code="button.edit" var="i18nButtonEdit" />
<spring:message code="computer.placehorlder.search.name" var="i18nPlaceHorlderSearchName" />

<c:set var="computers" value="${page.getItems()}" />
<c:set var="currentComputersFrom" value="${page.getFirstItemNumber()}" />
<c:set var="currentComputersTo" value="${page.getLastItemNumber()}" />
<c:set var="totalComputers" value="${page.getTotalItems()}" />
<c:set var="totalPages" value="${page.getTotalPages()}" />
<c:set var="currentPage" value="${requestModel.page}" />
<c:set var="currentLimit" value="${requestModel.limit}" />
<c:set var="currentOrderBy" value="${requestModel.orderBy}" />
<c:set var="currentOrderAlphaNumerical" value="${requestModel.orderAlphaNum}" />
<c:set var="nameSearched" value="${requestModel.search}" />
<c:set var="langMsg" value="${langMsg}" />

<!DOCTYPE html>
<html>
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta charset="utf-8">
<!-- Bootstrap -->
<link href="${pageContext.request.contextPath}/css/bootstrap.min.css"
	rel="stylesheet" media="screen">
<link href="${pageContext.request.contextPath}/css/font-awesome.css"
	rel="stylesheet" media="screen">
<link href="${pageContext.request.contextPath}/css/main.css"
	rel="stylesheet" media="screen">
</head>
<body>
	<header class="navbar navbar-inverse navbar-fixed-top">
		<div class="container">
			<a class="navbar-brand" href="dashboard"> Application -
				Computer Database </a>
			<div class="navbar-brand pull-right">
				<form:form name="logoutForm" action="${pageContext.request.contextPath}/logout" method="POST">
   					 <span class="display-inline" onclick="document.logoutForm.submit()">Logout</span>
				</form:form>
			</div>
		</div>
	</header>
	
	 <div id="errorMsg" class="alert alert-warning text-center" style="${not empty deleteStatus ? 'display: block;' : 'display: none;'}">
		<p>
			${deleteStatus}
		</p>
	</div>

	<section id="main">
		<div class="container">
			<h1 id="homeTitle">${currentComputersFrom}-${currentComputersTo} / ${totalComputers} ${i18nDashboardTitle}</h1>
			<div id="actions" class="form-horizontal">
				<div class="pull-left">
					<form id="searchForm" action="dashboard" onsubmit="return validateSearch()"  method="GET" class="form-inline">

						<input type="search" id="searchbox" name="search"
							class="form-control" placeholder="${i18nPlaceHorlderSearchName}" value="${nameSearched}" /> <input
							type="submit" id="searchsubmit" value="${i18nFilterByName}"
							class="btn btn-primary" />
					</form>
				</div>
				<security:authorize access="hasRole('ROLE_ADMIN')">
				<div class="pull-right">
					<div class="row">
						<a class="btn btn-success" id="addComputer" href="add_computer">${i18nButtonAdd}</a>
						<a class="btn btn-default" id="editComputer" href="#"
							onclick="$.fn.toggleEditMode();">${i18nButtonEdit}</a>
					</div>
					<div class="row">
						<button type="button" class="btn btn-primary btn-lg" data-toggle="modal" data-target="#import_export_modal">
							 Import/Export
						</button>
					</div>
				</div>
				</security:authorize>
			</div>
		</div>

		<form id="deleteForm" action="dashboard?page=${currentPage}&limit=${currentLimit}&search=${nameSearched}" method="POST">
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
						<th>
							${i18nComputerName}
							<myLib:link page="${currentPage}" limit="${currentLimit}" search="${nameSearched}" orderBy="name" orderAlphaNum="true" classes="glyphicon glyphicon-chevron-up"></myLib:link>
							<myLib:link page="${currentPage}" limit="${currentLimit}" search="${nameSearched}" orderBy="name" orderAlphaNum="false" classes="glyphicon glyphicon-chevron-down"></myLib:link>
						</th><th>
							${i18nComputerIntroduced}
							<myLib:link page="${currentPage}" limit="${currentLimit}" search="${nameSearched}" orderBy="introduced" orderAlphaNum="true" classes="glyphicon glyphicon-chevron-up"></myLib:link>
							<myLib:link page="${currentPage}" limit="${currentLimit}" search="${nameSearched}" orderBy="introduced" orderAlphaNum="false" classes="glyphicon glyphicon-chevron-down"></myLib:link>
						</th>
						<!-- Table header for Discontinued Date -->
						<th>
							${i18nComputerDiscontinued}
							<myLib:link page="${currentPage}" limit="${currentLimit}" search="${nameSearched}" orderBy="discontinued" orderAlphaNum="true" classes="glyphicon glyphicon-chevron-up"></myLib:link>
							<myLib:link page="${currentPage}" limit="${currentLimit}" search="${nameSearched}" orderBy="discontinued" orderAlphaNum="false" classes="glyphicon glyphicon-chevron-down"></myLib:link>
						</th>
						<!-- Table header for Company -->
						<th>
							${i18nComputerCompany}
							<myLib:link page="${currentPage}" limit="${currentLimit}" search="${nameSearched}" orderBy="company" orderAlphaNum="true" classes="glyphicon glyphicon-chevron-up"></myLib:link>
							<myLib:link page="${currentPage}" limit="${currentLimit}" search="${nameSearched}" orderBy="company" orderAlphaNum="false" classes="glyphicon glyphicon-chevron-down"></myLib:link>
						</th>
					</tr>
				</thead>
				<!-- Browse attribute computers -->
				<tbody id="results">
					<jslt:forEach var="computer" items="${computers}">
						<tr>
							<td class="editMode"><input type="checkbox" name="cb"
								class="cb" value="${computer.id}"></td>
							<td><a href="edit_computer?id=${computer.id}" onclick="">${computer.name}</a>
							</td>
							<td>${computer.getIntroduced()}</td>
							<td>${computer.getDiscontinued()}</td>
							<td>${computer.getCompanyName()}</td>

						</tr>
					</jslt:forEach>
				</tbody>
			</table>
		</div>
	</section>

	<footer class="navbar-fixed-bottom">
		<div class="container text-center">

			<myLib:pagination start="1" current="${currentPage}" end="${totalPages}" limit="${currentLimit}" search="${nameSearched}" orderBy="${currentOrderBy}" orderAlphaNum="${currentOrderAlphaNumerical}" />
	
			<div class="btn-group btn-group-sm pull-right" role="group">
			
				<myLib:link page="1" limit="10" search="${nameSearched}" orderBy="${currentOrderBy}" orderAlphaNum="${currentOrderAlphaNumerical}" classes="btn btn-default">
					10
				</myLib:link>
				<myLib:link page="1" limit="50" search="${nameSearched}" orderBy="${currentOrderBy}" orderAlphaNum="${currentOrderAlphaNumerical}" classes="btn btn-default">
					50 
				</myLib:link>
				<myLib:link page="1" limit="100" search="${nameSearched}" orderBy="${currentOrderBy}" orderAlphaNum="${currentOrderAlphaNumerical}" classes="btn btn-default">
					100
				</myLib:link>
				
			</div>
		</div>
	</footer>
	
	<jsp:include page="components/ImportExport.jsp">
		<jsp:param name="currentPage" value="${currentPage}" />
		<jsp:param name="currentLimit" value="12" />
		<jsp:param name="nameSearched" value="${nameSearched}" />
	</jsp:include>
	
	<script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/dashboard.js"></script>
	
	<script type="text/javascript">
		$.messages = ${langMsg};
	</script>

</body>
</html>