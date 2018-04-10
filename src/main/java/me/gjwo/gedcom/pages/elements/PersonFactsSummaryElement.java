package me.gjwo.gedcom.pages.elements;

import me.gjwo.gedcom.PersonFactBuilder;
import me.gjwo.gedcom.pages.abstractions.WebElement;
import org.gedcom4j.model.Individual;

import java.io.IOException;

public class PersonFactsSummaryElement extends WebElement
{
    private final Individual person;
    private boolean includeLables;
    private boolean includeTitles;

    public void setLables(Boolean includeLables){this.includeLables = includeLables;}
    public void setDates(Boolean includeTitles){this.includeTitles = includeTitles;}

    public PersonFactsSummaryElement(Individual person)
    {
        this.person = person;
        this.includeLables = true;
        this.includeTitles = false;
    }

    @Override
    public String render() throws IOException {
        String content;
        PersonFactBuilder fb = new PersonFactBuilder(person);
        return fb.buildKeyEventsTable(includeTitles,includeLables);
    }
}
