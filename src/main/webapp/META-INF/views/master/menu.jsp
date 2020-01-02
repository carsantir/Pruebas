<%--
- menu.jsp
-
- Copyright (c) 2019 Rafael Corchuelo.
-
- In keeping with the traditional purpose of furthering education and research, it is
- the policy of the copyright owner to permit non-commercial use and redistribution of
- this software. It has been tested carefully, but it is not guaranteed for any particular
- purposes.  The copyright owner does not offer any warranties or representations, nor do
- they accept any liabilities with respect to them.
--%>

<%@page language="java" import="acme.framework.helpers.PrincipalHelper,acme.entities.roles.Provider,acme.entities.roles.Consumer"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<acme:menu-bar code="master.menu.home">
	<acme:menu-left>
		<acme:menu-option code="master.menu.anonymous" access="isAnonymous()">
			<acme:menu-suboption code="master.menu.anonymous.features.announcement" action="/anonymous/announcement/list"/>
			<acme:menu-separator/>
			<acme:menu-suboption code="master.menu.anonymous.features.company-record.list-all" action="/anonymous/company-record/list-all"/>
			<acme:menu-suboption code="master.menu.anonymous.features.company-record.list-top" action="/anonymous/company-record/list-top"/>
			<acme:menu-separator/>
			<acme:menu-suboption code="master.menu.anonymous.features.investor-record.list-all" action="/anonymous/investor-record/list-all"/>
			<acme:menu-suboption code="master.menu.anonymous.features.investor-record.list-top" action="/anonymous/investor-record/list-top"/>
		</acme:menu-option>
		
		<acme:menu-option code="master.menu.authenticated" access="isAuthenticated()">
			<acme:menu-suboption code="master.menu.features.request" action="/authenticated/request/list"/>
			<acme:menu-suboption code="master.menu.features.announcement" action="/authenticated/announcement/list"/>
			<acme:menu-suboption code="master.menu.features.challenge" action="/authenticated/challenge/list"/>
			<acme:menu-suboption code="master.menu.features.company-record" action="/authenticated/company-record/list"/>
			<acme:menu-suboption code="master.menu.features.investor-record" action="/authenticated/investor-record/list"/>
			<acme:menu-suboption code="master.menu.features.offer" action="/authenticated/offer/list"/>
			<acme:menu-suboption code="master.menu.features.job" action="/authenticated/job/list-all-active"/>
			<acme:menu-separator/>
			<acme:menu-suboption code="master.menu.features.message-thread.create" action="/authenticated/message-thread/create"/>			
			<acme:menu-suboption code="master.menu.features.message-thread" action="/authenticated/message-thread/list-mine"/>
		</acme:menu-option>

		<acme:menu-option code="master.menu.administrator" access="hasRole('Administrator')">
			<acme:menu-suboption code="master.menu.administrator.announcements" action="/administrator/announcement/list"/>
     		<acme:menu-suboption code="master.menu.administrator.announcements.create" action="/administrator/announcement/create"/>
     		<acme:menu-separator/>
     		<acme:menu-suboption code="master.menu.administrator.challenges" action="/administrator/challenge/list"/>
     		<acme:menu-suboption code="master.menu.administrator.challenges.create" action="/administrator/challenge/create"/>
     		<acme:menu-separator/>
     		<acme:menu-suboption code="master.menu.administrator.auditor" action="/administrator/not-active-auditor/list"/>
     		<acme:menu-separator/>
			<acme:menu-suboption code="master.menu.administrator.investor-record" action="/administrator/investor-record/list"/>
			<acme:menu-suboption code="master.menu.administrator.investor-record.create" action="/administrator/investor-record/create"/>
			<acme:menu-separator/>
			<acme:menu-suboption code="master.menu.administrator.company-record" action="/administrator/company-record/list"/>
			<acme:menu-suboption code="master.menu.administrator.company-record.create" action="/administrator/company-record/create"/>
			<acme:menu-separator/>
      		<acme:menu-suboption code="master.menu.administrator.commercial-banner" action="/administrator/commercial-banner/list"/>
			<acme:menu-suboption code="master.menu.administrator.commercial-banner.create" action="/administrator/commercial-banner/create"/>
			<acme:menu-suboption code="master.menu.administrator.non-commercial-banner" action="/administrator/non-commercial-banner/list"/>
			<acme:menu-suboption code="master.menu.administrator.non-commercial-banner.create" action="/administrator/non-commercial-banner/create"/>
			<acme:menu-separator/>
			<acme:menu-suboption code="master.menu.administrator.configuration" action="/administrator/configuration/list"/>
			<acme:menu-suboption code="master.menu.administrator.user-accounts" action="/administrator/user-account/list"/>
			<acme:menu-suboption code="master.menu.administrator.dashboard" action="/administrator/dashboard/show"/>
			<acme:menu-separator/>
			<acme:menu-suboption code="master.menu.administrator.shutdown" action="/master/shutdown"/>
		</acme:menu-option>

		<acme:menu-option code="master.menu.provider" access="hasRole('Provider')">
			<acme:menu-suboption code="master.menu.provider.features.request.create" action="/provider/request/create"/>
		</acme:menu-option>

		<acme:menu-option code="master.menu.consumer" access="hasRole('Consumer')">
      		<acme:menu-suboption code="master.menu.consumer.offers.create" action="/consumer/offer/create"/>
		</acme:menu-option>
		<acme:menu-option code="master.menu.employer" access="hasRole('Employer')">
			<acme:menu-suboption code="master.menu.employer.features.job.list" action="/employer/job/list-mine"/>
			<acme:menu-suboption code="master.menu.employer.application.list-mine" action="/employer/application/list-mine"/>
			<acme:menu-suboption code="master.menu.employer.features.job.create" action="/employer/job/create"/>
		</acme:menu-option>
		
		<acme:menu-option code="master.menu.auditor" access="hasRole('Auditor')">
			<acme:menu-suboption code="master.menu.auditor.features.notwrittenjob.list" action="/auditor/job/list-not-written"/>
			<acme:menu-suboption code="master.menu.auditor.features.job.list" action="/auditor/job/list-written"/>
		</acme:menu-option>

		<acme:menu-option code="master.menu.worker" access="hasRole('Worker')">
      		<acme:menu-suboption code="master.menu.worker.application.list-mine" action="/worker/application/list-mine"/>
      		<acme:menu-suboption code="master.menu.worker.application.create" action="/authenticated/job/list-all-active"/>
		</acme:menu-option>
		
		<acme:menu-option code="master.menu.sponsor" access="hasRole('Sponsor')">
      		<acme:menu-suboption code="master.menu.sponsor.commercial-banner.list-mine" action="/sponsor/commercial-banner/list-mine"/>
      		<acme:menu-suboption code="master.menu.sponsor.commercial-banner.create" action="/sponsor/commercial-banner/create"/>
      		<acme:menu-suboption code="master.menu.sponsor.non-commercial-banner.list-mine" action="/sponsor/non-commercial-banner/list-mine"/>
      		<acme:menu-suboption code="master.menu.sponsor.non-commercial-banner.create" action="/sponsor/non-commercial-banner/create"/>
		</acme:menu-option>
		
	</acme:menu-left>

	<acme:menu-right>
		<acme:menu-option code="master.menu.sign-up" action="/anonymous/user-account/create" access="isAnonymous()"/>
		<acme:menu-option code="master.menu.sign-in" action="/master/sign-in" access="isAnonymous()"/>

		<acme:menu-option code="master.menu.user-account" access="isAuthenticated()">
			<acme:menu-suboption code="master.menu.user-account.general-data" action="/authenticated/user-account/update"/>
			<acme:menu-suboption code="master.menu.user-account.become-employer" action="/authenticated/employer/create" access="!hasRole('Employer')"/>
			<acme:menu-suboption code="master.menu.user-account.employer" action="/authenticated/employer/update" access="hasRole('Employer')"/>
			<acme:menu-suboption code="master.menu.user-account.become-worker" action="/authenticated/worker/create" access="!hasRole('Worker')"/>
			<acme:menu-suboption code="master.menu.user-account.worker" action="/authenticated/worker/update" access="hasRole('Worker')"/>
			<acme:menu-suboption code="master.menu.user-account.become-auditor" action="/authenticated/not-active-auditor/create" access="!hasRole('NotActiveAuditor')"/>
			<acme:menu-suboption code="master.menu.user-account.auditor" action="/authenticated/not-active-auditor/update" access="hasRole('NotActiveAuditor')"/>
			<acme:menu-suboption code="master.menu.user-account.become-sponsor" action="/authenticated/sponsor/create" access="!hasRole('Sponsor')"/>
			<acme:menu-suboption code="master.menu.user-account.sponsor" action="/authenticated/sponsor/update" access="hasRole('Sponsor')"/>
			<acme:menu-suboption code="master.menu.user-account.become-provider" action="/authenticated/provider/create" access="!hasRole('Provider')"/>
			<acme:menu-suboption code="master.menu.user-account.provider" action="/authenticated/provider/update" access="hasRole('Provider')"/>
			<acme:menu-suboption code="master.menu.user-account.become-consumer" action="/authenticated/consumer/create" access="!hasRole('Consumer')"/>
			<acme:menu-suboption code="master.menu.user-account.consumer" action="/authenticated/consumer/update" access="hasRole('Consumer')"/>
		</acme:menu-option>

		<acme:menu-option code="master.menu.sign-out" action="/master/sign-out" access="isAuthenticated()"/>
	</acme:menu-right>
</acme:menu-bar>

