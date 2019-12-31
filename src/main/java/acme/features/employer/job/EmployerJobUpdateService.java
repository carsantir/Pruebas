
package acme.features.employer.job;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.jobs.Duty;
import acme.entities.jobs.Job;
import acme.entities.roles.Employer;
import acme.framework.components.Errors;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Principal;
import acme.framework.services.AbstractUpdateService;

@Service
public class EmployerJobUpdateService implements AbstractUpdateService<Employer, Job> {

	@Autowired
	EmployerJobRepository repository;


	@Override
	public boolean authorise(final Request<Job> request) {
		assert request != null;

		boolean result;
		int jobId;
		Job job;
		Employer employer;
		Principal principal;

		jobId = request.getModel().getInteger("id");
		job = this.repository.findOneJobById(jobId);
		employer = job.getEmployer();
		principal = request.getPrincipal();
		result = employer.getUserAccount().getId() == principal.getAccountId() && job.isDraft();

		return result;
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

		request.unbind(entity, model, "reference", "title", "deadline", "salary", "moreInfo", "description", "draft");
	}

	@Override
	public Job findOne(final Request<Job> request) {
		assert request != null;
		Job result;
		int id;

		id = request.getModel().getInteger("id");
		result = this.repository.findOneJobById(id);

		return result;
	}

	@Override
	public void validate(final Request<Job> request, final Job entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		boolean isDuplicated;
		isDuplicated = this.repository.findJobByReference(entity.getReference()) != null && !entity.getReference().equals(this.repository.findOneJobById(entity.getId()).getReference());
		errors.state(request, !isDuplicated, "reference", "employer.job.error.duplicated");

		boolean workloadFinal;
		Collection<Duty> duties = this.repository.findDutiesFromJob(entity.getId());
		if (!entity.isDraft()) {
			workloadFinal = duties.stream().mapToDouble(d -> d.getPercentage()).sum() == 100.00;
			errors.state(request, workloadFinal, "draft", "employer.job.error.workload");
		}

		// Contar spam de un job

		// Si no es un borrador, se debe comprobar que no sea spam antes de publicarlo

		if (!request.getModel().getBoolean("draft")) {

			String spamWords = this.repository.findConfigurationParameters().stream().findFirst().get().getSpamWords();
			String[] spamArray = spamWords.toLowerCase().split(",");
			Double threshold = this.repository.findConfigurationParameters().stream().findFirst().get().getSpamThreshold();

			double numSpamTitle = 0;
			double numSpamReference = 0;
			double numSpamDescription = 0;

			String title = entity.getTitle().toLowerCase();
			String reference = entity.getReference().toLowerCase();
			String description = entity.getDescription().toLowerCase();

			if (entity.getTitle() != null && entity.getReference() != null && entity.getDescription() != null) {

				for (String element : spamArray) {

					while (title.indexOf(element) > -1) {
						title = title.substring(title.indexOf(element) + element.length(), title.length());
						numSpamTitle++;
					}

					while (reference.indexOf(element) > -1) {
						reference = reference.substring(reference.indexOf(element) + element.length(), reference.length());
						numSpamReference++;
					}

					while (description.indexOf(element) > -1) {
						description = description.substring(description.indexOf(element) + element.length(), description.length());
						numSpamDescription++;
					}
				}

				boolean titleSpam, referenceSpam, descriptionSpam;

				titleSpam = numSpamTitle / entity.getTitle().split(" ").length < threshold;
				errors.state(request, titleSpam, "title", "employer.job.error.spam");

				referenceSpam = numSpamReference / entity.getReference().split(" ").length < threshold;
				errors.state(request, referenceSpam, "reference", "employer.job.error.spam");

				descriptionSpam = numSpamDescription / entity.getDescription().split(" ").length < threshold;
				errors.state(request, descriptionSpam, "description", "employer.job.error.spam");

			}

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

			boolean hasDescriptor;

			hasDescriptor = !entity.isDraft() && entity.getDescription() != null && !entity.isDraft() && !entity.getDescription().isEmpty() && !entity.isDraft() && !entity.getDescription().equals("");
			errors.state(request, hasDescriptor, "description", "employer.job.error.must-have-description");

			// Contar spam en las duties

			Collection<Duty> jobDuties = this.repository.findDutiesFromJob(entity.getId());
			for (Duty duty : jobDuties) {

				numSpamTitle = 0;
				numSpamDescription = 0;

				String dutyTitle = duty.getTitle().toLowerCase();
				String dutyDescription = duty.getDescription().toLowerCase();

				if (duty.getTitle() != null && duty.getDescription() != null) {
					for (String element : spamArray) {

						while (dutyTitle.indexOf(element) > -1) {
							dutyTitle = dutyTitle.substring(dutyTitle.indexOf(element) + element.length(), dutyTitle.length());
							numSpamTitle++;
						}

						while (dutyDescription.indexOf(element) > -1) {
							dutyDescription = dutyDescription.substring(dutyDescription.indexOf(element) + element.length(), dutyDescription.length());
							numSpamDescription++;
						}
					}

					boolean isSpam;

					isSpam = numSpamTitle / entity.getTitle().split(" ").length < threshold && numSpamDescription / entity.getDescription().split(" ").length < threshold;
					errors.state(request, isSpam, "draft", "employer.job.duty.error.spam");

				}

				if (errors.hasErrors("draft")) {
					break;
				}
			}
		}
	}

	@Override
	public void update(final Request<Job> request, final Job entity) {

		this.repository.save(entity);
	}

}
