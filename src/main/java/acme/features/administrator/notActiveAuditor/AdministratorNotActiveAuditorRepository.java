
package acme.features.administrator.notActiveAuditor;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.roles.NotActiveAuditor;
import acme.framework.entities.UserAccount;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AdministratorNotActiveAuditorRepository extends AbstractRepository {

	@Query("select a from NotActiveAuditor a where a.id=?1")
	NotActiveAuditor findOneById(int id);

	@Query("select a from NotActiveAuditor a where a.enabled=0")
	Collection<NotActiveAuditor> findManyAll();

	@Query("select userAccount.id from NotActiveAuditor a where a.id=?1")
	Integer findOneNotActiveAuditorUserAccountIdById(int id);

	@Query("select ua from UserAccount ua where ua.id=?1")
	UserAccount findOneUserAccountById(int id);

	@Query("select id from NotActiveAuditor a where a.enabled=1")
	Collection<Integer> findOneNotActiveAuditorByEnabled();
}
