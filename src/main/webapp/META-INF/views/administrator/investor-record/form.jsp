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
	<acme:form-textbox code="administrator.investor-record.form.label.investorName" path="investorName"/>
	<acme:form-textbox code="administrator.investor-record.form.label.sector" path="sector"/>
	<acme:form-textarea code="administrator.investor-record.form.label.investingStatement" path="investingStatement"/>	
	<acme:form-textbox code="administrator.investor-record.form.label.stars" path="stars"/>	
	
	<acme:form-submit test="${command == 'show'}"
		code="administrator.investor-record.form.button.delete"
		action="/administrator/investor-record/delete"/>
		
	<acme:form-submit test="${command == 'show'}"
		code="administrator.investor-record.form.button.update"
		action="/administrator/investor-record/update"/>

	<acme:form-submit test="${command == 'create'}"
		code="administrator.investor-record.form.button.create"
		action="/administrator/investor-record/create"/>

	<acme:form-submit test="${command == 'delete'}"
		code="administrator.investor-record.form.button.delete"
		action="/administrator/investor-record/delete"/>
		
	<acme:form-submit test="${command == 'update'}"
		code="administrator.investor-record.form.button.update"
		action="/administrator/investor-record/update"/>
		
	<acme:form-return code="administrator.investor-record.form.button.return"/>
</acme:form>

