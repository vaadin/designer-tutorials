package org.vaadin.example.backend;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class MessageFacade {

    @Inject
    private MessageRepository repository;

    public List<MessageEntity> findAll() {
        return repository.findAll();
    }
}
