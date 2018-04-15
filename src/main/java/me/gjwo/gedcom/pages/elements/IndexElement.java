package me.gjwo.gedcom.pages.elements;

import me.gjwo.gedcom.HtmlWrapper;
import me.gjwo.gedcom.pages.abstractions.WebElement;

import java.io.IOException;

public class IndexElement extends WebElement
{
    private final String htmlString;
    public IndexElement()
    {
        StringBuilder sb = new StringBuilder();
        sb.append(HtmlWrapper.wrapHyperlink("/nameindex/","All&nbsp;"));
        for(char c = 65; c<91; c++)
        {
            sb.append(HtmlWrapper.wrapHyperlink("/nameindex/"+c,Character.toString(c)+"&nbsp;"));
        }
        sb.append(HtmlWrapper.wrapHyperlink("/nameindex/1","Other"));
        htmlString = HtmlWrapper.wrapDiv(HtmlWrapper.wrapCenter(sb.toString()));
    }

    @Override
    public String render() throws IOException
    {
        return htmlString;
    }
}
