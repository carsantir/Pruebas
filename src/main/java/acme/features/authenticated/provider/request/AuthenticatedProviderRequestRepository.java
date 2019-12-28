
package acme.features.authenticated.provider.request;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.requests.Request;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AuthenticatedProviderRequestRepository extends AbstractRepository {

	@Query("select r from Request r where r.id = ?1")
	Request findOneById(int id);

	@Query("select r from Request r where r.deadLine>=sysdate()")
	Collection<Request> findManyAll();

	@Query("select r from Request r where r.ticker = ?1")
	Request findOneByTicker(String ticker);
}
