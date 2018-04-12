package me.gjwo.gedcom.pages.elements;

import me.gjwo.gedcom.FactPicker;
import me.gjwo.gedcom.pages.abstractions.WebElement;
import org.gedcom4j.model.Individual;
import me.gjwo.gedcom.TableBuilder;
import org.gedcom4j.model.enumerations.IndividualEventType;

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
    public String render()  {
        return testTable.buildTable(
                factPicker.getIndEventTableData(List.of(IndividualEventType.BIRTH,
                                                IndividualEventType.BAPTISM,
                                                IndividualEventType.DEATH,
                                                IndividualEventType.CENSUS)),
                                        List.of("Date","Place"));
    }
}
