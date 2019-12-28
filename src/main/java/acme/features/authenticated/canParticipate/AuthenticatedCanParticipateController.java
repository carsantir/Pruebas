
package acme.features.authenticated.canParticipate;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import acme.components.CustomCommand;
import acme.entities.messageThreads.CanParticipate;
import acme.framework.components.BasicCommand;
import acme.framework.controllers.AbstractController;
import acme.framework.entities.Authenticated;

@Controller
@RequestMapping("/authenticated/can-participate/")
public class AuthenticatedCanParticipateController extends AbstractController<Authenticated, CanParticipate> {

	@Autowired
	private AuthenticatedCanParticipateListInvolvedService	listInvolvedService;

	@Autowired
	private AuthenticatedCanParticipateShowService			showService;

	@Autowired
	private AuthenticatedCanParticipateDeleteService		deleteService;

	@Autowired
	private AuthenticatedCanParticipateCreateService		createService;


	@PostConstruct
	private void initialise() {
		super.addCustomCommand(CustomCommand.LIST_INVOLVED, BasicCommand.LIST, this.listInvolvedService);
		super.addBasicCommand(BasicCommand.DELETE, this.deleteService);
		super.addBasicCommand(BasicCommand.SHOW, this.showService);
		super.addBasicCommand(BasicCommand.CREATE, this.createService);
	}

}
