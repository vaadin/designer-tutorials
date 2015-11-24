package org.vaadin.example.ui;

import org.vaadin.example.backend.Message;

/**
 * Event is fired by {@link FolderView} and observed by {@link MyUI}.
 * 
 */
public class MessageModifiedEvent {
    private final Message message;

    public MessageModifiedEvent(Message message) {
        this.message = message;
    }

    public Message getMessage() {
        return message;
    }
}
