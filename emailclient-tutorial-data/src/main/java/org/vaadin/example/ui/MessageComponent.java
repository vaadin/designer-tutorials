package org.vaadin.example.ui;

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

    public MessageComponent(Message message,
            MessageClickListener clickListener) {
        senderLabel.setValue(message.getSender());
        messageLabel.setCaption(message.getSubject());
        messageLabel.setValue(message.getBody());

        addLayoutClickListener(
                event -> clickListener.messageClick(this, message));
        setIndicator(message.isRead(), message.getFlag());
    }

    public void setIndicator(boolean read, Flag flag) {
        MyTheme.MESSAGE_STYLES.forEach(indicatorButton::removeStyleName);
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

    @FunctionalInterface
    interface MessageClickListener {
        public void messageClick(MessageComponent source, Message message);
    }
}
