package org.vaadin.example;

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
import org.vaadin.example.backend.MessageEntity;
import org.vaadin.example.backend.MessageFacade;

import com.vaadin.annotations.Theme;
import com.vaadin.cdi.CDIUI;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.UI;

/**
 *
 */
@Theme("mytheme")
@CDIUI("")
public class MyUI extends UI {

    @Inject
    private MessageFacade messageFacade;

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        final ApplicationDesign design = new ApplicationDesign();
        setContent(design);

        messageFacade.findAll().stream().map(this::createFromEntity)
                .forEach(design.messageList::addComponent);
        // This replaces a proper backend just to simulate how the view behaves
        // with dynamic content
        // for (int i = 0; i < 10; i++) {
        // design.messageList.addComponent(new Message());
        // }
    }

    private Message createFromEntity(MessageEntity entity) {
        Message msg = new Message();
        msg.senderLabel.setValue(entity.getSender());
        msg.messageLabel.setCaption(entity.getSubject());
        msg.messageLabel.setValue(entity.getBody());
        return msg;
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
