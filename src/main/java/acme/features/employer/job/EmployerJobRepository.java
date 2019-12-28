
package acme.features.employer.job;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.auditRecords.AuditRecord;
import acme.entities.configurations.Configuration;
import acme.entities.jobs.Application;
import acme.entities.jobs.Duty;
import acme.entities.jobs.Job;
import acme.entities.roles.Employer;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface EmployerJobRepository extends AbstractRepository {

	@Query("select j from Job j where j.id=?1")
	Job findOneJobById(int id);

	@Query("select j from Job j where j.employer.id=?1")
	Collection<Job> findManyByEmployerId(int employerId);

	@Query("select e from Employer e where e.id = (select j.employer.id from Job j where j.id = ?1 )")
	Employer findEmployer(int id);

	@Query("select e from Employer e where e.id=?1")
	Employer findEmployerbyEmployerId(int id);

	@Query("select d from Duty d where d.job.id=?1")
	Collection<Duty> findDutiesFromJob(int id);

	@Query("select a from Application a where a.job.id=?1")
	Collection<Application> findApplicationsFromJob(int id);

	@Query("select ar from AuditRecord ar where ar.job.id=?1")
	Collection<AuditRecord> findAuditRecordsFromJob(int id);

	@Query("select c from Configuration c")
	Collection<Configuration> findConfigurationParameters();

	@Query("select j from Job j where j.reference = ?1")
	Job findJobByReference(String reference);
}
