
package acme.features.authenticated.canParticipate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.messageThreads.CanParticipate;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Authenticated;
import acme.framework.services.AbstractShowService;

@Service
public class AuthenticatedCanParticipateShowService implements AbstractShowService<Authenticated, CanParticipate> {

	@Autowired
	private AuthenticatedCanParticipateRepository repository;


	@Override
	public boolean authorise(final Request<CanParticipate> request) {
		assert request != null;

		boolean result;

		int canParticipateId;
		CanParticipate cp;

		canParticipateId = request.getModel().getInteger("id");
		cp = this.repository.findOneById(canParticipateId);

		result = request.getPrincipal().getActiveRoleId() == cp.getMessageThread().getAuthenticated().getId();

		return result;
	}

	@Override
	public void unbind(final Request<CanParticipate> request, final CanParticipate entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "authenticated.userAccount.username", "messageThread.title");

		if (entity.getMessageThread().getAuthenticated().getId() != entity.getAuthenticated().getId()) {
			model.setAttribute("isNotCreator", true);
		} else {
			model.setAttribute("isNotCreator", false);
		}

	}

	@Override
	public CanParticipate findOne(final Request<CanParticipate> request) {
		assert request != null;

		int canParticipateId;
		CanParticipate cp;

		canParticipateId = request.getModel().getInteger("id");
		cp = this.repository.findOneById(canParticipateId);

		return cp;
	}

}
