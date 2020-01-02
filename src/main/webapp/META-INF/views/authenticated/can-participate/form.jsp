<%--
- form.jsp
-
- Copyright (c) 2019 Rafael Corchuelo.
-
- In keeping with the traditional purpose of furthering education and research, it is
- the policy of the copyright owner to permit non-commercial use and redistribution of
- this software. It has been tested carefully, but it is not guaranteed for any particular
- purposes.  The copyright owner does not offer any warranties or representations, nor do
- they accept any liabilities with respect to them.
--%>

<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<acme:form>	
	<jstl:if test="${command == 'create' }">			
		<acme:form-hidden path="mtId"/>
			
		<acme:form-select code="authenticated.add-user.form.select" path="users">
			<jstl:forEach var="item" items="${users}">
				<acme:form-option code="${item.userAccount.username}" value="${item.id}"/>
			</jstl:forEach>
		</acme:form-select>
		
		<acme:form-errors path="noesnuevo"/>
		<acme:form-submit code="authenticated.can-participate.form.button.add"
			action="/authenticated/can-participate/create" />
	</jstl:if>
	
	<jstl:if test="${command == 'show'}">
		<acme:form-textbox code="authenticated.can-participate.form.label.userAccount.username" path="authenticated.userAccount.username" readonly="true"/>
		<acme:form-textbox code="authenticated.can-participate.form.label.messageThread" path="messageThread.title" readonly="true"/>
   	</jstl:if>
   	
   	<jstl:if test="${command == 'show' && isNotCreator}">
	   	<acme:form-submit code="authenticated.can-participate.form.button.delete"
   			action="/authenticated/can-participate/delete"/>
   	</jstl:if>
   	
   	<jstl:if test="${command == 'delete'}">
	   	<acme:form-textbox code="authenticated.can-participate.form.label.userAccount.username" path="authenticated.userAccount.username" readonly="true"/>
		<acme:form-textbox code="authenticated.can-participate.form.label.messageThread" path="messageThread.title" readonly="true"/>
   		<acme:form-submit code="authenticated.can-participate.form.button.delete"
   			action="/authenticated/can-participate/delete" />
   	</jstl:if>
   	
   	
	<acme:form-return code="authenticated.can-participate.form.button.return"/>
</acme:form>
