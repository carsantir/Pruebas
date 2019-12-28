
package acme.features.anonymous.companyrecord;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import acme.components.CustomCommand;
import acme.entities.companyrecords.CompanyRecord;
import acme.framework.components.BasicCommand;
import acme.framework.controllers.AbstractController;
import acme.framework.entities.Anonymous;

@Controller
@RequestMapping("/anonymous/company-record")
public class AnonymousCompanyRecordController extends AbstractController<Anonymous, CompanyRecord> {

	@Autowired
	private AnonymousCompanyRecordListAllService	listAllService;

	@Autowired
	private AnonymousCompanyRecordShowService		showService;

	@Autowired
	private AnonymousCompanyRecordListTopService	listTopService;


	@PostConstruct
	private void initialise() {
		super.addCustomCommand(CustomCommand.LIST_ALL, BasicCommand.LIST, this.listAllService);
		super.addCustomCommand(CustomCommand.LIST_TOP, BasicCommand.LIST, this.listTopService);
		super.addBasicCommand(BasicCommand.SHOW, this.showService);
	}

}
