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
	<acme:form-url code="sponsor.non-commercial-banner.form.label.pictureUrl" path="pictureUrl" />
	<acme:form-url code="sponsor.non-commercial-banner.form.label.targetUrl" path="targetUrl" />
	<acme:form-textbox code="sponsor.non-commercial-banner.form.label.jingle" path="jingle" />
	<acme:form-textbox code="sponsor.non-commercial-banner.form.label.slogan" path="slogan" />		
   
   	<jstl:if test="${command != 'create'}">	
	<acme:form-textbox code="sponsor.non-commercial-banner.form.label.sponsor.userAccount.username" path="sponsor.userAccount.username" readonly="true"/>
	<acme:form-textbox code="sponsor.non-commercial-banner.form.label.sponsor.organisationName" path="sponsor.organisationName" readonly="true"/>
	</jstl:if>
	
		<acme:form-submit test="${command == 'show'}"
		code="sponsor.non-commercial-banner.form.button.update"
		action="/sponsor/non-commercial-banner/update"/>
		
		<acme:form-submit test="${command == 'create'}"
		code="sponsor.non-commercial-banner.form.button.create"
		action="/sponsor/non-commercial-banner/create"/>
		
		<acme:form-submit test="${command == 'update'}"
		code="sponsor.non-commercial-banner.form.button.update"
		action="/sponsor/non-commercial-banner/update"/>
	
	<acme:form-submit test="${command == 'show'}"
		code="sponsor.noncommercial-banner.form.button.delete"
		action="/sponsor/non-commercial-banner/delete"/>
	
	<acme:form-submit test="${command == 'delete'}"
		code="sponsor.noncommercial-banner.form.button.delete"
		action="/sponsor/non-commercial-banner/delete"/>

   	
   	<acme:form-return code="sponsor.non-commercial-banner.form.button.return"/>
</acme:form>
