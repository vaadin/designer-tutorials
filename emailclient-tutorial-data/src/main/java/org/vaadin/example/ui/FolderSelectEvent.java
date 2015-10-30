package org.vaadin.example.ui;

public class FolderSelectEvent {
    private final String folder;

    public FolderSelectEvent(String folder) {
        this.folder = folder;
    }

    public String getFolder() {
        return folder;
    }
}
