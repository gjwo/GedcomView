package me.gjwo.gedcom.pages.elements;

import me.gjwo.gedcom.HtmlWrapper;
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

    public PersonLinkElement( )
    {
        super(null);
        System.out.println("PersonLinkElement() called");
        this.person = null;
        this.linkToFamily = true;
        this.includeRef = true;
        this.includeShortDates = true;
    }

    /**
     * PersonLinkElement    -   Constructor
     * @param person            The focus person
     */
    public PersonLinkElement(Individual person)
    {
        super(person);
        System.out.println("PersonLinkElement(person) called");
        this.person = person;
        this.linkToFamily = true;
        this.includeRef = true;
        this.includeShortDates = true;
    }

    @Override
    public String render()
    {
        System.out.println("PersonLinkElement.render()");
        PersonFactBuilder pfb = new PersonFactBuilder(person);
        StringBuilder sb = new StringBuilder();
        sb.append(linkToFamily? HtmlWrapper.wrapHyperlink(pfb.buildPersonFamilyLink(person), person.getFormattedName())
                                :HtmlWrapper.wrapHyperlink(pfb.buildPersonIndividualLink(person), person.getFormattedName()));
        if(includeRef)
        {
            sb.append(" #");
            sb.append(pfb.getRefNumber());
            sb.append(" ");
        }
        if(includeShortDates) sb.append(pfb.getShortDates());
        return sb.toString();
    }
}
