package org.vaadin.example.ui;

import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

import org.vaadin.example.backend.DatabaseInitialization;
import org.vaadin.example.backend.MessageFacade;
import org.vaadin.example.ui.themes.mytheme.MyTheme;

import com.vaadin.annotations.Theme;
import com.vaadin.cdi.CDIUI;
import com.vaadin.cdi.CDIViewProvider;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.UI;

/**
 *
 */
@Theme("mytheme")
@CDIUI("")
public class MyUI extends UI {

    @Inject
    private MessageFacade messageFacade;

    @Inject
    private CDIViewProvider viewProvider;

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        final ApplicationDesign design = new ApplicationDesign();
        setContent(design);

        setMenuBadge(design.inboxButton, "Inbox", messageFacade.countUnread());
        setMenuBadge(design.flaggedButton, "Flagged",
                messageFacade.countFlagged());

        Navigator navigator = new Navigator(this, design.messagePanel);
        navigator.addProvider(viewProvider);
        setNavigator(navigator);

        if (navigator.getState().isEmpty()) {
            navigateToFolder(MessageFacade.FOLDER_INBOX);
        } else {
            navigator.navigateTo(navigator.getState());
        }
        mapButton(design.inboxButton, MessageFacade.FOLDER_INBOX);
        mapButton(design.draftsButton, MessageFacade.FOLDER_DRAFTS);
        mapButton(design.sentButton, MessageFacade.FOLDER_SENT);
        mapButton(design.junkButton, MessageFacade.FOLDER_JUNK);
        mapButton(design.trashButton, MessageFacade.FOLDER_TRASH);
        mapButton(design.flaggedButton, MessageFacade.FOLDER_FLAGGED);
    }

    public void folderSelected(@Observes FolderSelectEvent event) {
        ((ApplicationDesign) getContent()).menuItems.forEach(
                component -> adjustStyleByData(component, event.getFolder()));
    }

    private void mapButton(Button button, String folderName) {
        button.addClickListener(event -> navigateToFolder(folderName));
        button.setData(folderName);
    }

    private void adjustStyleByData(Component component, Object data) {
        if (component instanceof Button) {
            if (data != null && data.equals(((Button) component).getData())) {
                component.addStyleName(MyTheme.SELECTED);
            } else {
                component.removeStyleName(MyTheme.SELECTED);
            }
        }
    }

    private void navigateToFolder(String folder) {
        getNavigator().navigateTo(FolderView.VIEW_NAME + "/" + folder);
    }

    private void setMenuBadge(Component component, String caption, Long count) {
        String badgeText = (count != null && count > 0)
                ? (count > 99) ? "99+" : count.toString() : "";
        String captionFormat = badgeText.isEmpty() ? ""
                : "%s <span class=\"valo-menu-badge\">%s</span>";
        if (captionFormat.isEmpty()) {
            component.setCaption(caption);
        } else {
            component.setCaption(
                    String.format(captionFormat, caption, badgeText));
        }
    }

    @WebListener
    private static class MyUIServlectContextListener
            implements ServletContextListener {

        @Inject
        private UserTransaction transaction;

        @Inject
        private DatabaseInitialization init;

        @Override
        public void contextDestroyed(ServletContextEvent arg0) {
            // do nothing
        }

        @Override
        public void contextInitialized(ServletContextEvent arg0) {
            try {
                transaction.begin();
                init.initDatabaseIfEmpty();
                transaction.commit();
            } catch (NotSupportedException | SystemException | SecurityException
                    | IllegalStateException | RollbackException
                    | HeuristicMixedException | HeuristicRollbackException e) {
                e.printStackTrace();
            }
        }
    }
}
