package me.gjwo.gedcom.pages.elements;

import me.gjwo.gedcom.FactPicker;
import me.gjwo.gedcom.HtmlWrapper;
import me.gjwo.gedcom.pages.abstractions.WebElement;
import org.gedcom4j.model.Family;
import org.gedcom4j.model.enumerations.FamilyEventType;
import org.gedcom4j.model.enumerations.IndividualEventType;

import java.util.List;

public class FamilyKeyEventsElement extends WebElement
{
    private final String htmlString;

    /**
     * PersonKeyEventsElement - Constructor builds an HTML table of key events
     * @param family            The focus family for the events
     */
    public FamilyKeyEventsElement(Family family)
    {
        FactPicker factPicker = new FactPicker(null,family);
        htmlString = HtmlWrapper.wrapTable(
                                    factPicker.pickFamilyEventTableData(List.of(
                                            FamilyEventType.MARRIAGE,
                                            FamilyEventType.DIVORCE)),
                                    List.of("Event","Date","Place"));
    }

    /**
     * render   -   Returns an HTML string containing a personal events table
     * @return      The html string
     */
    @Override
    public String render() {return htmlString;}
}