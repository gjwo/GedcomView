package me.gjwo.gedcom.pages.elements;

import me.gjwo.gedcom.PersonFactBuilder;
import me.gjwo.gedcom.pages.abstractions.WebElement;
import org.gedcom4j.model.Individual;

import java.io.IOException;

import static me.gjwo.gedcom.FileUtil.readFile;

public class PersonKeyEventsElement extends WebElement
{
    private final Individual person;
    private boolean includeLables;
    private boolean includeTitles;

    public void setRef(Boolean includeRef){this.includeLables = includeRef;}
    public void setDates(Boolean includeShortDates){this.includeTitles = includeShortDates;}

    public PersonKeyEventsElement(Individual person)
    {
        this.person = person;
        this.includeLables = true;
        this.includeTitles = true;
    }

    @Override
    public String render() throws IOException {
        String content;
        PersonFactBuilder fb = new PersonFactBuilder(person);
        return fb.buildFactTable(includeTitles,includeLables);
    }
}
