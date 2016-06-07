<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="jslt"%>
<!DOCTYPE html>
<html>
<head>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="jslt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Bootstrap -->
<link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet" media="screen">
<link href="${pageContext.request.contextPath}/css/font-awesome.css" rel="stylesheet" media="screen">
<link href="${pageContext.request.contextPath}/css/main.css" rel="stylesheet" media="screen">
<link href="${pageContext.request.contextPath}/css/jquery-ui.min.css" rel="stylesheet" media="screen">
</head>
<body>
    <header class="navbar navbar-inverse navbar-fixed-top">
        <div class="container">
            <a class="navbar-brand" href="${pageContext.request.contextPath}/Dashboard"> Application - Computer Database </a>
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
            <div class="row">
                <div class="col-xs-8 col-xs-offset-2 box">
                    <h1>Add Computer</h1>
                    <form action="${pageContext.request.contextPath}/AddComputer" onsubmit="return validateForm()" method="POST">
                        <fieldset>
                            <div class="form-group">
                                <label for="computerName">Computer name</label>
                                <input name="computerName" type="text" class="form-control" id="computerName" placeholder="Computer name">
                            </div>
                            <div class="form-group">
                                <label for="introduced">Introduced date</label>
                                <input name="introduced" type="text" class="form-control" id="introduced" placeholder="Introduced date">
                            </div>
                            <div class="form-group">
                                <label for="discontinued">Discontinued date</label>
                                <input name="discontinued" type="text" class="form-control" id="discontinued" placeholder="Discontinued date">
                            </div>
                            <div class="form-group">
                                <label for="companyId">Company</label>
                                <select name="companyId" class="form-control" id="companyId" >
                                		<option value="">Aucune</option>
                                	<jslt:forEach var="company" items="${companies}">
                                		<option value="${company.id}">${company.name}</option>
                                	</jslt:forEach>
                                    
                                </select>
                            </div>                  
                        </fieldset>
                        <div class="actions pull-right">
                            <input type="submit" value="Add" class="btn btn-primary">
                            or
                            <a href="${pageContext.request.contextPath}/Router?action=dashboard" class="btn btn-default">Cancel</a>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </section>
	<script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/jquery-ui.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/validateComputerForm.js"></script>
	<script>
		$(function() {
		  $( "#introduced" ).datepicker({ dateFormat: 'dd/mm/yy' }).val();
		  $( "#discontinued" ).datepicker({ dateFormat: 'dd/mm/yy' }).val();
		});
	</script>
</body>
</html>