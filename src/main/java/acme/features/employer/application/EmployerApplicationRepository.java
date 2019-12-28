
package acme.features.employer.application;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.jobs.Application;
import acme.entities.jobs.Job;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface EmployerApplicationRepository extends AbstractRepository {

	@Query("select a from Application a where a.id = ?1")
	Application findOneApplicationById(int id);

	@Query("select a from Application a	where a.job.id in (select j.id from Job j where j.employer.id = ?1) order by a.job.reference asc, a.status asc, a.moment desc")
	Collection<Application> findManyByEmployerId(int employerId);

	@Query("select j from Job j where j.id=?1")
	Job findOneJobById(int id);
}
