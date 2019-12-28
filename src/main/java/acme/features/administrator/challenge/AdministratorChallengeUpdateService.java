
package acme.features.administrator.challenge;

import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.challenges.Challenge;
import acme.framework.components.Errors;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Administrator;
import acme.framework.services.AbstractUpdateService;

@Service
public class AdministratorChallengeUpdateService implements AbstractUpdateService<Administrator, Challenge> {

	@Autowired
	private AdministratorChallengeRepository repository;


	@Override
	public boolean authorise(final Request<Challenge> request) {
		assert request != null;
		return true;
	}

	@Override
	public void bind(final Request<Challenge> request, final Challenge entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		request.bind(entity, errors);

	}

	@Override
	public void unbind(final Request<Challenge> request, final Challenge entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "title", "deadline", "description", "goalGold", "rewardGold", "goalSilver", "rewardSilver", "goalBronze", "rewardBronze");

	}

	@Override
	public Challenge findOne(final Request<Challenge> request) {
		assert request != null;

		Challenge result;
		int id;

		id = request.getModel().getInteger("id");
		result = this.repository.findOneById(id);
		return result;
	}

	@Override
	public void validate(final Request<Challenge> request, final Challenge entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		boolean isEuroGold, isEuroSilver, isEuroBronze;

		if (entity.getRewardGold() != null) {
			isEuroGold = entity.getRewardGold().getCurrency().equals("€") || entity.getRewardGold().getCurrency().equals("EUR");
			errors.state(request, isEuroGold, "rewardGold", "administrator.challenge.error.must-be-euro");
		}

		if (entity.getRewardSilver() != null) {
			isEuroSilver = entity.getRewardSilver().getCurrency().equals("€") || entity.getRewardSilver().getCurrency().equals("EUR");
			errors.state(request, isEuroSilver, "rewardSilver", "administrator.challenge.error.must-be-euro");
		}

		if (entity.getRewardBronze() != null) {
			isEuroBronze = entity.getRewardBronze().getCurrency().equals("€") || entity.getRewardBronze().getCurrency().equals("EUR");
			errors.state(request, isEuroBronze, "rewardBronze", "administrator.challenge.error.must-be-euro");
		}

		Date deadLineMoment;
		Boolean isFutureDate;

		deadLineMoment = request.getModel().getDate("deadline");

		if (deadLineMoment != null) {
			isFutureDate = deadLineMoment.after(Calendar.getInstance().getTime());
			errors.state(request, isFutureDate, "deadline", "administrator.challenge.error.must-be-future");
		}
	}

	@Override
	public void update(final Request<Challenge> request, final Challenge entity) {
		assert request != null;
		assert entity != null;

		this.repository.save(entity);

	}

}
