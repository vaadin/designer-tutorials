package org.vaadin.example.ui;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

import org.vaadin.example.backend.Message;
import org.vaadin.example.backend.Message.Flag;
import org.vaadin.example.ui.themes.mytheme.MyTheme;

import com.vaadin.server.FontAwesome;

/**
 * This is the place to implement component logic for MessageDesign. The super
 * class should not be edited, because Vaadin Designer overwrites it.
 * 
 */
public class MessageComponent extends MessageDesign {

    private static final List<String> MESSAGE_STYLES = Collections
            .unmodifiableList(Arrays.asList(MyTheme.INDICATOR_CIRCLE,
                    MyTheme.INDICATOR_STAR_RED));

    public MessageComponent(Message message,
            Consumer<ClickEvent<MessageComponent, Message>> messageClicked) {
        senderLabel.setValue(message.getSender());
        messageLabel.setCaption(message.getSubject());
        messageLabel.setValue(message.getBody());

        addLayoutClickListener(event -> messageClicked
                .accept(new ClickEvent<>(this, message)));

        setIndicator(message.isRead(), message.getFlag());
    }

    public void setIndicator(boolean read, Flag flag) {
        MESSAGE_STYLES.forEach(this::removeStyleName);
        indicatorButton.setIcon(null);
        if (flag == Flag.FLAG_STAR) {
            indicatorButton.setIcon(FontAwesome.STAR);
            if (!read) {
                indicatorButton.addStyleName(MyTheme.INDICATOR_STAR_RED);
            }
        } else if (!read) {
            indicatorButton.setIcon(FontAwesome.CIRCLE);
            indicatorButton.addStyleName(MyTheme.INDICATOR_CIRCLE);
        }
    }

    public static class ClickEvent<T, PAYLOAD> {
        private final T source;
        private final PAYLOAD data;

        public ClickEvent(T source, PAYLOAD data) {
            this.source = source;
            this.data = data;
        }

        public T getSource() {
            return source;
        }

        public PAYLOAD getData() {
            return data;
        }
    }
}
