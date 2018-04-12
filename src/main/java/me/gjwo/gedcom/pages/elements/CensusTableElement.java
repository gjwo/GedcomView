package me.gjwo.gedcom.pages.elements;

import me.gjwo.gedcom.FactPicker;
import me.gjwo.gedcom.PersonFactBuilder;
import me.gjwo.gedcom.TableBuilder;
import me.gjwo.gedcom.pages.abstractions.WebElement;
import org.gedcom4j.model.Individual;
import org.gedcom4j.model.enumerations.IndividualEventType;

import java.io.IOException;
import java.util.List;


public class CensusTableElement extends WebElement
{
    private final FactPicker factPicker;

    public CensusTableElement(Individual person)
    {
        this.factPicker = new FactPicker(person,null);
    }

    private String buildPersonsCensusTable() throws IOException {
        return TableBuilder.buildTable(
                factPicker.getIndEventTableData(List.of(IndividualEventType.CENSUS)),
                List.of("Event","Date","Place"));
    }

    @Override
    public String render() throws IOException
    {
        return buildPersonsCensusTable();
    }
}
