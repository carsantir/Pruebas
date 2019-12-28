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
	<acme:form-textbox code="administrator.dashboard.form.label.totalAnnouncements" path="totalAnnouncements"/>
	<acme:form-textbox code="administrator.dashboard.form.label.totalCompanyRecords" path="totalCompanyRecords"/>
	<acme:form-textbox code="administrator.dashboard.form.label.totalInvestorRecords" path="totalInvestorRecords"/>

	<acme:form-money code="administrator.dashboard.form.label.minRewardRequest" path="minRewardRequest"/>
	<acme:form-textbox code="administrator.dashboard.form.label.maxRewardRequest" path="maxRewardRequest"/>
	<acme:form-textbox code="administrator.dashboard.form.label.averageRewardRequest" path="averageRewardRequest"/>
	<acme:form-textbox code="administrator.dashboard.form.labelsdRewardRequest" path="sdRewardRequest"/>
	
	
	<acme:form-textbox code="administrator.dashboard.form.label.minRewardOffer" path="minRewardOffer"/>
	<acme:form-textbox code="administrator.dashboard.form.label.maxRewardOffer" path="maxRewardOffer"/>
	<acme:form-textbox code="administrator.dashboard.form.label.averageRewardOffer" path="averageRewardOffer"/>
	<acme:form-textbox code="administrator.dashboard.form.labelsdMinRewardOffer" path="sdMinRewardOffer"/>	
	<acme:form-textbox code="administrator.dashboard.form.labelsdMaxRewardOffer" path="sdMaxRewardOffer"/>
	
	<acme:form-textbox code="administrator.dashboard.form.label.avgJobsPerEmployer" path="avgJobsPerEmployer"/>
	<acme:form-textbox code="administrator.dashboard.form.label.avgApplicationsPerEmployer" path="avgApplicationsPerEmployer"/>
	<acme:form-textbox code="administrator.dashboard.form.label.avgApplicationsPerWorkers" path="avgApplicationsPerWorker"/>

	
</acme:form>

<div>
	<canvas id="canvas"></canvas>
</div>

<div>
	<canvas id="canvas2"></canvas>
</div>

<div>
	<canvas id="canvas3"></canvas>
</div>

<div>
	<canvas id="canvas4"></canvas>
</div>

<div>
	<canvas id="canvas5"></canvas>
</div>

<div>
	<canvas id="canvas6"></canvas>
</div>

<div>
	<canvas id="canvas7"></canvas>
</div>
<script type="text/javascript">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
	$(document).ready(function(){
		var data = {
				labels :   [<jstl:forEach var="item" items="${companySectors}" >
								"${item}",
							</jstl:forEach>],  
				datasets: [
					{	label : "Number of Companies in the following sectors",
						data : [<jstl:forEach var="item" items="${companiesPerSector}" >
									<jstl:out value="${item}" />,
								</jstl:forEach>
								]
						
					}
				]
		};
		var options = {
				scales : {
					yAxes : [{
						ticks : {
							suggestedMin: 0.0
						}
					}
					]
				}
		};
		var data2 = {
				labels :   [<jstl:forEach var="item4" items="${investorSectors}" >
								"${item4}",
							</jstl:forEach>],  
				datasets: [
					{
						label : "Number of Investors in the following sectors",
						data : [<jstl:forEach var="item5" items="${investorsPerSector}" >
									<jstl:out value="${item5}" />,
								</jstl:forEach>
								]
						
					}
				]
		};
		
		var canvas, context, canvas2, context2;
		
		canvas = document.getElementById("canvas");
		canvas2 = document.getElementById("canvas2");
		context = canvas.getContext("2d");
		context2 = canvas2.getContext("2d");
		new Chart(context, {
			type : "bar",
			data : data,
			options : options
		});
		
		new Chart(context2,{
			type : "bar",
			data : data2,
			options : options
		});
		
		var data3 = {
				labels :   ["PENDING", "ACCEPTED", "REJECTED"],  
				datasets: [
					{	label : "Ratio of Applications depending on the status",
						data : [
								<jstl:out value="${ratioOfPendingApplications}" />,
								<jstl:out value="${ratioOfAcceptedApplications}" />,
								<jstl:out value="${ratioOfRejectedApplications}" />
								]
						
					}
				]
		};
		

		var canvas3, context3;

		canvas3 = document.getElementById("canvas3");
		context3 = canvas3.getContext("2d");
		
		new Chart(context3,{
			type : "bar",
			data : data3,
			options : options
		});
		
		var data4 = {
				labels :   ["DRAFT", "PUBLISHED"],  
				datasets: [
					{	label : "Ratio of Jobs depending on the status",
						data : [
								<jstl:out value="${ratioOfDraftJobs}" />,
								<jstl:out value="${ratioOfPublishedJobs}" />
								]
						
					}
				]
		};
		

		var canvas4, context4;

		canvas4 = document.getElementById("canvas4");
		context4 = canvas4.getContext("2d");
		
		new Chart(context4,{
			type : "bar",
			data : data4,
			options : options
		});
		
		var data5 = {
				labels :   [<jstl:forEach var="item2" items="${lastFourWeeksPerDay}" >
								"${item2}",
							</jstl:forEach>],  
				datasets: [
					{	label : "Number of Pending Applications per day in the last four weeks",
						data : [<jstl:forEach var="item3" items="${pendingApplicationsPerDay}" >
									<jstl:out value="${item3}" />,
								</jstl:forEach>
								]
						
					}
				]
		};

		
		var canvas5, contex5;
		
		canvas5 = document.getElementById("canvas5");
		context5= canvas5.getContext("2d");
		new Chart(context5, {
			type : "bar",
			data : data5,
			options : options
		});
		
		var data6 = {
				labels :   [<jstl:forEach var="item7" items="${lastFourWeeksPerDay}" >
								"${item7}",
							</jstl:forEach>],  
				datasets: [
					{	label : "Number of Accepted Applications per day in the last four weeks",
						data : [<jstl:forEach var="item8" items="${acceptedApplicationsPerDay}" >
									<jstl:out value="${item8}" />,
								</jstl:forEach>
								]
						
					}
				]
		};

		
		var canvas6, contex6;
		
		canvas6 = document.getElementById("canvas6");
		context6= canvas6.getContext("2d");
		new Chart(context6, {
			type : "bar",
			data : data6,
			options : options
		});
		
		var data7 = {
				labels :   [<jstl:forEach var="item9" items="${lastFourWeeksPerDay}" >
								"${item9}",
							</jstl:forEach>],  
				datasets: [
					{	label : "Number of Rejected Applications per day in the last four weeks",
						data : [<jstl:forEach var="item10" items="${rejectedApplicationsPerDay}" >
									<jstl:out value="${item10}" />,
								</jstl:forEach>
								]
						
					}
				]
		};

		
		var canvas7, contex7;
		
		canvas7 = document.getElementById("canvas7");
		context7= canvas7.getContext("2d");
		new Chart(context7, {
			type : "bar",
			data : data7,
			options : options
		});

		
	});
</script>
