package org.vaadin.example;

import java.util.Iterator;

import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewDisplay;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.UI;

public class MainLayout extends MainLayoutDesign implements ViewDisplay {

    private static final String STYLE_SELECTED = "selected";
    private Navigator navigator;

    public MainLayout() {
        navigator = new Navigator(UI.getCurrent(), (ViewDisplay) this);
        addNavigatorView(DashboardView.VIEW_NAME, DashboardView.class,
                menuButton1);
        addNavigatorView(OrderView.VIEW_NAME, OrderView.class, menuButton2);
        addNavigatorView(AboutView.VIEW_NAME, AboutView.class, menuButton3);
        if (navigator.getState().isEmpty()) {
            navigator.navigateTo(DashboardView.VIEW_NAME);
        } else {
            navigator.navigateTo(navigator.getState());
        }
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

    @Override
    public void showView(View view) {
        if (view instanceof Component) {
            scroll_panel.setContent((Component) view);
            Iterator<Component> it = side_bar.iterator();
            while (it.hasNext()) {
                adjustStyleByData(it.next(), view.getClass().getName());
            }
        } else {
            throw new IllegalArgumentException("View is not a Component");
        }
    }
}
