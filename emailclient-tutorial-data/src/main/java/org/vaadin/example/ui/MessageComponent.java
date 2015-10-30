package org.vaadin.example.ui;

import org.vaadin.example.ui.themes.mytheme.MyTheme;

import com.vaadin.server.FontAwesome;

/**
 * This is the place to implement component logic for MessageDesign. The super
 * class should not be edited, because Vaadin Designer overwrites it.
 */
public class MessageComponent extends MessageDesign {

    public MessageComponent(String sender, String subject, String body) {
        senderLabel.setValue(sender);
        messageLabel.setCaption(subject);
        messageLabel.setValue(body);
    }

    public void clearIndicator() {
        setStyle(false, false);
    }

    public void setNewIndicator() {
        setStyle(true, false);
    }

    public void setFlagIndicator() {
        setStyle(false, true);
    }

    private void setStyle(boolean _new, boolean flagged) {
        indicatorButton.removeStyleName(MyTheme.INDICATOR_CIRCLE);
        indicatorButton.removeStyleName(MyTheme.INDICATOR_STAR);
        if (flagged) {
            indicatorButton.setIcon(FontAwesome.STAR);
            indicatorButton.addStyleName(MyTheme.INDICATOR_STAR);
        } else if (_new) {
            indicatorButton.setIcon(FontAwesome.CIRCLE);
            indicatorButton.addStyleName(MyTheme.INDICATOR_CIRCLE);
        } else {
            indicatorButton.setIcon(null);
        }

    }
}
