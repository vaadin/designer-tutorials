package org.vaadin.example.backend;

import java.util.List;

import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = Message.class)
public interface MessageRepository extends EntityRepository<Message, Long> {

    @Query("select count(m) from Message m where m.read = false")
    Long countUnread();

    @Query("select count(m) from Message m where m.flag is not null")
    Long countByFlagIsNotNull();

    List<Message> findByFlagIsNotNullAndTrashed(boolean trashed);
}
