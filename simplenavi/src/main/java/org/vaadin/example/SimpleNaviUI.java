package org.vaadin.example;

import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.UI;

@Theme("simple")
public class SimpleNaviUI extends UI {

    @Override
    protected void init(VaadinRequest request) {
        setContent(new MainLayout());
    }

}
