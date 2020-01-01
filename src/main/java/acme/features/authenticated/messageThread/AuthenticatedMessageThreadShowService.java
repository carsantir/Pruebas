
package acme.features.authenticated.messageThread;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.messageThreads.CanParticipate;
import acme.entities.messageThreads.MessageThread;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Authenticated;
import acme.framework.entities.Principal;
import acme.framework.services.AbstractShowService;

@Service
public class AuthenticatedMessageThreadShowService implements AbstractShowService<Authenticated, MessageThread> {

	@Autowired
	AuthenticatedMessageThreadRepository repository;


	@Override
	public boolean authorise(final Request<MessageThread> request) {
		assert request != null;

		Principal principal;
		principal = request.getPrincipal();

		int id;
		String url = request.getServletRequest().getQueryString();
		if (url != null) {
			String[] aux = url.split("id=");
			id = Integer.parseInt(aux[1]);
		} else {
			id = request.getModel().getInteger("messageThread.id");
		}

		CanParticipate e = this.repository.findOneMessageThreadByUserAccountId(principal.getActiveRoleId(), id);
		if (e != null) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void unbind(final Request<MessageThread> request, final MessageThread entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		Principal principal;
		principal = request.getPrincipal();
		request.unbind(entity, model, "title", "moment", "authenticated.id");

		if (entity.getAuthenticated().getId() != principal.getActiveRoleId()) {
			model.setAttribute("isNotCreator", true);
		} else {
			model.setAttribute("isNotCreator", false);
		}

	}
	@Override
	public MessageThread findOne(final Request<MessageThread> request) {
		assert request != null;

		MessageThread result;
		int id;

		id = request.getModel().getInteger("id");
		result = this.repository.findOneMessageThreadById(id);

		return result;
	}
}
