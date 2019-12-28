
package acme.features.authenticated.message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.messageThreads.CanParticipate;
import acme.entities.messageThreads.Message;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Authenticated;
import acme.framework.services.AbstractShowService;

@Service
public class AuthenticatedMessageShowService implements AbstractShowService<Authenticated, Message> {

	@Autowired
	AuthenticatedMessageRepository repository;


	@Override
	public boolean authorise(final Request<Message> request) {
		assert request != null;

		int messageId, messageThreadId;
		Message message;
		CanParticipate userInvolved;

		messageId = request.getModel().getInteger("id");
		message = this.repository.findOneMessageById(messageId);
		messageThreadId = message.getMessageThread().getId();
		userInvolved = this.repository.findOneUserInvolvedById(request.getPrincipal().getActiveRoleId(), messageThreadId);

		if (userInvolved != null) {
			return true;
		} else {
			return false;
		}

	}

	@Override
	public void unbind(final Request<Message> request, final Message entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "title", "moment", "tags", "body", "authenticated.userAccount.username", "messageThread.title");
	}
	@Override
	public Message findOne(final Request<Message> request) {
		assert request != null;

		Message result;
		int id;

		id = request.getModel().getInteger("id");
		result = this.repository.findOneMessageById(id);

		Authenticated au = this.repository.findAuthenticated(result.getAuthenticated().getId());

		result.setAuthenticated(au);
		return result;
	}
}
