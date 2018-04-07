package me.gjwo.gedcom.pages.elements;

import me.gjwo.gedcom.PersonFactBuilder;
import me.gjwo.gedcom.LinkBuilder;
import me.gjwo.gedcom.pages.abstractions.WebElement;
import org.gedcom4j.model.Individual;

import java.io.IOException;

public class PersonLinkElement extends WebElement
{
    private final Individual individual;
    private boolean linkToFamily;
    private boolean includeRef;
    private boolean includeShortDates;

    public void setLinkIndividual(){linkToFamily=false;}
    public void setlinkFamily(){linkToFamily=true;}

    public PersonLinkElement(Individual individual)
    {
        this.individual = individual;
        this.linkToFamily = true;
        this.includeRef = true;
        this.includeShortDates = true;
    }

    @Override
    public String render() throws IOException {
        LinkBuilder lb = new LinkBuilder();
        PersonFactBuilder fb = new PersonFactBuilder(individual);
        StringBuilder sb = new StringBuilder();
        if(includeRef) sb.append(fb.getRefNumber());
        return linkToFamily?lb.buildPersonFamilyLink(individual):lb.buildPersonIndividualLink(individual);
    }
}
