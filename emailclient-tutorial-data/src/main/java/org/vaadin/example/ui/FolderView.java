package org.vaadin.example.ui;

import java.util.Optional;

import javax.inject.Inject;

import org.vaadin.example.backend.Message;
import org.vaadin.example.backend.MessageFacade;

import com.vaadin.cdi.CDIView;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.VerticalLayout;

/**
 * View listing messages fetched through MessageFacade. Also handles message
 * clicks to mark them as read.
 *
 */
@CDIView(supportsParameters = true, value = FolderView.VIEW_NAME)
public class FolderView extends VerticalLayout implements View {

    public static final String VIEW_NAME = "folder";

    @Inject
    private MessageFacade messageFacade;

    @Inject
    javax.enterprise.event.Event<FolderSelectEvent> folderSelectEvent;

    @Inject
    javax.enterprise.event.Event<MessageModifiedEvent> messageSelectEvent;

    @Override
    public void enter(ViewChangeEvent event) {
        // handle view
        String folder = event.getParameters();
        if (MessageFacade.FOLDERS.contains(folder)) {
            folderSelectEvent.fire(new FolderSelectEvent(folder));
            refreshFolder(folder);
        }
    }

    private void refreshFolder(String folder) {
        removeAllComponents();
        messageFacade.findAllByFolder(folder).stream()
                .map(this::createFromEntity).forEach(this::addComponent);
    }

    private MessageComponent createFromEntity(Message entity) {
        MessageComponent msg = new MessageComponent(entity, Optional.of(
                event -> onMessageClicked(event.getSource(), event.getData())));
        return msg;
    }

    private void onMessageClicked(MessageComponent source, Message message) {
        if (!message.isRead()) {
            message.setRead(true);
            messageFacade.save(message);
            source.setIndicator(true, message.getFlag());
        }
        messageSelectEvent.fire(new MessageModifiedEvent(message));
    }

}
