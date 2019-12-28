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

<acme:form readonly="true">
	<acme:form-textbox code="anonymous.company-record.form.label.companyName" path="companyName"/>
	<acme:form-textbox code="anonymous.company-record.form.label.sector" path="sector"/>
	<acme:form-textbox code="anonymous.company-record.form.label.ceo" path="ceo"/>
	<acme:form-textarea code="anonymous.company-record.form.label.description" path="description"/>
	<acme:form-url code="anonymous.company-record.form.label.website" path="website"/>
	<acme:form-textbox code="anonymous.company-record.form.label.number" path="number"/>
	<acme:form-textbox code="anonymous.company-record.form.label.email" path="email"/>
	<acme:form-textbox code="anonymous.company-record.form.label.stars" path="stars"/>
	<acme:form-checkbox code="anonymous.company-record.form.label.incorporated" path="incorporated"/>
	
  	<acme:form-return code="anonymous.company-record.form.button.return"/>
</acme:form>
