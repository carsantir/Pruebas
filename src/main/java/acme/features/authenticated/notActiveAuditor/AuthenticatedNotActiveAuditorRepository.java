
package acme.features.authenticated.notActiveAuditor;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.roles.Auditor;
import acme.entities.roles.NotActiveAuditor;
import acme.framework.entities.UserAccount;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AuthenticatedNotActiveAuditorRepository extends AbstractRepository {

	@Query("select a from NotActiveAuditor a where a.userAccount.id=?1")
	NotActiveAuditor findOneNotActiveAuditorByUserAccountId(int id);

	@Query("select ua from UserAccount ua where ua.id=?1")
	UserAccount findOneUserAccountById(int id);

	@Query("select a from Auditor a where a.userAccount.id=?1")
	Auditor findOneAuditorByUserAccountId(int id);
}
