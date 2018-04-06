package me.gjwo.gedcom.pages.elements;

import me.gjwo.gedcom.LinkBuilder;
import me.gjwo.gedcom.pages.abstractions.WebElement;
import org.gedcom4j.model.Individual;

import java.io.IOException;

public class IndividualElement extends WebElement
{
    private final Individual individual;
    private boolean linkToFamily;

    public void setLinkIndividual(){linkToFamily=false;}
    public void setlinkFamily(){linkToFamily=true;}

    public IndividualElement(Individual individual)
    {
        this.individual = individual;
        this.linkToFamily = true;
    }

    @Override
    public String render() throws IOException {
        LinkBuilder lb = new LinkBuilder();
        return linkToFamily?lb.buildPersonFamilyLink(individual):lb.buildPersonIndividualLink(individual);
    }
}
