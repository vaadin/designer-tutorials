package org.vaadin.example.backend;

import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = MessageEntity.class)
public interface MessageRepository
        extends EntityRepository<MessageEntity, Long> {

}
