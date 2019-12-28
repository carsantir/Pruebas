
package acme.features.anonymous.announcement;

import java.util.Collection;
import java.util.Date;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.announcements.Announcement;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AnonymousAnnouncementRepository extends AbstractRepository {

	@Query("select s from Announcement s where s.id=?1")
	Announcement findOneById(int id);

	@Query("select s from Announcement s where s.moment > ?1 AND s.moment < ?2")
	Collection<Announcement> findLessOneMonthAgo(Date previousMonthCalendar, Date actualMonthDate);
}
