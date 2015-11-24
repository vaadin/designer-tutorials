package org.vaadin.example.ui;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import org.vaadin.example.backend.MessageFacade;
import org.vaadin.example.ui.themes.mytheme.MyTheme;

import com.vaadin.cdi.UIScoped;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.UI;

/**
 * 
 * Handles the left-side menu and the top toolbar
 */
@UIScoped
public class MainLayout extends MainLayoutDesign {

    @Inject
    private MessageFacade messageFacade;

    private Map<String, Supplier<Long>> badgeSuppliers = new HashMap<>();

    @PostConstruct
    protected void init() {
        setupMenuButtons();
    }

    public void setSelectedFolder(String folder) {
        menuItems.forEach(component -> adjustStyleByData(component, folder));
    }

    // Adjust all menu badges when a message is modified (marked as read)
    public void messageModified(@Observes MessageModifiedEvent event) {
        menuItems.forEach(this::setMenuBadge);
    }

    void navigateToFolder(String folder) {
        UI.getCurrent().getNavigator()
                .navigateTo(FolderView.VIEW_NAME + "/" + folder);
    }

    private void setupMenuButtons() {
        mapButton(inboxButton, MessageFacade.FOLDER_INBOX,
                Optional.of(() -> messageFacade.countAllUnread()));
        mapButton(draftsButton, MessageFacade.FOLDER_DRAFTS);
        mapButton(sentButton, MessageFacade.FOLDER_SENT);
        mapButton(junkButton, MessageFacade.FOLDER_JUNK);
        mapButton(trashButton, MessageFacade.FOLDER_TRASH);
        mapButton(flaggedButton, MessageFacade.FOLDER_FLAGGED,
                Optional.of(() -> messageFacade.countFlaggedUnread()));

        menuItems.forEach(this::setMenuBadge);
    }

    // Formats a given Button component's caption to contain HTML in a
    // particular format required by Valo themed menu item component
    private void setMenuBadge(Component component) {
        if (component instanceof Button) {
            Button button = (Button) component;
            if (button.getData() != null
                    && button.getData() instanceof String) {
                String folder = (String) button.getData();
                Long count = badgeSuppliers.containsKey(folder)
                        ? badgeSuppliers.get(folder).get() : 0;
                String badgeText = (count != null && count > 0)
                        ? (count > 99) ? "99+" : count.toString() : "";
                String captionFormat = badgeText.isEmpty() ? ""
                        : "%s <span class=\"valo-menu-badge\">%s</span>";
                if (captionFormat.isEmpty()) {
                    component.setCaption(folder);
                } else {
                    component.setCaption(
                            String.format(captionFormat, folder, badgeText));
                }
            }
        }
    }

    // Map button click to a navigation method.
    // Map button to a folder name to allow setting selected style when folder
    // is navigated without clicking the button.
    // Map button to a Supplier method, that provides count for unread items
    private void mapButton(Button button, String folderName,
            Optional<Supplier<Long>> badgeSupplier) {
        button.addClickListener(event -> navigateToFolder(folderName));
        button.setData(folderName);
        if (badgeSupplier.isPresent()) {
            badgeSuppliers.put(folderName, badgeSupplier.get());
        }
    }

    private void mapButton(Button button, String folderName) {
        mapButton(button, folderName, Optional.empty());
    }

    // Checks if a given Component has a given folder name as data, and adds or
    // removes selected style appropriately
    private void adjustStyleByData(Component component, Object data) {
        if (component instanceof Button) {
            if (data != null && data.equals(((Button) component).getData())) {
                component.addStyleName(MyTheme.SELECTED);
            } else {
                component.removeStyleName(MyTheme.SELECTED);
            }
        }
    }
}
