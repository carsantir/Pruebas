
package acme.features.employer.auditRecord;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.auditRecords.AuditRecord;
import acme.entities.jobs.Job;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface EmployerAuditRecordRepository extends AbstractRepository {

	@Query("select ar from AuditRecord ar where ar.id=?1")
	AuditRecord findOneAuditRecordById(int id);

	@Query("select ar from AuditRecord ar where ar.job.id=?1 and ar.draft=false")
	Collection<AuditRecord> findManyActiveByJobId(int jobId);

	@Query("select j from Job j where j.id=?1")
	Job findOneJobById(int jobId);

}
