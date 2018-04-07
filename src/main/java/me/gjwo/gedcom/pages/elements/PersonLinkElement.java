package me.gjwo.gedcom.pages.elements;

import me.gjwo.gedcom.PersonFactBuilder;
import me.gjwo.gedcom.LinkBuilder;
import me.gjwo.gedcom.pages.abstractions.WebElement;
import org.gedcom4j.model.Individual;

import java.io.IOException;

public class PersonLinkElement extends WebElement
{
    private final Individual person;
    private boolean linkToFamily;
    private boolean includeRef;
    private boolean includeShortDates;

    public void setLinkIndividual(){linkToFamily=false;}
    public void setlinkFamily(){linkToFamily=true;}
    public void setRef(Boolean includeRef){this.includeRef=includeRef;}
    public void setDates(Boolean includeShortDates){this.includeShortDates = includeShortDates;}

    public PersonLinkElement(Individual person)
    {
        this.person = person;
        this.linkToFamily = true;
        this.includeRef = true;
        this.includeShortDates = true;
    }

    @Override
    public String render() throws IOException {
        LinkBuilder lb = new LinkBuilder();
        PersonFactBuilder fb = new PersonFactBuilder(person);
        StringBuilder sb = new StringBuilder();
        sb.append(linkToFamily?lb.buildPersonFamilyLink(person):lb.buildPersonIndividualLink(person));
        if(includeRef)
        {
            sb.append(" #");
            sb.append(fb.getRefNumber());
            sb.append(" ");
        }
        if(includeShortDates)
        {   sb.append(" (");
            sb.append(fb.getDateOfBirth());
            sb.append(" - ");
            sb.append(fb.getDateOfDeath());
            sb.append(")");
        }
        return sb.toString();
    }
}
