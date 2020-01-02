
package acme.features.auditor.job;

import java.util.Collection;
import java.util.Date;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.jobs.Job;
import acme.entities.roles.Employer;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AuditorJobRepository extends AbstractRepository {

	@Query("select j from Job j where j.id=?1")
	Job findOneJobById(int id);

	@Query("select j from Job j where j.id not in (select ar.job.id from AuditRecord ar where ar.auditor.id=?1) and j.draft=0 and j.deadline>?2")
	Collection<Job> findManyByAuditorId(int auditorId, Date date);

	@Query("select j from Job j where j.id in (select ar.job.id from AuditRecord ar where ar.auditor.id=?1) and j.draft=0 and j.deadline>?2")
	Collection<Job> findManyByEmployerId(int auditorId, Date date);

	@Query("select e from Employer e where e.id= (select j.employer.id from Job j where j.id=?1)")
	Employer findEmployer(int id);

}
