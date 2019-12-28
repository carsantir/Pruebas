
package acme.features.sponsor.commercialBanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.banners.CommercialBanner;
import acme.entities.roles.Sponsor;
import acme.framework.components.Errors;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.services.AbstractDeleteService;

@Service
public class SponsorCommercialBannerDeleteService implements AbstractDeleteService<Sponsor, CommercialBanner> {

	@Autowired
	SponsorCommercialBannerRepository repository;


	@Override
	public boolean authorise(final Request<CommercialBanner> request) {

		CommercialBanner banner;
		banner = this.repository.findOneById(request.getModel().getInteger("id"));

		int sponsorId, bannerSponsorId;
		boolean isCreator;

		sponsorId = request.getPrincipal().getActiveRoleId();
		bannerSponsorId = banner.getSponsor().getId();

		isCreator = sponsorId == bannerSponsorId;

		return isCreator;
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

		CommercialBanner banner;
		int id;

		id = request.getModel().getInteger("id");
		banner = this.repository.findOneById(id);

		return banner;
	}

	@Override
	public void validate(final Request<CommercialBanner> request, final CommercialBanner entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

	}

	@Override
	public void delete(final Request<CommercialBanner> request, final CommercialBanner entity) {
		assert request != null;
		assert entity != null;

		this.repository.delete(entity);

	}

}
