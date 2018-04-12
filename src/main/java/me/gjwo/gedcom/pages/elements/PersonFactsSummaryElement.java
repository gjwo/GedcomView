package me.gjwo.gedcom.pages.elements;

import me.gjwo.gedcom.FactPicker;
import me.gjwo.gedcom.PersonFactBuilder;
import me.gjwo.gedcom.TableBuilder;
import me.gjwo.gedcom.pages.abstractions.WebElement;
import org.gedcom4j.model.Individual;
import org.gedcom4j.model.IndividualReference;
import org.gedcom4j.model.enumerations.IndividualEventType;

import java.io.IOException;
import java.util.List;

public class PersonFactsSummaryElement extends WebElement
{
    private final Individual person;
    private boolean includeLables;
    private boolean includeTitles;
    private FactPicker factPicker;

    public void setLables(Boolean includeLables){this.includeLables = includeLables;}
    public void setDates(Boolean includeTitles){this.includeTitles = includeTitles;}

    public PersonFactsSummaryElement(Individual person)
    {
        this.person = person;
        this.includeLables = true;
        this.includeTitles = false;
        this.factPicker = new FactPicker(person,null);

    }

    @Override
    public String render() throws IOException {
        return TableBuilder.buildTable(
                factPicker.getIndSummaryTableData(List.of(person)),
                List.of("Ref","Name","Birth date","Birth place", "Death Date", "Death place"));
    }
}