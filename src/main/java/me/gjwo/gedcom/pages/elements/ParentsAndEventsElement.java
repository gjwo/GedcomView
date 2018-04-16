package me.gjwo.gedcom.pages.elements;

import me.gjwo.gedcom.FactPicker;
import me.gjwo.gedcom.HtmlWrapper;
import me.gjwo.gedcom.pages.abstractions.WebElement;
import org.gedcom4j.model.Family;
import org.gedcom4j.model.FamilyChild;
import org.gedcom4j.model.Individual;
import org.gedcom4j.model.enumerations.FamilyEventType;

import java.util.List;

public class ParentsAndEventsElement extends WebElement
{
    private final FamiliesElement pfe;

    /**
     * PersonKeyEventsElement - Constructor builds an HTML table of key events
     * @param person            The focus person for information
     */
    public ParentsAndEventsElement(Individual person)
    {
        Family fa[];
        fa = person.getFamiliesWhereChild() != null? person.getFamiliesWhereChild().stream().map(FamilyChild::getFamily).toArray(Family[]::new):new Family[0];
        pfe = new FamiliesElement(person,false,true,fa);
    }

    /**
     * render   -   Returns an HTML string containing a personal events table
     * @return      The html string
     */
    @Override
    public String render() {return pfe.render();}
}