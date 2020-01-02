
package acme.features.administrator.notActiveAuditor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.roles.Auditor;
import acme.entities.roles.NotActiveAuditor;
import acme.framework.components.Errors;
import acme.framework.components.HttpMethod;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.components.Response;
import acme.framework.entities.Administrator;
import acme.framework.entities.UserAccount;
import acme.framework.helpers.PrincipalHelper;
import acme.framework.services.AbstractUpdateService;

@Service
public class AdministratorNotActiveAuditorUpdateService implements AbstractUpdateService<Administrator, NotActiveAuditor> {

	@Autowired
	AdministratorNotActiveAuditorRepository repository;


	@Override
	public boolean authorise(final Request<NotActiveAuditor> request) {
		assert request != null;

		return true;
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

		request.unbind(entity, model, "firm", "responsibilityStatement", "enabled");

	}

	@Override
	public NotActiveAuditor findOne(final Request<NotActiveAuditor> request) {
		assert request != null;
		NotActiveAuditor result;
		int id;

		id = request.getModel().getInteger("id");
		result = this.repository.findOneById(id);

		return result;
	}

	@Override
	public void validate(final Request<NotActiveAuditor> request, final NotActiveAuditor entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

	}

	@Override
	public void update(final Request<NotActiveAuditor> request, final NotActiveAuditor entity) {
		assert request != null;
		assert entity != null;

		if (entity.isEnabled()) {
			Auditor result = new Auditor();
			UserAccount userAccount;
			int userAccountId;
			result.setResponsibilityStatement(entity.getResponsibilityStatement());
			result.setFirm(entity.getFirm());

			userAccountId = this.repository.findOneNotActiveAuditorUserAccountIdById(entity.getId());
			userAccount = this.repository.findOneUserAccountById(userAccountId);

			result.setUserAccount(userAccount);
			this.repository.save(result);
		}
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
