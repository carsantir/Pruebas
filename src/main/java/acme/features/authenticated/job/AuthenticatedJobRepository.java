
package acme.features.authenticated.job;

import java.util.Collection;
import java.util.Date;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.jobs.Job;
import acme.entities.roles.Employer;
import acme.framework.entities.UserAccount;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AuthenticatedJobRepository extends AbstractRepository {

	@Query("select j from Job j where j.id=?1")
	Job findOneJobById(int id);

	@Query("select j from Job j where j.deadline > ?1 AND j.draft= ?2")
	Collection<Job> findManyActive(Date actual, boolean aux);

	@Query("SELECT ua from UserAccount ua where ua.id = (select e.userAccount.id from Employer e where e.id = (select j.employer.id from Job j where j.id = ?1 ))")
	UserAccount findUserAccount(int id);

	@Query("select e from Employer e where e.id = (select j.employer.id from Job j where j.id = ?1 )")
	Employer findEmployer(int id);
}
