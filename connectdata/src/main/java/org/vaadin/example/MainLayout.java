package org.vaadin.example;

import com.vaadin.cdi.UIScoped;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewDisplay;
import com.vaadin.ui.Component;

@UIScoped
public class MainLayout extends MainLayoutDesign implements ViewDisplay {

    @Override
    public void showView(View view) {
        if (view instanceof Component) {
            scroll_panel.setContent((Component) view);
        } else {
            throw new IllegalArgumentException("View is not a Component");
        }
    }

}
