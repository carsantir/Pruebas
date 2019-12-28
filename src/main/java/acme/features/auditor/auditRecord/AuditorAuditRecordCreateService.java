
package acme.features.auditor.auditRecord;

import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.auditRecords.AuditRecord;
import acme.entities.jobs.Job;
import acme.entities.roles.Auditor;
import acme.framework.components.Errors;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.services.AbstractCreateService;

@Service
public class AuditorAuditRecordCreateService implements AbstractCreateService<Auditor, AuditRecord> {

	@Autowired
	private AuditorAuditRecordRepository repository;


	@Override
	public boolean authorise(final Request<AuditRecord> request) {
		assert request != null;

		boolean result;
		int jobId;
		Job job;

		String url = request.getServletRequest().getQueryString();
		if (url != null) {
			String[] aux = url.split("jobId=");
			jobId = Integer.parseInt(aux[1]);
			job = this.repository.findOneJobById(jobId);
		} else {
			job = this.repository.findOneJobById(request.getModel().getInteger("job.id"));
		}

		result = !job.isDraft() && job.getDeadline().after(Calendar.getInstance().getTime());

		return result;
	}

	@Override
	public void bind(final Request<AuditRecord> request, final AuditRecord entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		request.bind(entity, errors, "moment");

	}

	@Override
	public void unbind(final Request<AuditRecord> request, final AuditRecord entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "job.id", "title", "body", "draft");

	}

	@Override
	public AuditRecord instantiate(final Request<AuditRecord> request) {
		AuditRecord result;
		result = new AuditRecord();
		Job job;

		int jobId;
		String url = request.getServletRequest().getQueryString();
		if (url != null) {
			String[] aux = url.split("jobId=");
			jobId = Integer.parseInt(aux[1]);
			job = this.repository.findOneJobById(jobId);
		} else {
			job = this.repository.findOneJobById(request.getModel().getInteger("job.id"));
		}

		result.setJob(job);

		int auditorId;
		auditorId = request.getPrincipal().getActiveRoleId();
		Auditor auditor = this.repository.findOneAuditorById(auditorId);
		result.setAuditor(auditor);

		return result;
	}

	@Override
	public void validate(final Request<AuditRecord> request, final AuditRecord entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

	}

	@Override
	public void create(final Request<AuditRecord> request, final AuditRecord entity) {
		assert request != null;
		assert entity != null;

		Date moment;
		moment = new Date(System.currentTimeMillis() - 1);
		entity.setMoment(moment);

		this.repository.save(entity);

	}

}
