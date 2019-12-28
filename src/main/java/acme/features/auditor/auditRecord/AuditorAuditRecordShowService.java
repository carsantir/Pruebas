
package acme.features.auditor.auditRecord;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.auditRecords.AuditRecord;
import acme.entities.roles.Auditor;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Principal;
import acme.framework.services.AbstractShowService;

@Service
public class AuditorAuditRecordShowService implements AbstractShowService<Auditor, AuditRecord> {

	@Autowired
	AuditorAuditRecordRepository repository;


	@Override
	public boolean authorise(final Request<AuditRecord> request) {
		Principal principal;
		int idPrincipal;
		principal = request.getPrincipal();
		idPrincipal = principal.getActiveRoleId();

		int id;
		id = request.getModel().getInteger("id");
		AuditRecord ar = this.repository.findOneAuditRecordById(id);

		Collection<Integer> idNotEnabled = this.repository.findOneAuditorByEnabled();

		if (idNotEnabled.contains(idPrincipal)) {
			return false;
		} else {
			return !ar.isDraft() || ar.isDraft() && ar.getAuditor().getId() == request.getPrincipal().getActiveRoleId();
		}
	}
	@Override
	public void unbind(final Request<AuditRecord> request, final AuditRecord entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "title", "moment", "body", "draft", "job.title");

	}
	@Override
	public AuditRecord findOne(final Request<AuditRecord> request) {
		assert request != null;

		AuditRecord result;
		int id;

		id = request.getModel().getInteger("id");
		result = this.repository.findOneAuditRecordById(id);
		return result;
	}
}
