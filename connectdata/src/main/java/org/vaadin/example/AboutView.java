package org.vaadin.example;

import com.vaadin.cdi.CDIView;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;

@CDIView(AboutView.VIEW_NAME)
public class AboutView extends AboutViewDesign implements View {

    public static final String VIEW_NAME = "about";

    @Override
    public void enter(ViewChangeEvent event) {
        // TODO Auto-generated method stub

    }

}
