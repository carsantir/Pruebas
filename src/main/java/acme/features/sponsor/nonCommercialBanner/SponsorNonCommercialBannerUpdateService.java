
package acme.features.sponsor.nonCommercialBanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.banners.NonCommercialBanner;
import acme.entities.roles.Sponsor;
import acme.framework.components.Errors;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.services.AbstractUpdateService;

@Service
public class SponsorNonCommercialBannerUpdateService implements AbstractUpdateService<Sponsor, NonCommercialBanner> {

	@Autowired
	SponsorNonCommercialBannerRepository repository;


	@Override
	public boolean authorise(final Request<NonCommercialBanner> request) {
		assert request != null;

		NonCommercialBanner banner;
		banner = this.repository.findNonCommercialBannerById(request.getModel().getInteger("id"));

		int sponsorId, bannerSponsorId;
		boolean result;

		sponsorId = request.getPrincipal().getActiveRoleId();
		bannerSponsorId = banner.getSponsor().getId();

		result = sponsorId == bannerSponsorId;

		return result;
	}

	@Override
	public void bind(final Request<NonCommercialBanner> request, final NonCommercialBanner entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		request.bind(entity, errors);

	}

	@Override
	public void unbind(final Request<NonCommercialBanner> request, final NonCommercialBanner entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "pictureUrl", "slogan", "targetUrl", "jingle");

	}

	@Override
	public NonCommercialBanner findOne(final Request<NonCommercialBanner> request) {
		assert request != null;

		NonCommercialBanner result;
		int id;

		id = request.getModel().getInteger("id");
		result = this.repository.findNonCommercialBannerById(id);

		return result;
	}

	@Override
	public void validate(final Request<NonCommercialBanner> request, final NonCommercialBanner entity, final Errors errors) {
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

		boolean jingleSpam;

		double numSpamJingle = 0;

		String jingle = entity.getJingle().toLowerCase();

		if (entity.getJingle() != null) {
			for (String element : spamArray) {

				while (jingle.indexOf(element) > -1) {
					jingle = jingle.substring(jingle.indexOf(element) + element.length(), jingle.length());
					numSpamJingle++;
				}
			}

			jingleSpam = numSpamJingle / entity.getJingle().split(" ").length < threshold;
			errors.state(request, jingleSpam, "jingle", "sponsor.commercialbanner.error.spam");
		}

	}

	@Override
	public void update(final Request<NonCommercialBanner> request, final NonCommercialBanner entity) {
		assert request != null;
		assert entity != null;

		this.repository.save(entity);

	}

}
