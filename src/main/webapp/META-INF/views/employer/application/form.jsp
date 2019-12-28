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
	<acme:form-textbox code="employer.application.form.label.referenceNumber" path="referenceNumber" readonly="true"/>		
   	<acme:form-moment code="employer.application.form.label.moment" path="moment" readonly="true"/>
	<acme:form-textbox code="employer.application.form.label.status" path="status" readonly="true"/>
	<acme:form-textbox code="employer.application.form.label.statement" path="statement" readonly="true"/>	
	<acme:form-textbox code="employer.application.form.label.skills" path="skills" readonly="true"/>	
	<acme:form-textbox code="employer.application.form.label.qualifications" path="qualifications" readonly="true"/>	
	<acme:form-textbox code="employer.application.form.label.job.title" path="job.title" readonly="true"/>
	<acme:form-textbox code="employer.application.form.label.job.reference" path="job.reference" readonly="true"/>
	<acme:form-textbox code="employer.application.form.label.worker.userAccount.username" path="worker.userAccount.username" readonly="true"/>
	<acme:form-textarea code="employer.application.form.label.justification" path="justification" />
	
	<acme:form-submit test="${command == 'show' && status == 'PENDING'}"
		code="employer.application.form.button.reject" 
		action="/employer/application/update-reject"/>
	<acme:form-submit test="${command == 'update-reject'}"
		code="employer.application.form.button.reject" 
		action="/employer/application/update-reject"/>
		
	<acme:form-submit test="${command == 'show'&& status == 'PENDING'}"
		code="employer.application.form.button.accept" 
		action="/employer/application/update-accept"/>
	<acme:form-submit test="${command == 'update-accept'}"
		code="employer.application.form.button.accept" 
		action="/employer/application/update-accept"/>
	
	<acme:form-return code="employer.application.form.button.return"/>
</acme:form>
