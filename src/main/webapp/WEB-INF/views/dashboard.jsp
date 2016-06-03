<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="jslt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="myLib" %>

<c:set var="computers" value="${page.getItems()}" />
<c:set var="currentComputersFrom" value="${page.getFirstItemNumber()}" />
<c:set var="currentComputersTo" value="${page.getLastItemNumber()}" />
<c:set var="totalComputers" value="${page.getTotalItems()}" />
<c:set var="currentPage" value="${page.currentPage}" />
<c:set var="currentLimit" value="${page.getItemsByPage()}" />
<c:set var="currentOrderBy" value="${orderBy}" />
<c:set var="currentOrderAlphaNumerical" value="${orderAlphaNumerical}" />
<c:set var="totalPages" value="${page.getTotalPages()}" />

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
	${current}-${end}
			<a class="navbar-brand" href="Dashboard"> Application -
				Computer Database </a>
		</div>
	</header>

	<div id="errorMsg" class="alert alert-warning text-center" style="${validation.isStateDisplayed() eq 'true' ? 'display: block;' : 'display: none;'}">
		<c:if test="${validation.getMessages().isEmpty() ne true}">
	  		<jslt:forEach var="message" items="${validation.getMessages()}">
	  			<p>${message}</p>
	  		</jslt:forEach>
		</c:if>	  
	</div>

	<section id="main">
		<div class="container">
			<h1 id="homeTitle">${currentComputersFrom}-${currentComputersTo} / ${totalComputers} Computers found</h1>
			<div id="actions" class="form-horizontal">
				<div class="pull-left">
					<form id="searchForm" action="Dashboard"onsubmit="return validateSearch()"  method="GET" class="form-inline">

						<input type="search" id="searchbox" name="search"
							class="form-control" placeholder="Search name" /> <input
							type="submit" id="searchsubmit" value="Filter by name"
							class="btn btn-primary" />
					</form>
				</div>
				<div class="pull-right">
					<a class="btn btn-success" id="addComputer" href="AddComputer">Add
						Computer</a> <a class="btn btn-default" id="editComputer" href="#"
						onclick="$.fn.toggleEditMode();">Edit</a>
				</div>
			</div>
		</div>

		<form id="deleteForm" action="Dashboard?page=${currentPage}&limit=${currentLimit}" method="POST">
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
							Computer name
							<myLib:link page="${currentPage}" limit="${currentLimit}" search="${computerSearch}" orderBy="name" orderAlphaNum="true" classes="glyphicon glyphicon-chevron-up"></myLib:link>
							<myLib:link page="${currentPage}" limit="${currentLimit}" search="${computerSearch}" orderBy="name" orderAlphaNum="false" classes="glyphicon glyphicon-chevron-down"></myLib:link>
						</th><th>
							Introduced date
							<myLib:link page="${currentPage}" limit="${currentLimit}" search="${computerSearch}" orderBy="introduced" orderAlphaNum="true" classes="glyphicon glyphicon-chevron-up"></myLib:link>
							<myLib:link page="${currentPage}" limit="${currentLimit}" search="${computerSearch}" orderBy="introduced" orderAlphaNum="false" classes="glyphicon glyphicon-chevron-down"></myLib:link>
						</th>
						<!-- Table header for Discontinued Date -->
						<th>
							Discontinued date
							<myLib:link page="${currentPage}" limit="${currentLimit}" search="${computerSearch}" orderBy="discontinued" orderAlphaNum="true" classes="glyphicon glyphicon-chevron-up"></myLib:link>
							<myLib:link page="${currentPage}" limit="${currentLimit}" search="${computerSearch}" orderBy="discontinued" orderAlphaNum="false" classes="glyphicon glyphicon-chevron-down"></myLib:link>
						</th>
						<!-- Table header for Company -->
						<th>
							Company
							<myLib:link page="${currentPage}" limit="${currentLimit}" search="${computerSearch}" orderBy="company" orderAlphaNum="true" classes="glyphicon glyphicon-chevron-up"></myLib:link>
							<myLib:link page="${currentPage}" limit="${currentLimit}" search="${computerSearch}" orderBy="company" orderAlphaNum="false" classes="glyphicon glyphicon-chevron-down"></myLib:link>
						</th>
					</tr>
				</thead>
				<!-- Browse attribute computers -->
				<tbody id="results">
					<jslt:forEach var="computer" items="${computers}">
						<tr>
							<td class="editMode"><input type="checkbox" name="cb"
								class="cb" value="${computer.id}"></td>
							<td><a href="EditComputer?id=${computer.id}" onclick="">${computer.name}</a>
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

			<myLib:pagination start="1" current="${currentPage}" end="${totalPages}" limit="${currentLimit}" search="${computerSearch}" orderBy="${currentOrderBy}" orderAlphaNum="${currentOrderAlphaNumerical}" />
		
			<div class="btn-group btn-group-sm pull-right" role="group">
			
				<myLib:link page="1" limit="10" search="${computerSearch}" orderBy="${currentOrderBy}" orderAlphaNum="${currentOrderAlphaNumerical}" classes="btn btn-default">
					10
				</myLib:link>
				<myLib:link page="1" limit="50" search="${computerSearch}" orderBy="${currentOrderBy}" orderAlphaNum="${currentOrderAlphaNumerical}" classes="btn btn-default">
					50 
				</myLib:link>
				<myLib:link page="1" limit="100" search="${computerSearch}" orderBy="${currentOrderBy}" orderAlphaNum="${currentOrderAlphaNumerical}" classes="btn btn-default">
					100
				</myLib:link>
				
			</div>
		</div>
	</footer>
	<script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/dashboard.js"></script>

</body>
</html>