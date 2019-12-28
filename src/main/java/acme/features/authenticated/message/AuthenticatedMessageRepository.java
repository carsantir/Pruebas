
package acme.features.authenticated.message;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.configurations.Configuration;
import acme.entities.messageThreads.CanParticipate;
import acme.entities.messageThreads.Message;
import acme.entities.messageThreads.MessageThread;
import acme.framework.entities.Authenticated;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AuthenticatedMessageRepository extends AbstractRepository {

	@Query("select m from Message m where m.id=?1")
	Message findOneMessageById(int id);

	@Query("select m from Message m where m.messageThread.id = ?1")
	Collection<Message> findMessagesByThreadId(int id);

	@Query("select a from Authenticated a where a.id=?1")
	Authenticated findAuthenticated(int id);

	@Query("select m from MessageThread m where m.id=?1")
	MessageThread findOneMessageThreadById(int id);

	@Query("select c from Configuration c")
	Collection<Configuration> findConfigurationParameters();

	@Query("select c from CanParticipate c where c.messageThread.id=?2 and c.authenticated.id=?1")
	CanParticipate findOneUserInvolvedById(int idUser, int idMessageThread);

}
