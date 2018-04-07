package me.gjwo.gedcom.pages.elements;

import me.gjwo.gedcom.LinkBuilder;
import me.gjwo.gedcom.PersonFactBuilder;
import me.gjwo.gedcom.pages.abstractions.WebElement;
import org.gedcom4j.model.Family;
import org.gedcom4j.model.Individual;

import java.io.IOException;

import static me.gjwo.gedcom.FileUtil.readFile;


public class CensusTable extends WebElement
{
    private final Individual person;

    public CensusTable(Individual person)
    {
        this.person = person;
    }

    private String buildPersonsCensusTable() throws IOException {
        PersonFactBuilder fb = new PersonFactBuilder(person);
        return fb.buildCensusTable(Boolean.TRUE);
    }

    @Override
    public String render() throws IOException
    {
        return buildPersonsCensusTable();
    }
}
