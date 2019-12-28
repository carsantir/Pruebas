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
	<acme:form-textbox code="administrator.company-record.form.label.companyName" path="companyName"/>
	<acme:form-textbox code="administrator.company-record.form.label.sector" path="sector"/>
	<acme:form-textbox code="administrator.company-record.form.label.ceo" path="ceo"/>
	<acme:form-textarea code="administrator.company-record.form.label.description" path="description"/>
	<acme:form-url code="administrator.company-record.form.label.website" path="website"/>
	<acme:form-textbox code="administrator.company-record.form.label.number" path="number" placeholder="+2 (678) 1234567"/>
	<acme:form-textbox code="administrator.company-record.form.label.email" path="email" placeholder="example@example.com"/>
	<acme:form-textbox code="administrator.company-record.form.label.stars" path="stars"/>
	<jstl:if test="${command != 'create'}">
		<acme:form-checkbox 
			code="administrator.company-record.form.label.incorporated" 
			path="incorporated"
			readonly="true"/>
	</jstl:if>
	
	
	<acme:form-submit test="${command == 'create'}" 
		code="administrator.company-record.form.button.create" 
		action="/administrator/company-record/create"/>

	<acme:form-submit test="${command == 'show'}"
		code="administrator.company-record.form.button.update" 
		action="/administrator/company-record/update"/>
	<acme:form-submit test="${command == 'update'}"
		code="administrator.company-record.form.button.update" 
		action="/administrator/company-record/update"/>
	<acme:form-submit test="${command == 'show'}"
		code="administrator.company-record.form.button.delete" 
		action="/administrator/company-record/delete"/>
	<acme:form-submit test="${command == 'delete'}"
		code="administrator.company-record.form.button.delete" 
		action="/administrator/company-record/delete"/>
		
  	<acme:form-return code="administrator.company-record.form.button.return"/>
</acme:form>
