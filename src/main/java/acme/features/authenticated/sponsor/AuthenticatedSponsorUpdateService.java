
package acme.features.authenticated.sponsor;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.roles.Sponsor;
import acme.framework.components.Errors;
import acme.framework.components.HttpMethod;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.components.Response;
import acme.framework.entities.Authenticated;
import acme.framework.entities.Principal;
import acme.framework.helpers.PrincipalHelper;
import acme.framework.services.AbstractUpdateService;

@Service
public class AuthenticatedSponsorUpdateService implements AbstractUpdateService<Authenticated, Sponsor> {

	@Autowired
	private AuthenticatedSponsorRepository repository;


	@Override
	public boolean authorise(final Request<Sponsor> request) {
		assert request != null;
		return true;
	}

	@Override
	public void bind(final Request<Sponsor> request, final Sponsor entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		request.bind(entity, errors);

	}

	@Override
	public void unbind(final Request<Sponsor> request, final Sponsor entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "organisationName", "creditCard", "cvv", "expirationDate");

	}

	@Override
	public Sponsor findOne(final Request<Sponsor> request) {
		assert request != null;

		Sponsor result;
		Principal principal;
		int userAccountId;

		principal = request.getPrincipal();
		userAccountId = principal.getAccountId();

		result = this.repository.findOneSponsorByUserAccountId(userAccountId);

		return result;
	}

	@Override
	public void validate(final Request<Sponsor> request, final Sponsor entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		if (entity.getCreditCard() != null && !entity.getCreditCard().isEmpty()) {
			boolean isCorrect;
			String cardNo = entity.getCreditCard();
			int nDigits = cardNo.length();

			int nSum = 0;
			boolean isSecond = false;
			for (int i = nDigits - 1; i >= 0; i--) {

				int d = cardNo.charAt(i) - '0';

				if (isSecond == true) {
					d = d * 2;
				}

				nSum += d / 10;
				nSum += d % 10;

				isSecond = !isSecond;
			}
			isCorrect = nSum % 10 == 0;
			errors.state(request, isCorrect, "creditCard", "authenticated.sponsor.error.credit-card");
		}

		if (entity.getExpirationDate() != null && !entity.getExpirationDate().isEmpty()) {
			boolean isFormat = entity.getExpirationDate().matches("^(0[1-9]{1}|1[0-2]{1})/\\d{4}$");
			errors.state(request, isFormat, "expirationDate", "authenticated.sponsor.error.invalid-format");

			if (isFormat) {
				boolean isFuture;
				Calendar hoy = new GregorianCalendar();
				String[] fecha = entity.getExpirationDate().split("/");
				Integer year = Integer.parseInt(fecha[1]);
				Integer month = Integer.parseInt(fecha[0]);
				isFuture = year > hoy.get(Calendar.YEAR) || hoy.get(Calendar.YEAR) == year && month > hoy.get(Calendar.MONTH);
				errors.state(request, isFuture, "expirationDate", "authenticated.sponsor.error.expiration-date");
			}
		}
	}

	@Override
	public void update(final Request<Sponsor> request, final Sponsor entity) {
		assert request != null;
		assert entity != null;

		this.repository.save(entity);

	}
	@Override
	public void onSuccess(final Request<Sponsor> request, final Response<Sponsor> response) {
		assert request != null;
		assert response != null;

		if (request.isMethod(HttpMethod.POST)) {
			PrincipalHelper.handleUpdate();
		}
	}

}
