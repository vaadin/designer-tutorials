package org.vaadin.example;

import com.vaadin.navigator.Navigator;
import com.vaadin.ui.UI;

public class MainLayout extends MainLayoutDesign {

    public MainLayout() {
        Navigator navigator = new Navigator(UI.getCurrent(), contentPanel);
        navigator.addView("", StatsView.class);
        navigator.addView(StatsView.VIEW_NAME, StatsView.class);
        navigator.addView(PluginsView.VIEW_NAME, PluginsView.class);
        navigator.addView(PermissionsView.VIEW_NAME, PermissionsView.class);
        UI.getCurrent().setNavigator(navigator);

        menuButton1.addClickListener(event -> doNavigate(StatsView.VIEW_NAME));
        menuButton2
                .addClickListener(event -> doNavigate(PluginsView.VIEW_NAME));
        menuButton3.addClickListener(
                event -> doNavigate(PermissionsView.VIEW_NAME));
    }

    private void doNavigate(String viewName) {
        getUI().getNavigator().navigateTo(viewName);
    }
}
