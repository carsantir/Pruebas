
package acme.features.authenticated.canParticipate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.messageThreads.CanParticipate;
import acme.entities.messageThreads.MessageThread;
import acme.framework.components.Errors;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Authenticated;
import acme.framework.services.AbstractDeleteService;

@Service
public class AuthenticatedCanParticipateDeleteService implements AbstractDeleteService<Authenticated, CanParticipate> {

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

		MessageThread mt = cp.getMessageThread();
		Authenticated creator = mt.getAuthenticated();

		result = creator.getId() != cp.getAuthenticated().getId() && request.getPrincipal().getActiveRoleId() == cp.getMessageThread().getAuthenticated().getId();

		return result;
	}

	@Override
	public void bind(final Request<CanParticipate> request, final CanParticipate entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		request.bind(entity, errors);

	}

	@Override
	public void unbind(final Request<CanParticipate> request, final CanParticipate entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "authenticated.userAccount.username", "messageThread.title");

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

	@Override
	public void validate(final Request<CanParticipate> request, final CanParticipate entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

	}

	@Override
	public void delete(final Request<CanParticipate> request, final CanParticipate entity) {
		assert request != null;
		assert entity != null;

		this.repository.delete(entity);

	}

}
