
package acme.features.authenticated.message;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.messageThreads.CanParticipate;
import acme.entities.messageThreads.Message;
import acme.framework.components.Errors;
import acme.framework.components.HttpMethod;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Authenticated;
import acme.framework.entities.Principal;
import acme.framework.services.AbstractCreateService;

@Service
public class AuthenticatedMessageCreateService implements AbstractCreateService<Authenticated, Message> {

	@Autowired
	private AuthenticatedMessageRepository repository;


	@Override
	public boolean authorise(final Request<Message> request) {
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
		CanParticipate m = this.repository.findOneUserInvolvedById(principal.getActiveRoleId(), id);
		if (m != null) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void bind(final Request<Message> request, final Message entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		request.bind(entity, errors, "moment");

	}

	@Override
	public void unbind(final Request<Message> request, final Message entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "title", "tags", "body", "messageThread.id");

		if (request.isMethod(HttpMethod.GET)) {
			model.setAttribute("checkbox", "false");
		} else {
			request.transfer(model, "checkbox");
		}

	}

	@Override
	public Message instantiate(final Request<Message> request) {
		assert request != null;

		Message result;
		result = new Message();

		int id;
		String url = request.getServletRequest().getQueryString();
		if (url != null) {
			String[] aux = url.split("id=");
			id = Integer.parseInt(aux[1]);
		} else {
			id = request.getModel().getInteger("messageThread.id");
		}
		result.setAuthenticated(this.repository.findAuthenticated(request.getPrincipal().getActiveRoleId()));
		result.setMessageThread(this.repository.findOneMessageThreadById(id));
		return result;
	}

	@Override
	public void validate(final Request<Message> request, final Message entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		boolean isAccepted;

		isAccepted = request.getModel().getBoolean("checkbox");
		errors.state(request, isAccepted, "checkbox", "authenticated.message.error.checkbox");

		boolean titleSpam, tagsSpam, bodySpam;

		String spamWords = this.repository.findConfigurationParameters().stream().findFirst().get().getSpamWords();
		Double threshold = this.repository.findConfigurationParameters().stream().findFirst().get().getSpamThreshold();

		String[] spamArray = spamWords.toLowerCase().split(",");

		double numSpamTitle = 0;
		double numSpamTags = 0;
		double numSpamBody = 0;

		String title = entity.getTitle().toLowerCase();
		String tags = entity.getTags().toLowerCase();
		String body = entity.getBody().toLowerCase();

		if (entity.getTitle() != null && entity.getTags() != null && entity.getBody() != null) {
			for (String element : spamArray) {

				while (title.indexOf(element) > -1) {
					title = title.substring(title.indexOf(element) + element.length(), title.length());
					numSpamTitle++;
				}

				while (tags.indexOf(element) > -1) {
					tags = tags.substring(tags.indexOf(element) + element.length(), tags.length());
					numSpamTags++;
				}

				while (body.indexOf(element) > -1) {
					body = body.substring(body.indexOf(element) + element.length(), body.length());
					numSpamBody++;
				}
			}

			titleSpam = numSpamTitle / entity.getTitle().split(" ").length < threshold;
			errors.state(request, titleSpam, "title", "authenticated.message.error.spam");

			tagsSpam = numSpamTags / entity.getTags().split(" ").length < threshold;
			errors.state(request, tagsSpam, "tags", "authenticated.message.error.spam");

			bodySpam = numSpamBody / entity.getBody().split(" ").length < threshold;
			errors.state(request, bodySpam, "body", "authenticated.message.error.spam");

		}
	}

	@Override
	public void create(final Request<Message> request, final Message entity) {
		assert request != null;
		assert entity != null;

		Date moment;

		moment = new Date(System.currentTimeMillis() - 1);
		entity.setMoment(moment);

		this.repository.save(entity);

	}

}
