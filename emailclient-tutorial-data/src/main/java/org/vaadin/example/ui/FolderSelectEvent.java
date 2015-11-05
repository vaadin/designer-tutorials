package org.vaadin.example.ui;

/**
 * Event is fired by {@link FolderView} and observed by {@link MyUI}.
 */
public class FolderSelectEvent {
    private final String folder;

    public FolderSelectEvent(String folder) {
        this.folder = folder;
    }

    public String getFolder() {
        return folder;
    }
}
