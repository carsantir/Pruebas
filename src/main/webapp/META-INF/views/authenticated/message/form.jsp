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
	<acme:form-textbox code="authenticated.message.form.label.title" path="title" />		
	<acme:form-textbox code="authenticated.message.form.label.tags" path="tags" />
	<acme:form-textarea code="authenticated.message.form.label.body" path="body" />
	<jstl:if test="${command != 'create'}">
		<acme:form-moment
			code="authenticated.message.form.label.moment"
			path="moment"
			readonly="true"/>
	
		<acme:form-textbox code="authenticated.message.form.label.userAccount.username" path="authenticated.userAccount.username" />	
		<acme:form-textbox code="authenticated.message.form.label.messageThread.title" path="messageThread.title"/>
	</jstl:if>
	
	<jstl:if test="${command == 'create'}">
		<acme:form-hidden path="messageThread.id" />
		<acme:form-checkbox code="authenticated.message.form.label.checkbox" path="checkbox"/>
	</jstl:if>
	
	<acme:form-submit test="${command == 'create'}"
		code="authenticated.message.form.button.create"
		action="/authenticated/message/create"/>
		
	<acme:form-return code="authenticated.message.form.button.return"/>
</acme:form>
