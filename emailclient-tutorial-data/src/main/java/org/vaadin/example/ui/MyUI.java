package org.vaadin.example.ui;

import javax.inject.Inject;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.vaadin.example.backend.DatabaseInitialization;
import org.vaadin.example.backend.MessageFacade;

import com.vaadin.annotations.Theme;
import com.vaadin.cdi.CDIUI;
import com.vaadin.cdi.CDIViewProvider;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.UI;

/**
 * UI class using Vaadin CDI addon annotation CDIUI, which makes it a managed
 * bean and allows to inject dependencies to it.
 */
@Theme("mytheme")
@CDIUI("")
public class MyUI extends UI {

    // CDIViewProvider implements com.vaadin.navigator.ViewProvider and
    // understands container managed views.
    @Inject
    private CDIViewProvider viewProvider;

    @Inject
    private MainLayout mainLayout;

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        setContent(mainLayout);

        Navigator navigator = new Navigator(this, mainLayout.messagePanel);
        navigator.addProvider(viewProvider);
        setNavigator(navigator);

        navigator.addViewChangeListener(new ViewChangeListener() {

            @Override
            public boolean beforeViewChange(ViewChangeEvent event) {
                return true;
            }

            @Override
            public void afterViewChange(ViewChangeEvent event) {
                String folder = event.getParameters();
                if (MessageFacade.FOLDERS.contains(folder)) {
                    mainLayout.setSelectedFolder(folder);
                }
            }
        });

        if (navigator.getState().isEmpty()) {
            mainLayout.navigateToFolder(MessageFacade.FOLDER_INBOX);
        }

    }

    // Test data initialization is done in contextInitialized Servlet life-cycle
    // event
    @WebListener
    private static class MyUIServlectContextListener
            implements ServletContextListener {

        @Inject
        private DatabaseInitialization init;

        @Override
        public void contextDestroyed(ServletContextEvent arg0) {
            // do nothing
        }

        @Override
        public void contextInitialized(ServletContextEvent arg0) {
            init.initDatabaseIfEmpty();
        }
    }
}
