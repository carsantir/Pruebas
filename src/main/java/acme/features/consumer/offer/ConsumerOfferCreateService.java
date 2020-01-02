
package acme.features.consumer.offer;

import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.offers.Offer;
import acme.entities.roles.Consumer;
import acme.framework.components.Errors;
import acme.framework.components.HttpMethod;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.services.AbstractCreateService;

@Service
public class ConsumerOfferCreateService implements AbstractCreateService<Consumer, Offer> {

	@Autowired
	ConsumerOfferRepository repository;


	@Override
	public boolean authorise(final Request<Offer> request) {
		assert request != null;
		return true;
	}

	@Override
	public void bind(final Request<Offer> request, final Offer entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		request.bind(entity, errors, "moment");

	}

	@Override
	public void unbind(final Request<Offer> request, final Offer entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "title", "deadline", "text", "minPrice", "maxPrice", "ticker");

		if (request.isMethod(HttpMethod.GET)) {
			model.setAttribute("checkbox", "false");
		} else {
			request.transfer(model, "checkbox");
		}
	}

	@Override
	public Offer instantiate(final Request<Offer> request) {
		Offer result;

		result = new Offer();

		return result;
	}

	@Override
	public void validate(final Request<Offer> request, final Offer entity, final Errors errors) {

		boolean isAccepted;

		isAccepted = request.getModel().getBoolean("checkbox");
		errors.state(request, isAccepted, "checkbox", "consumer.offer.error.checkbox");

		Date deadLineMoment;
		Boolean isFutureDate;

		deadLineMoment = request.getModel().getDate("deadline");

		if (deadLineMoment != null) {
			isFutureDate = deadLineMoment.after(Calendar.getInstance().getTime());
			errors.state(request, isFutureDate, "deadline", "consumer.offer.error.must-be-future");
		}

		boolean isDuplicated;

		isDuplicated = this.repository.findOneByTicker(entity.getTicker()) != null;
		errors.state(request, !isDuplicated, "ticker", "consumer.offer.error.duplicated");

		boolean isEuroMin;

		if (entity.getMinPrice() != null) {
			isEuroMin = entity.getMinPrice().getCurrency().equals("€") || entity.getMinPrice().getCurrency().equals("EUR");
			errors.state(request, isEuroMin, "minPrice", "consumer.offer.error.must-be-euro");
		}

		boolean isEuroMax;

		if (entity.getMaxPrice() != null) {
			isEuroMax = entity.getMaxPrice().getCurrency().equals("€") || entity.getMaxPrice().getCurrency().equals("EUR");
			errors.state(request, isEuroMax, "maxPrice", "consumer.offer.error.must-be-euro");
		}

		boolean isPriceInRange;

		if (entity.getMinPrice() != null && entity.getMaxPrice() != null) {
			isPriceInRange = entity.getMinPrice().getAmount() < entity.getMaxPrice().getAmount();
			errors.state(request, isPriceInRange, "minPrice", "consumer.offer.error.must-be-in-range");
		}

	}

	@Override
	public void create(final Request<Offer> request, final Offer entity) {
		Date moment;

		moment = new Date(System.currentTimeMillis() - 1);
		entity.setMoment(moment);
		this.repository.save(entity);

	}

}
