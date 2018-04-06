package me.gjwo.gedcom.pages.abstractions;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;

public abstract class WebPage
{
    public WebPage() {
        this.elements = new HashMap<>();
    }

    protected HashMap<Integer, WebElement> elements;

    public abstract String render() throws IOException;

}
