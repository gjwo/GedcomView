package me.gjwo.gedcom.pages.elements;

import edu.emory.mathcs.backport.java.util.Arrays;
import me.gjwo.gedcom.FactPicker;
import me.gjwo.gedcom.PersonFactBuilder;
import me.gjwo.gedcom.pages.abstractions.WebElement;
import org.gedcom4j.model.Individual;
import me.gjwo.gedcom.TableBuilder;
import org.gedcom4j.model.enumerations.IndividualEventType;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TestElement extends WebElement
{
    private boolean includeLables;
    private boolean includeTitles;
    private  TableBuilder testTable;
    private FactPicker factPicker;

    public void setLables(Boolean includeLables){this.includeLables = includeLables;}
    public void setDates(Boolean includeTitles){this.includeTitles = includeTitles;}

    public TestElement(Individual person)
    {
        this.includeLables = true;
        this.includeTitles = true;
        this.factPicker = new FactPicker(person,null);
        this.testTable = new TableBuilder();
    }

    @Override
    public String render() throws IOException {
        return testTable.buildTable( factPicker.getTableData(IndividualEventType.CENSUS),List.of("Date","Place"));
    }
}
