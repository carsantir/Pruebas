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
	<acme:form-textbox code="employer.duty.form.label.title" path="title"/>
	<acme:form-textbox code="employer.duty.form.label.description" path="description" />
	<acme:form-double code="employer.duty.form.label.percentage" path="percentage" placeholder="20"/>
	<acme:form-hidden path="job.draft"/>
	
	<jstl:if test="${command == 'delete'}">
		<acme:form-hidden path="job.employer.id"/>
	</jstl:if>
	
	<jstl:if test="${command == 'show'}">
		<acme:form-textbox code="employer.duty.form.label.job.reference" path="job.reference" readonly="true"/>
	</jstl:if>
	
	
	
	<jstl:if test="${command == 'create' }">
		<acme:form-hidden path="job.id" />
	</jstl:if>
	
	<acme:form-submit test="${command == 'create'}"
		code="employer.duty.form.button.create"
		action="/employer/duty/create"/>
		
	<acme:form-submit test="${command == 'show' && job.draft}"
		code="employer.duty.form.button.update"
		action="/employer/duty/update"/>
		
	<acme:form-submit test="${command == 'update'}"
		code="employer.duty.form.button.update"
		action="/employer/duty/update"/>
		
	<acme:form-submit test="${command == 'show' && job.draft}"
		code="employer.duty.form.button.delete"
		action="/employer/duty/delete"/>
		
	<acme:form-submit test="${command == 'delete'}"
		code="employer.duty.form.button.delete"
		action="/employer/duty/delete"/>
		
	<acme:form-return code="employer.duty.form.button.return"/>
</acme:form>
