
package acme.features.administrator.notActiveAuditor;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.roles.NotActiveAuditor;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Administrator;
import acme.framework.services.AbstractListService;

@Service
public class AdministratorNotActiveAuditorListService implements AbstractListService<Administrator, NotActiveAuditor> {

	@Autowired
	AdministratorNotActiveAuditorRepository repository;


	@Override
	public boolean authorise(final Request<NotActiveAuditor> request) {
		assert request != null;
		return true;
	}

	@Override
	public void unbind(final Request<NotActiveAuditor> request, final NotActiveAuditor entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "firm", "responsibilityStatement", "userAccount.username", "enabled");
	}

	@Override
	public Collection<NotActiveAuditor> findMany(final Request<NotActiveAuditor> request) {
		assert request != null;
		Collection<NotActiveAuditor> result;
		result = this.repository.findManyAll();

		return result;
	}

}
