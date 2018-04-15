package me.gjwo.gedcom.pages.elements;

import me.gjwo.gedcom.FactPicker;
import me.gjwo.gedcom.HtmlWrapper;
import me.gjwo.gedcom.pages.abstractions.WebElement;
import org.gedcom4j.model.Individual;
import org.gedcom4j.model.enumerations.IndividualEventType;

import java.io.IOException;
import java.util.List;


public class CensusTableElement extends WebElement
{
    private final String htmlString;
    /**
     * CensusTableElement   -   Constructor, builds an HTML table of census events into a string
     * @param person            the person to collect events for
     */
    public CensusTableElement(Individual person)
    {
        super(person);
        FactPicker factPicker = new FactPicker(person,null);
        htmlString = HtmlWrapper.wrapTable(
                factPicker.pickIndEventTableData(List.of(IndividualEventType.CENSUS)),
                List.of("Event","Date","Place"));
    }

    /**
     * render   -   returns an HTML table for Census events for the specified individual
     * @return      An HTML table
     */
    @Override
    public String render() {return htmlString;}
}
