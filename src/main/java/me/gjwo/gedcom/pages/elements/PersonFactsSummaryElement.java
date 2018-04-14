package me.gjwo.gedcom.pages.elements;

import me.gjwo.gedcom.FactPicker;
import me.gjwo.gedcom.HtmlWrapper;
import me.gjwo.gedcom.pages.abstractions.WebElement;
import org.gedcom4j.model.Individual;

import java.util.List;

public class PersonFactsSummaryElement extends WebElement
{
    private final String htmlString;

    /**
     * PersonFactsSummaryElement    -   Builds an HTML table containing key personal events on a single row
     * @param person                    the person that is the focus
     */
    public PersonFactsSummaryElement(Individual person)
    {
        FactPicker factPicker = new FactPicker(person,null);
        htmlString = HtmlWrapper.wrapTable(
                                    factPicker.getIndSummaryTableData(List.of(person)),
                                    List.of("Ref","Name","Birth date","Birth place", "Death Date", "Death place"));
    }

    /**
     * render   -   returns HTML containing a summary fact table
     * @return      the html string
     */
    @Override
    public String render(){return htmlString;}
}