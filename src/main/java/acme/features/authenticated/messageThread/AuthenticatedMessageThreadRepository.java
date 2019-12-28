
package acme.features.authenticated.messageThread;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.messageThreads.CanParticipate;
import acme.entities.messageThreads.MessageThread;
import acme.framework.entities.Authenticated;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AuthenticatedMessageThreadRepository extends AbstractRepository {

	@Query("select m from MessageThread m where m.id=?1")
	MessageThread findOneMessageThreadById(int id);

	@Query("select m from MessageThread m where m.id in (select messageThread.id from CanParticipate c where authenticated.id=?1)")
	Collection<MessageThread> findMessageThreadByUserId(int id);

	@Query("select c from CanParticipate c where c.authenticated.id=?1 and c.messageThread.id=?2")
	CanParticipate findOneMessageThreadByUserAccountId(int idPrincipal, int idMessageThread);

	@Query("select a from Authenticated a where a.userAccount.id=?1")
	Authenticated findOneAuthenticatedById(int id);
}
