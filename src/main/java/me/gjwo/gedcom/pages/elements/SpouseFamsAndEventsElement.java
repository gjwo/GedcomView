package me.gjwo.gedcom.pages.elements;

import me.gjwo.gedcom.pages.abstractions.WebElement;
import org.gedcom4j.model.Family;
import org.gedcom4j.model.FamilyChild;
import org.gedcom4j.model.FamilySpouse;
import org.gedcom4j.model.Individual;

public class SpouseFamsAndEventsElement extends WebElement
{
    private final FamiliesElement pfe;

    /**
     * PersonKeyEventsElement - Constructor builds an HTML table of key events
     * @param person            The focus person for information
     */
    public SpouseFamsAndEventsElement(Individual person)
    {
        Family fa[];
        fa = person.getFamiliesWhereSpouse() != null? person.getFamiliesWhereSpouse().stream().map(FamilySpouse::getFamily).toArray(Family[]::new):new Family[0];
        pfe =  new FamiliesElement(person,true,true, fa);
    }

    /**
     * render   -   Returns an HTML string containing a personal events table
     * @return      The html string
     */
    @Override
    public String render() {return pfe.render();}
}