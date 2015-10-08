package org.vaadin.example;

import javax.inject.Inject;

import com.vaadin.annotations.Theme;
import com.vaadin.cdi.CDIUI;
import com.vaadin.cdi.CDIViewProvider;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.ViewDisplay;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.UI;

/**
 *
 */
@Theme("mytheme")
@CDIUI("")
public class MyUI extends UI {

    @Inject
    private MainLayout mainLayout;

    @Inject
    private CDIViewProvider viewProvider;

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        setContent(mainLayout);

        Navigator navigator = new Navigator(this, (ViewDisplay) mainLayout);
        navigator.addProvider(viewProvider);
        navigator.navigateTo("dashboard");

    }
}
