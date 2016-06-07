<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ tag body-content="empty" %>  
<%@ attribute name="start" rtexprvalue="true" required="true" type="java.lang.Integer" %> 
<%@ attribute name="current" rtexprvalue="true" required="true" type="java.lang.Integer" %>
<%@ attribute name="end" rtexprvalue="true" required="true" type="java.lang.Integer" %>
<%@ attribute name="limit" rtexprvalue="true" required="true" type="java.lang.String" %>
<%@ attribute name="search" rtexprvalue="true" required="false" %>
<%@ attribute name="orderBy" rtexprvalue="true" required="false" %>
<%@ attribute name="orderAlphaNum" rtexprvalue="true" required="false" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="myLib" %>

<ul class="pagination">
	<c:if test="${current ne start}">
		<li><myLib:link classes="btn btn-default" page="${start}" limit="${limit}" search="${search}" orderBy="${orderBy}" orderAlphaNum="${orderAlphaNum}" att="aria-label=\"Next\"">
			First
		</myLib:link></li>
		<li><myLib:link classes="btn btn-default" page="${current - 1}" limit="${limit}" orderBy="${orderBy}" orderAlphaNum="${orderAlphaNum}" search="${search}" att="aria-label=\"Previous\"">
			<span aria-hidden="true">&laquo;</span>
		</myLib:link></li>
	</c:if>
	<c:set var="startLoop" value="${current - 5}" />
	<c:if test="${current - 5 lt 1}">
		<c:set var="startLoop" value="1" />
	</c:if>
	<c:set var="endLoop" value="${current + 5}" />
	<c:if test="${current + 5 gt end}">
		<c:set var="endLoop" value="${end}" />
	</c:if>
	<c:forEach begin="${startLoop}" end="${endLoop}" step="1" var="loop_i">
		<c:if test="${loop_i gt 0 and loop_i lt end+1}"> 
			<c:if test="${current ne loop_i}">
				<li><myLib:link classes="btn btn-default" page="${loop_i}" limit="${limit}" orderBy="${orderBy}" orderAlphaNum="${orderAlphaNum}" search="${search}">
					${loop_i}
				</myLib:link></li>
			</c:if>
			<c:if test="${current eq loop_i}">			
				<li><myLib:link classes="btn btn-primary active" page="${loop_i}" limit="${limit}" search="${search}" orderBy="${orderBy}" orderAlphaNum="${orderAlphaNum}" enable="false">
					${loop_i}
				</myLib:link></li>	
			</c:if> 
		</c:if>
	</c:forEach>
	<c:if test="${current ne end}">
		<li><myLib:link classes="btn btn-default" page="${current + 1}" limit="${limit}" search="${search}" orderBy="${orderBy}" orderAlphaNum="${orderAlphaNum}" att="aria-label=\"Next\"">
			<span aria-hidden="true">&raquo;</span>
		</myLib:link></li>
		<li><myLib:link classes="btn btn-default" page="${end}" limit="${limit}" search="${search}" orderBy="${orderBy}" orderAlphaNum="${orderAlphaNum}" att="aria-label=\"Next\"">
			Last
		</myLib:link></li>
	</c:if>
</ul>