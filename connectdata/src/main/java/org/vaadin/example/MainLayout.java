package org.vaadin.example;

import java.util.Iterator;

import com.vaadin.cdi.UIScoped;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewDisplay;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;

@UIScoped
public class MainLayout extends MainLayoutDesign implements ViewDisplay {

    private static final String STYLE_SELECTED = "selected";
    private Navigator navigator;

    public MainLayout() {
        addViewNaviAction(DashboardView.VIEW_NAME, DashboardView.class,
                menuButton1);
        addViewNaviAction(OrderView.VIEW_NAME, OrderView.class, menuButton2);
        addViewNaviAction(AboutView.VIEW_NAME, AboutView.class, menuButton3);
    }

    private void doNavigate(String viewName) {
        getUI().getNavigator().navigateTo(viewName);
    }

    private void addViewNaviAction(String viewName,
            Class<? extends View> viewClass, Button menuButton) {
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
        scroll_panel.setContent((Component) view);

        Iterator<Component> it = side_bar.iterator();
        while (it.hasNext()) {
            adjustStyleByData(it.next(), view.getClass().getName());

        }
    }
}
