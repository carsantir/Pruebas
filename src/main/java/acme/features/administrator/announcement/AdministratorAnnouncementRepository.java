
package acme.features.administrator.announcement;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.announcements.Announcement;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AdministratorAnnouncementRepository extends AbstractRepository {

	@Query("select s from Announcement s where s.id=?1")
	Announcement findOneById(int id);

	@Query("select s from Announcement s")
	Collection<Announcement> findManyAll();
}
