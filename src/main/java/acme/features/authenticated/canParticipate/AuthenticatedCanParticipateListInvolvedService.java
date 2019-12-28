
package acme.features.authenticated.canParticipate;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.messageThreads.CanParticipate;
import acme.entities.messageThreads.MessageThread;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Authenticated;
import acme.framework.services.AbstractListService;

@Service
public class AuthenticatedCanParticipateListInvolvedService implements AbstractListService<Authenticated, CanParticipate> {

	@Autowired
	private AuthenticatedCanParticipateRepository repository;


	@Override
	public boolean authorise(final Request<CanParticipate> request) {
		assert request != null;

		boolean result;
		int id;
		MessageThread messageThread;

		String url = request.getServletRequest().getQueryString();
		String[] aux = url.split("=");
		id = Integer.parseInt(aux[1]);
		messageThread = this.repository.findOneMessageThreadById(id);

		result = request.getPrincipal().getActiveRoleId() == messageThread.getAuthenticated().getId();

		return result;
	}

	@Override
	public void unbind(final Request<CanParticipate> request, final CanParticipate entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "authenticated.userAccount.username", "messageThread.title");

	}

	@Override
	public Collection<CanParticipate> findMany(final Request<CanParticipate> request) {
		assert request != null;

		int messageThreadId;
		Collection<CanParticipate> cp;

		String url = request.getServletRequest().getQueryString();

		String[] aux = url.split("=");
		messageThreadId = Integer.parseInt(aux[1]);

		cp = this.repository.findManyByMessageThreadId(messageThreadId);

		return cp;
	}

}
