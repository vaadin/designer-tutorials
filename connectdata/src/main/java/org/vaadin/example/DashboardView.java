package org.vaadin.example;

import com.vaadin.cdi.CDIView;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;

@CDIView(DashboardView.VIEW_NAME)
public class DashboardView extends DashboardViewDesign implements View {

    public static final String VIEW_NAME = "dashboard";

    @Override
    public void enter(ViewChangeEvent event) {
        // TODO Auto-generated method stub

    }

}
