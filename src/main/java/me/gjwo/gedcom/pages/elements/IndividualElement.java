package me.gjwo.gedcom.pages.elements;

import me.gjwo.gedcom.LinkBuilder;
import me.gjwo.gedcom.pages.abstractions.WebElement;
import org.gedcom4j.model.Individual;

import java.io.IOException;

public class IndividualElement extends WebElement
{
    private final Individual individual;

    public IndividualElement(Individual individual)
    {
        this.individual = individual;
    }

    @Override
    public String render() throws IOException {
        LinkBuilder lb = new LinkBuilder();
        return lb.buildPersonLink(individual);
    }
}
