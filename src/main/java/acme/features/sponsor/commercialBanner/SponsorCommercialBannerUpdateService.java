
package acme.features.sponsor.commercialBanner;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.banners.CommercialBanner;
import acme.entities.roles.Sponsor;
import acme.framework.components.Errors;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.services.AbstractUpdateService;

@Service
public class SponsorCommercialBannerUpdateService implements AbstractUpdateService<Sponsor, CommercialBanner> {

	@Autowired
	SponsorCommercialBannerRepository repository;


	@Override
	public boolean authorise(final Request<CommercialBanner> request) {
		assert request != null;

		CommercialBanner banner;
		banner = this.repository.findOneById(request.getModel().getInteger("id"));

		int sponsorId, bannerSponsorId;
		boolean result;

		sponsorId = request.getPrincipal().getActiveRoleId();
		bannerSponsorId = banner.getSponsor().getId();

		result = sponsorId == bannerSponsorId;

		return result;
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

		boolean sloganSpam;

		String spamWords = this.repository.findConfigurationParameters().stream().findFirst().get().getSpamWords();
		Double threshold = this.repository.findConfigurationParameters().stream().findFirst().get().getSpamThreshold();

		String[] spamArray = spamWords.toLowerCase().split(",");

		double numSpamSlogan = 0;

		String slogan = entity.getSlogan().toLowerCase();

		if (entity.getSlogan() != null) {
			for (String element : spamArray) {

				while (slogan.indexOf(element) > -1) {
					slogan = slogan.substring(slogan.indexOf(element) + element.length(), slogan.length());
					numSpamSlogan++;
				}

			}

			sloganSpam = numSpamSlogan / entity.getSlogan().split(" ").length < threshold;
			errors.state(request, sloganSpam, "slogan", "sponsor.commercialbanner.error.spam");

		}

		if (entity.getExpirationDate() != null && !entity.getExpirationDate().isEmpty()) {
			boolean isFormat = entity.getExpirationDate().matches("^(0[1-9]{1}|1[0-2]{1})/\\d{4}$");
			errors.state(request, isFormat, "expirationDate", "sponsor.commercial-banner.error.invalid-format");

			if (isFormat) {
				boolean isFuture;
				Calendar hoy = new GregorianCalendar();
				String[] fecha = entity.getExpirationDate().split("/");
				Integer year = Integer.parseInt(fecha[1]);
				Integer month = Integer.parseInt(fecha[0]);
				isFuture = year > hoy.get(Calendar.YEAR) || hoy.get(Calendar.YEAR) == year && month > hoy.get(Calendar.MONTH);
				errors.state(request, isFuture, "expirationDate", "sponsor.commercial-banner.error.expiration-date");
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
