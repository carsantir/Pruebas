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
	<acme:form-textbox code="authenticated.job.form.label.reference" path="reference" placeholder="EEEE-JJJJ (is recommended an anotation similar)" />
	<acme:form-textbox code="authenticated.job.form.label.title" path="title" />
	<acme:form-moment code="authenticated.job.form.label.deadline" path="deadline" />
	<acme:form-money code="authenticated.job.form.label.salary" path="salary" />
	<acme:form-url code="authenticated.job.form.label.moreInfo" path="moreInfo" />
	<acme:form-textarea code="authenticated.job.form.label.description" path="description" />
	<acme:form-textbox code="authenticated.job.form.label.username" path="employer.userAccount.username" />
	<acme:form-checkbox code="authenticated.job.form.label.draft" path="draft" />
	<acme:form-hidden path="id"/>
	<acme:form-submit code="authenticated.job.form.label.duties" action="/authenticated/duty/list?id=${id}" method="get" />
	<acme:form-submit code="authenticated.job.form.button.link" action="/authenticated/audit-record/list-all-active?id=${id}" method="get" />
	
  <acme:form-submit code="authenticated.job.form.button.write" 
    	action="/auditor/audit-record/create?jobId=${id}" method="get" test="${isAuditor}"/>
  
  <jstl:if test="${isWorker}">
		<acme:form-submit code="authenticated.job.form.button.apply" action="/worker/application/create?jobId=${id}" method="get"/>
	</jstl:if>
	
		

	<acme:form-return code="authenticated.job.form.button.return"/>
</acme:form>
