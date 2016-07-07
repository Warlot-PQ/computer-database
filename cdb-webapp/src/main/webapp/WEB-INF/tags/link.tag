<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ tag body-content="scriptless" %> 
<%@ attribute name="page" rtexprvalue="true" required="true" type="java.lang.Integer" %> 
<%@ attribute name="limit" rtexprvalue="true" required="true" type="java.lang.Integer" %> 
<%@ attribute name="classes" rtexprvalue="true" required="false" %> 
<%@ attribute name="att" rtexprvalue="true" required="false" %> 
<%-- enable or disable hyperlink --%>
<%@ attribute name="enable" rtexprvalue="true" required="false" %>
<!-- Search parameter in URL -->
<%@ attribute name="search" rtexprvalue="true" required="false" %> 
<%@ attribute name="orderBy" rtexprvalue="true" required="false" %>
<%@ attribute name="orderAlphaNum" rtexprvalue="true" required="false" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%-- Body tag content --%>
<jsp:doBody var="bodyRes" />
<%-- Convert special characters to character entities --%>
<c:set var="escapedBody" value="${fn:escapeXml(bodyRes)}" />
<c:set var="projectPath" value="${pageContext.request.contextPath}" />
<c:set var="href" value="#" />

<c:if test="${not empty search}">
	<c:set var="searchUrlFragment" value="search=${search}&" />
</c:if>
<c:if test="${not empty orderBy}">
	<c:set var="orderByUrlFragment" value="orderBy=${orderBy}&" />
</c:if><c:if test="${not empty orderBy}">
	<c:set var="orderAlphaNumUrlFragment" value="orderAlphaNum=${orderAlphaNum}&" />
</c:if>
<c:if test="${enable ne false}">
	<c:set var="href" value="${projectPath}/dashboard?${searchUrlFragment}${orderByUrlFragment}${orderAlphaNumUrlFragment}page=${page}&limit=${limit}" />
</c:if>
<a class="${classes}" href="${href}" ${att}>
	${bodyRes}	
</a>


