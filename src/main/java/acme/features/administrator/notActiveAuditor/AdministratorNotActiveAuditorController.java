
package acme.features.administrator.notActiveAuditor;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import acme.entities.roles.NotActiveAuditor;
import acme.framework.components.BasicCommand;
import acme.framework.controllers.AbstractController;
import acme.framework.entities.Administrator;

@Controller
@RequestMapping("/administrator/not-active-auditor/")
public class AdministratorNotActiveAuditorController extends AbstractController<Administrator, NotActiveAuditor> {

	@Autowired
	private AdministratorNotActiveAuditorListService	listService;

	@Autowired
	private AdministratorNotActiveAuditorShowService	showService;

	@Autowired
	private AdministratorNotActiveAuditorUpdateService	updateService;


	@PostConstruct
	private void initialise() {
		super.addBasicCommand(BasicCommand.LIST, this.listService);
		super.addBasicCommand(BasicCommand.SHOW, this.showService);
		super.addBasicCommand(BasicCommand.UPDATE, this.updateService);
	}
}
