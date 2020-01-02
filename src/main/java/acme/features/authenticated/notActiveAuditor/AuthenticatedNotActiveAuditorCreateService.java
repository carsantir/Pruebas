
package acme.features.authenticated.notActiveAuditor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.roles.NotActiveAuditor;
import acme.framework.components.Errors;
import acme.framework.components.HttpMethod;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.components.Response;
import acme.framework.entities.Authenticated;
import acme.framework.entities.Principal;
import acme.framework.entities.UserAccount;
import acme.framework.helpers.PrincipalHelper;
import acme.framework.services.AbstractCreateService;

@Service
public class AuthenticatedNotActiveAuditorCreateService implements AbstractCreateService<Authenticated, NotActiveAuditor> {

	@Autowired
	private AuthenticatedNotActiveAuditorRepository repository;


	@Override
	public boolean authorise(final Request<NotActiveAuditor> request) {
		assert request != null;
		Principal principal;
		principal = request.getPrincipal();
		NotActiveAuditor a = this.repository.findOneNotActiveAuditorByUserAccountId(principal.getAccountId());
		if (a == null) {
			return true;
		} else {
			return false;
		}

	}

	@Override
	public void bind(final Request<NotActiveAuditor> request, final NotActiveAuditor entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		request.bind(entity, errors);

	}

	@Override
	public void unbind(final Request<NotActiveAuditor> request, final NotActiveAuditor entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "firm", "responsibilityStatement");

	}

	@Override
	public NotActiveAuditor instantiate(final Request<NotActiveAuditor> request) {
		assert request != null;

		NotActiveAuditor result;
		Principal principal;
		int userAccountId;
		UserAccount userAccount;

		principal = request.getPrincipal();
		userAccountId = principal.getAccountId();
		userAccount = this.repository.findOneUserAccountById(userAccountId);

		result = new NotActiveAuditor();
		result.setEnabled(false);
		result.setUserAccount(userAccount);

		return result;
	}

	@Override
	public void validate(final Request<NotActiveAuditor> request, final NotActiveAuditor entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

	}

	@Override
	public void create(final Request<NotActiveAuditor> request, final NotActiveAuditor entity) {
		assert request != null;
		assert entity != null;

		this.repository.save(entity);

	}
	@Override
	public void onSuccess(final Request<NotActiveAuditor> request, final Response<NotActiveAuditor> response) {
		assert request != null;
		assert response != null;

		if (request.isMethod(HttpMethod.POST)) {
			PrincipalHelper.handleUpdate();
		}
	}

}
