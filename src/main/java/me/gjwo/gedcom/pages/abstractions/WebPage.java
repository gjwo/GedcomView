package me.gjwo.gedcom.pages.abstractions;

import java.io.IOException;
import java.util.HashMap;
import me.gjwo.gedcom.pages.elements.ElementTypes;

public abstract class WebPage
{
    protected WebPage()
    {
        for (ElementTypes e:ElementTypes.values())
        {
            placeholderMap.put(e.placeholder,e);
        }
    }

    protected final HashMap<ElementTypes, WebElement> elements= new HashMap<>();
    protected final HashMap<String,ElementTypes> placeholderMap= new HashMap<>();

    public abstract String render() throws IOException;

    protected String populate(String content, String tag, WebElement element)
    {
        try{
            content = content.replace(tag, element.render());
        }catch(Exception ignored){
            content = content.replace(tag,"Failed to load");
        }
        return content;
    }
    protected WebElement getWebElement(String placeholder)
    {
        if (placeholder !=null)
            if(placeholderMap.get(placeholder)!= null)
                return elements.get(placeholderMap.get(placeholder));
        return null;
    }
}
