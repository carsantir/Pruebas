
package acme.features.authenticated.job;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.jobs.Job;
import acme.entities.roles.Employer;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Authenticated;
import acme.framework.entities.UserAccount;
import acme.framework.services.AbstractShowService;

@Service
public class AuthenticatedJobShowService implements AbstractShowService<Authenticated, Job> {

	@Autowired
	AuthenticatedJobRepository repository;


	@Override
	public boolean authorise(final Request<Job> request) {
		assert request != null;

		boolean result;
		int jobId;
		Job job;

		jobId = request.getModel().getInteger("id");
		job = this.repository.findOneJobById(jobId);

		Calendar actual = new GregorianCalendar();

		Date fechaActual = actual.getTime();
		result = !job.isDraft() && job.getDeadline().after(fechaActual);

		return result;
	}
	@Override
	public void unbind(final Request<Job> request, final Job entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "reference", "title", "deadline");
		request.unbind(entity, model, "salary", "moreInfo", "draft", "description", "id", "employer.userAccount.username");
	}
	@Override
	public Job findOne(final Request<Job> request) {
		assert request != null;

		Job result;
		int id;

		id = request.getModel().getInteger("id");
		request.getModel().setAttribute("idJob", id);
		result = this.repository.findOneJobById(id);

		UserAccount userAccount = this.repository.findUserAccount(id);
		Employer employer = this.repository.findEmployer(id);

		employer.setUserAccount(userAccount);
		result.setEmployer(employer);

		return result;
	}
}
