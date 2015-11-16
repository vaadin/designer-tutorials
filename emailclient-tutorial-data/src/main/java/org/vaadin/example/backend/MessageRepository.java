package org.vaadin.example.backend;

import java.util.List;

import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.QueryResult;
import org.apache.deltaspike.data.api.Repository;

@Repository
public interface MessageRepository extends EntityRepository<Message, Long> {
    QueryResult<Message> findByRead(boolean read);

    QueryResult<Message> findByFlagIsNotNullAndRead(boolean read);

    List<Message> findByFlagIsNotNullAndTrashed(boolean trashed);
}
