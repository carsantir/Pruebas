
package acme.features.employer.auditRecord;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.auditRecords.AuditRecord;
import acme.entities.jobs.Job;
import acme.entities.roles.Employer;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.services.AbstractListService;

@Service
public class EmployerAuditRecordListActiveService implements AbstractListService<Employer, AuditRecord> {

	@Autowired
	private EmployerAuditRecordRepository repository;


	@Override
	public boolean authorise(final Request<AuditRecord> request) {
		assert request != null;

		int jobId;

		String[] aux = request.getServletRequest().getQueryString().trim().split("id=");
		jobId = Integer.parseInt(aux[1]);

		Job job = this.repository.findOneJobById(jobId);
		Employer employer = job.getEmployer();

		return employer.getId() == request.getPrincipal().getActiveRoleId();
	}

	@Override
	public void unbind(final Request<AuditRecord> request, final AuditRecord entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "title", "moment", "draft", "job.title", "auditor.userAccount.username");

	}

	@Override
	public Collection<AuditRecord> findMany(final Request<AuditRecord> request) {
		assert request != null;
		Collection<AuditRecord> result;
		int jobId;

		String[] aux = request.getServletRequest().getQueryString().trim().split("id=");
		jobId = Integer.parseInt(aux[1]);
		result = this.repository.findManyActiveByJobId(jobId);

		return result;
	}

}
