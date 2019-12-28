
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
	<acme:list-column code="employer.duty.list.label.title" path="title" width="10%" />		
   	<acme:list-column code="employer.duty.list.label.description" path="description" width="40%" />
   	<acme:list-column code="employer.duty.list.label.percentage" path="percentage" width="10%" />
   	<acme:list-column code="employer.duty.list.label.job.reference"	path="job.reference" width="10%" />
</acme:list>
<acme:form>
	<acme:form-return code="employer.duty.form.button.return"/>
</acme:form>