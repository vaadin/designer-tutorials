package org.vaadin.example.ui;

import javax.inject.Inject;

import org.vaadin.example.backend.MessageFacade;

import com.vaadin.cdi.CDIView;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

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
    javax.enterprise.event.Event<MessageModifiedEvent> messageSelectEvent;

    @Override
    public void enter(ViewChangeEvent event) {
        String folder = event.getParameters();
        refreshFolder(folder);
    }

    private void refreshFolder(String folder) {
        removeAllComponents();
        Label todo = new Label("TODO implement listing of messages");
        todo.setStyleName(ValoTheme.LABEL_LARGE);
        addComponent(todo);
    }
}
