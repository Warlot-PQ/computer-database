<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
<title>CDB login</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<!-- Bootstrap -->
<link href="${pageContext.request.contextPath}/css/bootstrap.min.css"
	rel="stylesheet" media="screen">
<link href="${pageContext.request.contextPath}/css/signin.css"
	rel="stylesheet" media="screen">
</head>
<body>
<div class="container">

		<form class="form-signin" role="form" action="${pageContext.request.contextPath}/login" method="post">
			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
			<h2 class="form-signin-heading">Please sign in</h2>
			  
			<label for="inputEmail" class="sr-only">Username</label>
			<input type="text" name="username" id="inputUsername" class="form-control" placeholder="Username" required autofocus>
			  
			<label for="inputPassword" class="sr-only">Password</label>
			<input type="password" name="password" id="inputPassword" class="form-control" placeholder="Password" required>
			  
			<div class="checkbox">
				<label>
					<input type="checkbox" name="remember-me" value="remember-me"> Remember me
				</label>
			</div>
			  
			<button class="btn btn-lg btn-primary btn-block" type="submit">Sign in</button>
		</form>

      	<c:if test="${error.isPresent() == true}">
			<p>The email or password you have entered is invalid, try again.</p>
		</c:if>
	</div>
</body>
</html>