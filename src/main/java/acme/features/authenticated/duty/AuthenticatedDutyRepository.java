
package acme.features.authenticated.duty;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.jobs.Duty;
import acme.entities.jobs.Job;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AuthenticatedDutyRepository extends AbstractRepository {

	@Query("select d from Duty d where d.id=?1")
	Duty findOneDutyById(int id);

	@Query("select d from Duty d where d.job.id = ?1")
	Collection<Duty> findManyAllFromJob(int idJob);

	@Query("select j from Job j where j.id = (select d.job.id from Duty d where d.id=?1)")
	Job findJobFromDutyId(int id);

	@Query("select j from Job j where j.id =?1")
	Job findJobFromId(int id);

}
