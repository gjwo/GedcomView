package me.gjwo.gedcom.pages.abstractions;

import java.io.IOException;
import java.util.HashMap;
import me.gjwo.gedcom.pages.elements.ElementTypes;

public abstract class WebPage
{
    protected WebPage() {
        this.elements = new HashMap<>();
    }

    protected final HashMap<ElementTypes, WebElement> elements;

    public abstract String render() throws IOException;

}
