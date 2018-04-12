package me.gjwo.gedcom.pages.elements;

import me.gjwo.gedcom.FactPicker;
import me.gjwo.gedcom.PersonFactBuilder;
import me.gjwo.gedcom.TableBuilder;
import me.gjwo.gedcom.pages.abstractions.WebElement;
import org.gedcom4j.model.Individual;
import org.gedcom4j.model.enumerations.IndividualEventType;

import java.io.IOException;
import java.util.List;

public class PersonKeyEventsElement extends WebElement
{
    private final Individual person;
    private FactPicker factPicker;


    public PersonKeyEventsElement(Individual person)
    {
        this.person = person;
        factPicker = new FactPicker(person,null);
    }

    @Override
    public String render() throws IOException {

        return TableBuilder.buildTable(
                    factPicker.getIndEventTableData(List.of(
                            IndividualEventType.BIRTH,
                            IndividualEventType.BAPTISM,
                            IndividualEventType.DEATH)),
                    List.of("Event","Date","Place"));
    }
}