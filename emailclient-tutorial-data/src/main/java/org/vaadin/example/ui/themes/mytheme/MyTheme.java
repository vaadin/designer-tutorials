package org.vaadin.example.ui.themes.mytheme;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MyTheme {
    public static final String INDICATOR_STAR_RED = "indicator-star-red";
    public static final String INDICATOR_CIRCLE = "indicator-circle";
    public static final String SELECTED = "selected";

    public static final List<String> MESSAGE_STYLES = Collections
            .unmodifiableList(Arrays.asList(MyTheme.INDICATOR_CIRCLE,
                    MyTheme.INDICATOR_STAR_RED));
}
