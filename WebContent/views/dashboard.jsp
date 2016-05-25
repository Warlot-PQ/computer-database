<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="jslt"%>
<%-- <%@ taglib uri="http://excilys.com/computer-database/jsp/myLib" prefix="myLib" %> --%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="myLib" %>

<%@ page import="com.excilys.beans.*"%>

<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>

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
	<% 
		boolean loadError = (boolean) request.getAttribute("loadError"); 
		List<ComputerDTO> computers = (ArrayList<ComputerDTO>) request.getAttribute("computer");
		int currentPage = (int) request.getAttribute("currentPage"); 
		int currentLimit = (int) request.getAttribute("currentLimit"); 
		int currentComputersFrom = (int) request.getAttribute("currentComputersFrom");
		int currentComputersTo = (int) request.getAttribute("currentComputersTo");
		int totalPages =  (int) request.getAttribute("totalPages"); 
		int totalComputers = (int) request.getAttribute("totalComputers");
	%>
	<header class="navbar navbar-inverse navbar-fixed-top">
		<div class="container">
			<a class="navbar-brand" href="${pageContext.request.contextPath}/Router"> Application -
				Computer Database </a>
		</div>
	</header>

	<section id="main">
		<div class="container">
			<h1 id="homeTitle">${currentComputersFrom}-${currentComputersTo} / ${totalComputers} Computers found</h1>
			<div id="actions" class="form-horizontal">
				<div class="pull-left">
					<form id="searchForm" action="#" method="GET" class="form-inline">

						<input type="search" id="searchbox" name="search"
							class="form-control" placeholder="Search name" /> <input
							type="submit" id="searchsubmit" value="Filter by name"
							class="btn btn-primary" />
					</form>
				</div>
				<div class="pull-right">
					<a class="btn btn-success" id="addComputer" href="${pageContext.request.contextPath}/Router?action=addComputer">Add
						Computer</a> <a class="btn btn-default" id="editComputer" href="#"
						onclick="$.fn.toggleEditMode();">Edit</a>
				</div>
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
						<th>Computer name</th>
						<th>Introduced date</th>
						<!-- Table header for Discontinued Date -->
						<th>Discontinued date</th>
						<!-- Table header for Company -->
						<th>Company</th>

					</tr>
				</thead>
				<!-- Browse attribute computers -->
				<tbody id="results">
					<jslt:forEach var="computer" items="${computers}">
						<tr>
							<td class="editMode"><input type="checkbox" name="cb"
								class="cb" value="0"></td>
							<td><a href="editComputer.html" onclick="">${computer.name}</a>
							</td>
							<td>${computer.getIntroducedStr()}</td>
							<td>${computer.getDiscontinuedStr()}</td>
							<td>${computer.getCompanyName()}</td>

						</tr>
					</jslt:forEach>
				</tbody>
			</table>
		</div>
	</section>

	<footer class="navbar-fixed-bottom">
		<div class="container text-center">
				
			<myLib:pagination start="1" current="${currentPage}" end="${totalPages}" limit="${currentLimit}"/>
		
			<div class="btn-group btn-group-sm pull-right" role="group">
			
				<myLib:link page="1" limit="10" classes="btn btn-default">
					10
				</myLib:link>
				<myLib:link page="1" limit="50" classes="btn btn-default">
					50
				</myLib:link>
				<myLib:link page="1" limit="100" classes="btn btn-default">
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