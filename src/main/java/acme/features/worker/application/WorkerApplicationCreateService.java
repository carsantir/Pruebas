
package acme.features.worker.application;

import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.jobs.Application;
import acme.entities.jobs.Job;
import acme.entities.jobs.Status;
import acme.entities.roles.Worker;
import acme.framework.components.Errors;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.services.AbstractCreateService;

@Service
public class WorkerApplicationCreateService implements AbstractCreateService<Worker, Application> {

	@Autowired
	private WorkerApplicationRepository repository;


	@Override
	public boolean authorise(final Request<Application> request) {
		assert request != null;

		boolean result;
		int id;
		Job job;

		String url = request.getServletRequest().getQueryString();

		if (url != null) {
			String[] aux = url.split("jobId=");
			id = Integer.parseInt(aux[1]);
			job = this.repository.findOneJobById(id);
		} else {
			job = this.repository.findOneJobById(request.getModel().getInteger("job.id"));
		}

		result = !job.isDraft() && job.getDeadline().after(Calendar.getInstance().getTime());

		return result;
	}

	@Override
	public void bind(final Request<Application> request, final Application entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		request.bind(entity, errors, "moment", "justification");

	}

	@Override
	public void unbind(final Request<Application> request, final Application entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "job.id", "referenceNumber", "statement", "skills", "qualifications", "answer", "propiedad3", "passwordPropiedad3", "job.propiedad1");

		boolean hasPropiedad1;

		Job job;

		job = entity.getJob();

		hasPropiedad1 = job.getPropiedad1() != null && !job.getPropiedad1().isEmpty();

		model.setAttribute("hasPropiedad1", hasPropiedad1);

	}

	@Override
	public Application instantiate(final Request<Application> request) {
		Application result;
		result = new Application();
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

		int workerId;
		workerId = request.getPrincipal().getActiveRoleId();
		Worker worker = this.repository.findOneWorkerById(workerId);
		result.setWorker(worker);

		result.setStatus(Status.PENDING);
		result.setJustification(null);

		result.setSkills(this.repository.findOneWorkerById(request.getPrincipal().getActiveRoleId()).getSkillsRecord());
		result.setQualifications(this.repository.findOneWorkerById(request.getPrincipal().getActiveRoleId()).getQualificationsRecord());
		return result;

	}

	@Override
	public void validate(final Request<Application> request, final Application entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		boolean isDuplicated;
		isDuplicated = this.repository.findApplicationByReference(entity.getReferenceNumber()) != null;
		errors.state(request, !isDuplicated, "referenceNumber", "worker.application.error.duplicated");

		boolean passwordCorrect;
		boolean hasPropiedad3 = entity.getPropiedad3() != null && !entity.getPropiedad3().isEmpty();

		if (hasPropiedad3) {
			boolean hasAnswer = entity.getAnswer() != null && !entity.getAnswer().isEmpty();
			errors.state(request, hasAnswer, "answer", "worker.application.error.answer");
		}

		if (!(entity.getPasswordPropiedad3() == null || entity.getPasswordPropiedad3().isEmpty())) {
			errors.state(request, hasPropiedad3, "propiedad3", "worker.application.error.propiedad3");

			passwordCorrect = entity.getPasswordPropiedad3().matches("^(?=(.*[A-Za-z]){3})(?=(.*[0-9]){3})(?=(.*[¡!\\\"\\\\#$%&'()*+,\\r\\n\\\\./:;<=>¿?@\\\\[\\r\\n\\\\]^_‘{|}~]){3}).+") && entity.getPasswordPropiedad3().length() >= 8;
			errors.state(request, passwordCorrect, "passwordPropiedad3", "worker.application.error.password");
		}

	}

	@Override
	public void create(final Request<Application> request, final Application entity) {
		assert request != null;
		assert entity != null;

		Date moment;
		moment = new Date(System.currentTimeMillis() - 1);
		entity.setMoment(moment);

		this.repository.save(entity);

	}

}
