
package acme.features.administrator.auditor;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.roles.Auditor;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AdministratorAuditorRepository extends AbstractRepository {

	@Query("select a from Auditor a where a.id=?1")
	Auditor findOneById(int id);

	@Query("select a from Auditor a where a.enabled=0")
	Collection<Auditor> findManyAll();
}
