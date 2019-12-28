
package acme.features.administrator.dashboard;

import java.util.Date;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.framework.datatypes.Money;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AdministratorDashboardRepository extends AbstractRepository {

	@Query("select count(*) from Announcement")
	Integer getTotalAnnouncement();

	@Query("select count(*) from CompanyRecord")
	Integer getTotalCompanyRecord();

	@Query("select count(*) from InvestorRecord")
	Integer getTotalInvestorRecord();

	@Query("select a.reward from Request a where a.reward.amount = (select min(reward.amount) from Request)")
	Money getMinRewardRequest();

	@Query("select a.reward from Request a where a.reward.amount = (select max(reward.amount) from Request)")
	Money getMaxRewardRequest();

	@Query("select avg(reward.amount) from Request")
	Double getAverageRewardRequestAmount();

	@Query("select a.reward.currency from Request a where a.reward.amount = (select max(reward.amount) from Request)")
	String getAverageRewardRequestCurrency();

	@Query("select stddev(reward.amount) from Request")
	Double getSdRewardRequestAmount();

	@Query("select a.minPrice from Offer a where a.minPrice.amount = (select min(minPrice.amount) from Offer)")
	Money getMinRewardOffer();

	@Query("select a.maxPrice from Offer a where a.maxPrice.amount = (select max(maxPrice.amount) from Offer)")
	Money getMaxRewardOffer();

	@Query("select avg(minPrice.amount + maxPrice.amount)/2 from Offer")
	Double getAverageRewardOfferAmount();

	@Query("select a.minPrice.currency from Offer a where a.minPrice.amount = (select min(minPrice.amount) from Offer)")
	String getAverageRewardOfferCurrency();

	@Query("select stddev(minPrice.amount) from Offer")
	Double getMinSdRewardRequestAmount();

	@Query("select stddev(maxPrice.amount) from Offer")
	Double getMaxSdRewardRequestAmount();

	@Query("select count(*) from CompanyRecord group by sector")
	Integer[] getCompaniesPerSector();

	@Query("select sector from CompanyRecord group by sector")
	String[] getCompanySectors();

	@Query("select count(*) from InvestorRecord group by sector")
	Integer[] getInvestorPerSector();

	@Query("select sector from InvestorRecord group by sector")
	String[] getInvestorSectors();

	@Query("select avg(select count(j) from Job j where j.employer.id=e.id) from Employer e")
	Double averageNumberOfJobsPerEmployer();

	@Query("select avg(select count(a) from Application a where a.worker.id=w.id) from Worker w")
	Double averageNumberOfApplicationsPerWorker();

	@Query("select avg(select count(a) from Application a where exists(select j from Job j where j.employer.id=e.id and a.job.id=j.id)) from Employer e")
	Double averageNumberOfApplicationsPerEmployer();

	@Query("select 1.0 * count(a) / (select count(b) from Application b) from Application a where a.status = acme.entities.jobs.Status.PENDING")
	Double ratioOfPendingApplications();

	@Query("select 1.0 * count(a) / (select count(b) from Application b) from Application a where a.status = acme.entities.jobs.Status.ACCEPTED")
	Double ratioOfAcceptedApplications();

	@Query("select 1.0 * count(a) / (select count(b) from Application b) from Application a where a.status = acme.entities.jobs.Status.REJECTED")
	Double ratioOfRejectedApplications();

	@Query("select 1.0 * count(a)/(select count(b) from Job b) from Job a where a.draft=true")
	Double ratioOfDraftJobs();

	@Query("select 1.0 * count(a)/(select count(b) from Job b) from Job a where a.draft=false")
	Double ratioOfPublishedJobs();

	@Query("select count(*) from Application a where a.status = acme.entities.jobs.Status.PENDING and year(a.moment) = year(?1) and month(a.moment) = month(?1) and day(a.moment) = day(?1)")
	Integer pendingApplicationsPerDay(Date fecha);

	@Query("select count(*) from Application a where a.status = acme.entities.jobs.Status.ACCEPTED and year(a.moment) = year(?1) and month(a.moment) = month(?1) and day(a.moment) = day(?1)")
	Integer acceptedApplicationsPerDay(Date fecha);

	@Query("select count(*) from Application a where a.status = acme.entities.jobs.Status.REJECTED and year(a.moment) = year(?1) and month(a.moment) = month(?1) and day(a.moment) = day(?1)")
	Integer rejectedApplicationsPerDay(Date fecha);

}
