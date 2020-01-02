
package acme.features.auditor.job;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.jobs.Job;
import acme.entities.roles.Auditor;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Principal;
import acme.framework.services.AbstractListService;

@Service
public class AuditorJobListNotWrittenService implements AbstractListService<Auditor, Job> {

	@Autowired
	AuditorJobRepository repository;


	@Override
	public boolean authorise(final Request<Job> request) {
		assert request != null;

		return true;
	}
	@Override
	public void unbind(final Request<Job> request, final Job entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "reference", "title", "deadline", "employer.userAccount.username");
	}
	@Override
	public Collection<Job> findMany(final Request<Job> request) {
		assert request != null;
		Collection<Job> result;
		Principal principal;

		principal = request.getPrincipal();
		Calendar actual = new GregorianCalendar();

		Date fechaActual = actual.getTime();
		result = this.repository.findManyByAuditorId(principal.getActiveRoleId(), fechaActual);

		result.stream().forEach(j -> j.setEmployer(this.repository.findEmployer(j.getId())));

		return result;
	}
}
