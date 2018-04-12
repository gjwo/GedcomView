package me.gjwo.gedcom.pages.elements;

import me.gjwo.gedcom.PersonFactBuilder;
import me.gjwo.gedcom.pages.abstractions.WebElement;
import org.gedcom4j.model.Individual;

import java.io.IOException;

public class PersonKeyEventsElement extends WebElement
{
    private final Individual person;
    private boolean includeLables;
    private boolean includeTitles;

    public void setLables(Boolean includeLables){this.includeLables = includeLables;}
    public void setDates(Boolean includeTitles){this.includeTitles = includeTitles;}

    public PersonKeyEventsElement(Individual person)
    {
        this.person = person;
        this.includeLables = true;
        this.includeTitles = true;
    }

    @Override
    public String render() throws IOException {
        PersonFactBuilder fb = new PersonFactBuilder(person);
        return fb.buildKeyEventsTable(includeTitles,includeLables);
    }
}
