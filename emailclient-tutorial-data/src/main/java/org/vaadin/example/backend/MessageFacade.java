package org.vaadin.example.backend;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class MessageFacade {

    public static final String FOLDER_INBOX = "inbox";
    public static final String FOLDER_DRAFTS = "drafts";
    public static final String FOLDER_SENT = "sent";
    public static final String FOLDER_JUNK = "junk";
    public static final String FOLDER_TRASH = "trash";
    public static final String FOLDER_FLAGGED = "flagged";
    public static final List<String> FOLDERS = Arrays.asList(FOLDER_INBOX,
            FOLDER_DRAFTS, FOLDER_SENT, FOLDER_JUNK, FOLDER_TRASH,
            FOLDER_FLAGGED);

    @Inject
    private MessageRepository repository;

    public List<Message> findAll() {
        return repository.findAll();
    }

    public List<Message> findAllByFolder(String folder) {
        if (FOLDERS.contains(folder)) {
            if (folder.equals(FOLDER_INBOX)) {
                return repository.findAll();
            } else if (folder.equals(FOLDER_FLAGGED)) {
                return repository.findByFlagIsNotNullAndTrashed(false);
            }
        }
        return new ArrayList<>();
    }

    public long countUnread() {
        return repository.findByRead(false).count();
    }

    public long countFlagged() {
        return repository.findByFlagIsNotNull().count();
    }

    public void save(Message message) {
        repository.saveAndFlush(message);
    }
}
