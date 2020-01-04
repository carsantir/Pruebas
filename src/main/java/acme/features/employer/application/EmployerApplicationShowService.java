
package acme.features.employer.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.jobs.Application;
import acme.entities.jobs.Job;
import acme.entities.roles.Employer;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Principal;
import acme.framework.services.AbstractShowService;

@Service
public class EmployerApplicationShowService implements AbstractShowService<Employer, Application> {

	@Autowired
	private EmployerApplicationRepository repository;


	@Override
	public boolean authorise(final Request<Application> request) {
		assert request != null;

		boolean result;
		int applicationId;
		Job job;
		Application application;
		Employer employer;
		Principal principal;

		applicationId = request.getModel().getInteger("id");
		application = this.repository.findOneApplicationById(applicationId);
		job = this.repository.findOneJobById(application.getJob().getId());
		employer = job.getEmployer();
		principal = request.getPrincipal();
		result = employer.getUserAccount().getId() == principal.getAccountId();

		return result;
	}

	@Override
	public void unbind(final Request<Application> request, final Application entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "referenceNumber", "moment", "status", "statement", "skills", "qualifications", "job.title", "job.reference", "worker.userAccount.username", "justification", "answer", "propiedad3", "passwordPropiedad3",
			"job.propiedad1");

		boolean hasPropiedad1;

		Job job;

		job = entity.getJob();

		hasPropiedad1 = job.getPropiedad1() != null && !job.getPropiedad1().isEmpty();

		model.setAttribute("hasPropiedad1", hasPropiedad1);

	}

	@Override
	public Application findOne(final Request<Application> request) {
		assert request != null;

		Application result;
		int id;

		id = request.getModel().getInteger("id");
		result = this.repository.findOneApplicationById(id);
		return result;
	}

}
