package org.vaadin.example;

import java.util.Iterator;

import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.UI;

public class MainLayout extends MainLayoutDesign {

    private static final String STYLE_SELECTED = "selected";
    private Navigator navigator;

    public MainLayout() {
        navigator = new Navigator(UI.getCurrent(), scroll_panel);
        navigator.addView("", DashboardView.class);
        addNavigatorView(DashboardView.VIEW_NAME, DashboardView.class,
                menuButton1);
        addNavigatorView(OrderView.VIEW_NAME, OrderView.class, menuButton2);
        addNavigatorView(AboutView.VIEW_NAME, AboutView.class, menuButton3);
        navigator.addViewChangeListener(viewChangeListener);
    }

    private void doNavigate(String viewName) {
        getUI().getNavigator().navigateTo(viewName);
    }

    private void addNavigatorView(String viewName,
            Class<? extends View> viewClass, Button menuButton) {
        navigator.addView(viewName, viewClass);
        menuButton.addClickListener(event -> doNavigate(viewName));
        menuButton.setData(viewClass.getName());
    }

    private void adjustStyleByData(Component component, Object data) {
        if (component instanceof Button) {
            if (data != null && data.equals(((Button) component).getData())) {
                component.addStyleName(STYLE_SELECTED);
            } else {
                component.removeStyleName(STYLE_SELECTED);
            }
        }
    }

    private final ViewChangeListener viewChangeListener = new ViewChangeListener() {

        @Override
        public boolean beforeViewChange(ViewChangeEvent event) {
            return true;
        }

        @Override
        public void afterViewChange(ViewChangeEvent event) {

            Iterator<Component> it = side_bar.iterator();
            while (it.hasNext()) {
                adjustStyleByData(it.next(),
                        event.getNewView().getClass().getName());

            }
        }

    };
}
