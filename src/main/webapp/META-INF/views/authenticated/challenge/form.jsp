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
	<acme:form-textbox code="authenticated.challenge.form.label.title" path="title" />
	<acme:form-textarea code="authenticated.challenge.form.label.description" path="description" />
	<acme:form-textbox code="authenticated.challenge.form.label.goalGold" path="goalGold" />
	<acme:form-money code="authenticated.challenge.form.label.rewardGold" path="rewardGold"/>
		<acme:form-textbox code="authenticated.challenge.form.label.goalSilver" path="goalSilver" />
	<acme:form-money code="authenticated.challenge.form.label.rewardSilver" path="rewardSilver"/>
		<acme:form-textbox code="authenticated.challenge.form.label.goalBronze" path="goalBronze" />
	<acme:form-money code="authenticated.challenge.form.label.rewardBronze" path="rewardBronze"/>
	<acme:form-moment code="authenticated.challenge.form.label.deadline" path="deadline" />
	
	
	<acme:form-return code="authenticated.challenge.form.button.return"/>
</acme:form>
