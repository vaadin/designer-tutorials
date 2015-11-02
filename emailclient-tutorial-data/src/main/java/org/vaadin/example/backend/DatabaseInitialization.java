package org.vaadin.example.backend;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.vaadin.example.backend.Message.Flag;

@Stateless
public class DatabaseInitialization {

    @Inject
    private MessageRepository msgRepo;

    public void initDatabaseIfEmpty() {

        if (!msgRepo.findAll().isEmpty()) {
            return;
        }

        createMessage("Marc Englund", "Vaadin Designer 1.0 has arrived",
                LocalDateTime.of(2015, 10, 24, 21, 0),
                "We are thrilled to announce the release of Vaadin Designer 1.0 after 6 months of intensive beta testing. "
                        + "Vaadin Designer is available for all developers, to be installed from Eclipse Marketplace today.",
                false, null);
        createMessage("Jurka Rahikkala",
                "10+1 things to know about Vaadin for job seekers",
                LocalDateTime.of(2015, 10, 22, 12, 34),
                "We are constantly seeking highly skilled software experts to join the team. The Vaadin team is constantly working on several projects, "
                        + "ranging from our own products (such as Vaadin Framework, Designer and Elements) to several customer projects around the world "
                        + "(examples at https://vaadin.com/success-stories). Needless to say, these projects do not get done without the right people.",
                true, null);
        createMessage("Teemu Pöntelin",
                "Building a mobile-first app with Polymer and Vaadin Elements",
                LocalDateTime.of(2015, 10, 19, 18, 20),
                "As you might have noticed, we at Vaadin have been experimenting with bringing our Grid and Charts components to a new audience by wrapping "
                        + "them into Polymer elements. Polymer is Google’s project to bring Web Components into developers’ hands and into production.",
                true, Flag.FLAG_STAR);
        createMessage("Tanja Repo", "Come and meet us at JavaOne 2015",
                LocalDateTime.of(2015, 10, 13, 17, 50),
                "Once again Vaadin packs up for the upcoming JavaOne 2015 and heads to San Francisco, US. JavaOne is ‘the event’ for Java enthusiasts, "
                        + "where one can hear the best speakers, get insights into the future of Java, and connect with other members of the Java community.",
                true, null);
    }

    private Message createMessage(String sender, String subject,
            LocalDateTime dateTime, String body, boolean isRead, Flag flag) {
        Message msg = new Message();
        msg.setSender(sender);
        msg.setSubject(subject);
        msg.setSent(Date.from(dateTime.toInstant(ZoneOffset.UTC)));
        msg.setBody(body);
        msg.setRead(isRead);
        msg.setFlag(flag);
        msg.setTrashed(false);
        msgRepo.saveAndFlush(msg);
        return msg;
    }
}
