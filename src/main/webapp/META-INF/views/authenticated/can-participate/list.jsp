
<%--
- list.jsp
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

<acme:list>
	<acme:list-column code="authenticated.can-participate.list.label.userAccount.username" path="authenticated.userAccount.username" width="20%" />
	<acme:list-column code="authenticated.can-participate.list.label.messageThread" path="messageThread.title" width="40%"/>			
</acme:list>

<acme:form readonly="true">
	<acme:form-return code="authenticated.can-participate.list.button.return"/>
</acme:form>