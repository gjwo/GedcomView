package me.gjwo.gedcom.pages.elements;

import me.gjwo.gedcom.PersonFactBuilder;
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
        PersonFactBuilder pfb = new PersonFactBuilder(person);
        StringBuilder sb = new StringBuilder();
        sb.append(linkToFamily?pfb.buildPersonFamilyLink(person):pfb.buildPersonIndividualLink(person));
        if(includeRef)
        {
            sb.append(" #");
            sb.append(pfb.getRefNumber());
            sb.append(" ");
        }
        if(includeShortDates)
        {   sb.append(" (");
            sb.append(pfb.getDateOfBirth());
            sb.append(" - ");
            sb.append(pfb.getDateOfDeath());
            sb.append(")");
        }
        return sb.toString();
    }
}
