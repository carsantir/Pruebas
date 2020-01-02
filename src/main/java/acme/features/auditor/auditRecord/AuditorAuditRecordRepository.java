
package acme.features.auditor.auditRecord;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.auditRecords.AuditRecord;
import acme.entities.jobs.Job;
import acme.entities.roles.Auditor;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AuditorAuditRecordRepository extends AbstractRepository {

	@Query("select ar from AuditRecord ar where ar.id=?1")
	AuditRecord findOneAuditRecordById(int id);

	@Query("select ar from AuditRecord ar where (ar.job.id=?1 and ar.draft=false) or (ar.auditor.id=?2 and ar.job.id=?1 and ar.draft=true)")
	Collection<AuditRecord> findManyActiveByJobId(int jobId, int auditorId);

	@Query("select a from Auditor a where a.id=?1")
	Auditor findOneAuditorById(int auditorId);

	@Query("select j from Job j where j.id=?1")
	Job findOneJobById(int id);

	//	@Query("select id from Auditor a where a.enabled=0")
	//	Collection<Integer> findOneAuditorByEnabled();

}
