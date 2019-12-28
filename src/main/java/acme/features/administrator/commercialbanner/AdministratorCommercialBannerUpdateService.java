
package acme.features.administrator.commercialbanner;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.banners.CommercialBanner;
import acme.framework.components.Errors;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Administrator;
import acme.framework.services.AbstractUpdateService;

@Service
public class AdministratorCommercialBannerUpdateService implements AbstractUpdateService<Administrator, CommercialBanner> {

	@Autowired
	AdministratorCommercialBannerRepository repository;


	@Override
	public boolean authorise(final Request<CommercialBanner> request) {
		assert request != null;

		return true;
	}

	@Override
	public void bind(final Request<CommercialBanner> request, final CommercialBanner entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		request.bind(entity, errors);

	}

	@Override
	public void unbind(final Request<CommercialBanner> request, final CommercialBanner entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "pictureUrl", "slogan", "targetUrl", "creditCard", "cvv", "expirationDate");
	}

	@Override
	public CommercialBanner findOne(final Request<CommercialBanner> request) {
		assert request != null;

		CommercialBanner result;
		int id;

		id = request.getModel().getInteger("id");
		result = this.repository.findOneById(id);

		return result;
	}

	@Override
	public void validate(final Request<CommercialBanner> request, final CommercialBanner entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		if (entity.getExpirationDate() != null && !entity.getExpirationDate().isEmpty()) {
			boolean isFormat = entity.getExpirationDate().matches("^(0[1-9]{1}|1[0-2]{1})/\\d{4}$");
			errors.state(request, isFormat, "expirationDate", "administrator.commercial-banner.error.invalid-format");

			if (isFormat) {
				boolean isFuture;
				Calendar hoy = new GregorianCalendar();
				String[] fecha = entity.getExpirationDate().split("/");
				Integer year = Integer.parseInt(fecha[1]);
				Integer month = Integer.parseInt(fecha[0]);
				isFuture = year > hoy.get(Calendar.YEAR) || hoy.get(Calendar.YEAR) == year && month > hoy.get(Calendar.MONTH);
				errors.state(request, isFuture, "expirationDate", "administrator.commercial-banner.error.expiration-date");
			}
		}
	}

	@Override
	public void update(final Request<CommercialBanner> request, final CommercialBanner entity) {
		assert request != null;
		assert entity != null;

		this.repository.save(entity);

	}

}
