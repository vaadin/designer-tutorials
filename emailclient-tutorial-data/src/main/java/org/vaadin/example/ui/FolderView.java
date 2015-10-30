package org.vaadin.example.ui;

import javax.inject.Inject;

import org.vaadin.example.backend.Message;
import org.vaadin.example.backend.MessageFacade;

import com.vaadin.cdi.CDIView;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.VerticalLayout;

@CDIView(supportsParameters = true, value = FolderView.VIEW_NAME)
public class FolderView extends VerticalLayout implements View {

    public static final String VIEW_NAME = "folder";

    @Inject
    private MessageFacade messageFacade;

    @Inject
    javax.enterprise.event.Event<FolderSelectEvent> folderSelectEvent;

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
        MessageComponent msg = new MessageComponent(entity.getSender(), entity.getSubject(),
                entity.getBody());
        if (entity.isFlagged()) {
            msg.setFlagIndicator();
        } else if (!entity.isRead()) {
            msg.setNewIndicator();
        } else {
            msg.clearIndicator();
        }
        return msg;
    }

}
