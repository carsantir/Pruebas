
package acme.features.authenticated.notActiveAuditor;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import acme.entities.roles.NotActiveAuditor;
import acme.framework.components.BasicCommand;
import acme.framework.controllers.AbstractController;
import acme.framework.entities.Authenticated;

@Controller
@RequestMapping("/authenticated/not-active-auditor/")
public class AuthenticatedNotActiveAuditorController extends AbstractController<Authenticated, NotActiveAuditor> {

	@Autowired
	private AuthenticatedNotActiveAuditorUpdateService	updateService;

	@Autowired
	private AuthenticatedNotActiveAuditorCreateService	createService;


	@PostConstruct
	private void initialise() {
		super.addBasicCommand(BasicCommand.UPDATE, this.updateService);
		super.addBasicCommand(BasicCommand.CREATE, this.createService);
	}
}
