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
        navigator = new Navigator(UI.getCurrent(), contentPanel);
        navigator.addView("", StatsView.class);
        addNavigatorView(StatsView.VIEW_NAME, StatsView.class, menuButton1);
        addNavigatorView(PluginsView.VIEW_NAME, PluginsView.class, menuButton2);
        addNavigatorView(PermissionsView.VIEW_NAME, PermissionsView.class,
                menuButton3);
        navigator.addViewChangeListener(viewChangeListener);
        UI.getCurrent().setNavigator(navigator);
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

            Iterator<Component> it = menuLayout.iterator();
            while (it.hasNext()) {
                adjustStyleByData(it.next(),
                        event.getNewView().getClass().getName());

            }
        }

    };
}
