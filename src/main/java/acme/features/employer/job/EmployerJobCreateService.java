
package acme.features.employer.job;

import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.jobs.Job;
import acme.entities.roles.Employer;
import acme.framework.components.Errors;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.services.AbstractCreateService;

@Service
public class EmployerJobCreateService implements AbstractCreateService<Employer, Job> {

	@Autowired
	EmployerJobRepository repository;


	@Override
	public boolean authorise(final Request<Job> request) {
		assert request != null;

		return true;
	}

	@Override
	public void bind(final Request<Job> request, final Job entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		request.bind(entity, errors);
	}

	@Override
	public void unbind(final Request<Job> request, final Job entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "reference", "title", "deadline", "salary", "moreInfo", "description", "draft", "propiedad1", "propiedad2");
	}

	@Override
	public Job instantiate(final Request<Job> request) {
		Job result;

		result = new Job();

		result.setEmployer(this.repository.findEmployerbyEmployerId(request.getPrincipal().getActiveRoleId()));
		result.setDraft(true);
		return result;
	}

	@Override
	public void validate(final Request<Job> request, final Job entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		Date deadLineMoment;
		Boolean isFutureDate;

		deadLineMoment = request.getModel().getDate("deadline");

		if (deadLineMoment != null) {
			isFutureDate = deadLineMoment.after(Calendar.getInstance().getTime());
			errors.state(request, isFutureDate, "deadline", "employer.job.error.future");
		}

		boolean isEuro;

		if (entity.getSalary() != null) {
			isEuro = entity.getSalary().getCurrency().equals("€") || entity.getSalary().getCurrency().equals("EUR");
			errors.state(request, isEuro, "salary", "employer.job.error.must-be-euro");
		}

		boolean isDuplicated;
		isDuplicated = this.repository.findJobByReference(entity.getReference()) != null;
		errors.state(request, !isDuplicated, "reference", "employer.job.error.duplicated");

		if (entity.getPropiedad2() != null && !entity.getPropiedad2().isEmpty()) {
			boolean hasPropiedad1;
			String propiedad1 = entity.getPropiedad1();
			hasPropiedad1 = propiedad1 != null && !propiedad1.isEmpty();
			errors.state(request, hasPropiedad1, "propiedad1", "employer.job.error.propiedad1");
		}
	}

	@Override
	public void create(final Request<Job> request, final Job entity) {

		this.repository.save(entity);
	}

}
