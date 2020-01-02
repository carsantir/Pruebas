
package acme.features.employer.duty;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.jobs.Duty;
import acme.entities.jobs.Job;
import acme.entities.roles.Employer;
import acme.framework.components.Errors;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Principal;
import acme.framework.services.AbstractCreateService;

@Service
public class EmployerDutyCreateService implements AbstractCreateService<Employer, Duty> {

	@Autowired
	EmployerDutyRepository repository;


	@Override
	public boolean authorise(final Request<Duty> request) {
		assert request != null;

		boolean result;

		Job job;

		Employer employer;
		Principal principal;

		int id;
		String url = request.getServletRequest().getQueryString();

		if (url != null) {
			String[] aux = url.split("jobId=");
			id = Integer.parseInt(aux[1]);

			job = this.repository.findJobById(id);

		} else {
			job = this.repository.findJobById(request.getModel().getInteger("job.id"));

		}
		employer = job.getEmployer();
		principal = request.getPrincipal();
		result = employer.getUserAccount().getId() == principal.getAccountId() && job.isDraft();

		return result;
	}

	@Override
	public void bind(final Request<Duty> request, final Duty entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		request.bind(entity, errors);
	}

	@Override
	public void unbind(final Request<Duty> request, final Duty entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "title", "description", "percentage", "job.id", "job.draft");
	}

	@Override
	public Duty instantiate(final Request<Duty> request) {
		Duty result;
		Job job;

		result = new Duty();

		int id;
		String url = request.getServletRequest().getQueryString();

		if (url != null) {
			String[] aux = url.split("jobId=");
			id = Integer.parseInt(aux[1]);

			job = this.repository.findJobById(id);

		} else {
			job = this.repository.findJobById(request.getModel().getInteger("job.id"));

		}

		result.setJob(job);
		return result;
	}

	@Override
	public void validate(final Request<Duty> request, final Duty entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		Job job;

		int id;
		String url = request.getServletRequest().getQueryString();

		if (url != null) {
			String[] aux = url.split("jobId=");
			id = Integer.parseInt(aux[1]);

			job = this.repository.findJobById(id);

		} else {
			job = this.repository.findJobById(request.getModel().getInteger("job.id"));

		}

		Collection<Duty> duties = this.repository.findManyAllFromJob(job.getId());

		if (entity.getPercentage() != null) {
			boolean isUpToHundred = duties.stream().mapToDouble(d -> d.getPercentage()).sum() + entity.getPercentage() <= 100.00;
			errors.state(request, isUpToHundred, "percentage", "employer.duty.error.up-to-hundred");

			boolean isMoreThanCero = entity.getPercentage() > 0;
			errors.state(request, isMoreThanCero, "percentage", "employer.duty.error.more-than-cero");
		}

	}

	@Override
	public void create(final Request<Duty> request, final Duty entity) {

		this.repository.save(entity);
	}

}
