
package acme.features.employer.duty;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.jobs.Duty;
import acme.entities.roles.Employer;
import acme.framework.components.Errors;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Principal;
import acme.framework.services.AbstractUpdateService;

@Service
public class EmployerDutyUpdateService implements AbstractUpdateService<Employer, Duty> {

	@Autowired
	EmployerDutyRepository repository;


	@Override
	public boolean authorise(final Request<Duty> request) {
		assert request != null;

		boolean result;

		int dutyId, employerId;
		Principal principal;

		dutyId = request.getModel().getInteger("id");
		employerId = this.repository.findJobFromDutyId(dutyId).getEmployer().getId();
		principal = request.getPrincipal();

		result = employerId == principal.getActiveRoleId() && this.repository.findOneDutyById(dutyId).getJob().isDraft();

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

		request.unbind(entity, model, "title", "description", "percentage", "job.employer.id");
	}

	@Override
	public Duty findOne(final Request<Duty> request) {
		assert request != null;
		Duty result;
		int id;

		id = request.getModel().getInteger("id");
		result = this.repository.findOneDutyById(id);

		return result;
	}

	@Override
	public void validate(final Request<Duty> request, final Duty entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		Collection<Duty> duties = this.repository.findManyAllFromJob(entity.getJob().getId());
		Duty formerDuty = this.repository.findOneDutyById(entity.getId());
		Double formerPercentage = formerDuty.getPercentage();

		if (entity.getPercentage() != null) {
			boolean isUpToHundred = duties.stream().mapToDouble(d -> d.getPercentage()).sum() + entity.getPercentage() - formerPercentage <= 100.00;
			errors.state(request, isUpToHundred, "percentage", "employer.duty.error.up-to-hundred");

			boolean isMoreThanCero = entity.getPercentage() > 0;
			errors.state(request, isMoreThanCero, "percentage", "employer.duty.error.more-than-cero");
		}

	}

	@Override
	public void update(final Request<Duty> request, final Duty entity) {

		this.repository.save(entity);
	}

}
