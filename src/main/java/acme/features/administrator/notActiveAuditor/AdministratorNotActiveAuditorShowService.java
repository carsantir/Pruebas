/*
 * AnonymousUserAccountCreateService.java
 *
 * Copyright (c) 2019 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.administrator.notActiveAuditor;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.roles.NotActiveAuditor;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Administrator;
import acme.framework.services.AbstractShowService;

@Service
public class AdministratorNotActiveAuditorShowService implements AbstractShowService<Administrator, NotActiveAuditor> {

	@Autowired
	private AdministratorNotActiveAuditorRepository repository;


	@Override
	public boolean authorise(final Request<NotActiveAuditor> request) {
		assert request != null;

		Collection<Integer> idNotEnabled = this.repository.findOneNotActiveAuditorByEnabled();

		int id;
		String url = request.getServletRequest().getQueryString();
		if (url != null) {
			String[] aux = url.split("id=");
			id = Integer.parseInt(aux[1]);
		} else {
			id = request.getModel().getInteger("notActiveAuditor.id");
		}
		if (idNotEnabled.contains(id)) {
			return false;
		} else {
			return true;
		}

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

}
