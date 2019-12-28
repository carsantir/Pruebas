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
	<acme:form-textbox code="authenticated.provider.request.form.label.title" path="title"/>
	<acme:form-moment code="authenticated.provider.request.form.label.deadLine" path="deadLine"/>
	<acme:form-textbox code="authenticated.provider.request.form.label.text" path="text"/>
	<acme:form-money code="authenticated.provider.request.form.label.reward" path="reward"/>
	<acme:form-textbox code="authenticated.provider.request.form.label.ticker" path="ticker" placeholder="RXXXX-99999"/>
	
	<acme:form-checkbox code="authenticated.provider.request.form.label.confirmed" path="checkbox"/>
	
	<acme:form-submit code="authenticated.provider.request.form.button.create"
		action="/provider/request/create"/>
			
  	<acme:form-return code="authenticated.provider.request.form.button.return"/>
</acme:form>
