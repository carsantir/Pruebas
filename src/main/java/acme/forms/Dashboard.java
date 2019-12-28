
package acme.forms;

import java.io.Serializable;
import java.util.Date;

import acme.framework.datatypes.Money;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Dashboard implements Serializable {

	private static final long	serialVersionUID	= 1L;

	Integer						totalAnnouncements;
	Integer						totalCompanyRecords;
	Integer						totalInvestorRecords;

	Money						minRewardRequest;
	Money						maxRewardRequest;
	Money						averageRewardRequest;
	Money						sdRewardRequest;

	Money						minRewardOffer;
	Money						maxRewardOffer;
	Money						averageRewardOffer;
	Money						sdMinRewardOffer;
	Money						sdMaxRewardOffer;

	Integer[]					companiesPerSector;
	String[]					companySectors;

	Integer[]					investorsPerSector;
	String[]					investorSectors;

	Double						avgJobsPerEmployer;
	Double						avgApplicationsPerEmployer;
	Double						avgApplicationsPerWorker;

	Double						ratioOfPendingApplications;
	Double						ratioOfAcceptedApplications;
	Double						ratioOfRejectedApplications;

	Double						ratioOfDraftJobs;
	Double						ratioOfPublishedJobs;

	Date[]						lastFourWeeksPerDay;
	Integer[]					pendingApplicationsPerDay;
	Integer[]					acceptedApplicationsPerDay;
	Integer[]					rejectedApplicationsPerDay;
}
