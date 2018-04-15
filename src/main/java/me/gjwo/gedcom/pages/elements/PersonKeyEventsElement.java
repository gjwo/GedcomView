package me.gjwo.gedcom.pages.elements;

import me.gjwo.gedcom.FactPicker;
import me.gjwo.gedcom.HtmlWrapper;
import me.gjwo.gedcom.pages.abstractions.WebElement;
import org.gedcom4j.model.Individual;
import org.gedcom4j.model.enumerations.IndividualEventType;
import java.util.List;

public class PersonKeyEventsElement extends WebElement
{
    private final String htmlString;

    /**
     * PersonKeyEventsElement - Constructor builds an HTML table of key events
     * @param person            The focus person for the events
     */
    public PersonKeyEventsElement(Individual person)
    {
        FactPicker factPicker = new FactPicker(person,null);
        htmlString = HtmlWrapper.wrapTable(
                                    factPicker.pickIndEventTableData(List.of(
                                            IndividualEventType.BIRTH,
                                            IndividualEventType.BAPTISM,
                                            IndividualEventType.DEATH)),
                                    List.of("Event","Date","Place"));
    }

    /**
     * render   -   Returns an HTML string containing a personal events table
     * @return      The html string
     */
    @Override
    public String render() {return htmlString;}
}