package me.gjwo.gedcom.pages.elements;

import me.gjwo.gedcom.FactPicker;
import me.gjwo.gedcom.pages.abstractions.WebElement;
import org.gedcom4j.model.Individual;
import me.gjwo.gedcom.HtmlWrapper;
import org.gedcom4j.model.enumerations.IndividualEventType;

import java.util.List;

public class TestElement extends WebElement
{
    private final FactPicker factPicker;

    public TestElement(Individual person)
    {
        this.factPicker = new FactPicker(person,null);
    }

    @Override
    public String render()  {
        return HtmlWrapper.wrapTable(
                factPicker.pickIndEventTableData(List.of(IndividualEventType.BIRTH,
                                                        IndividualEventType.BAPTISM,
                                                        IndividualEventType.DEATH,
                                                        IndividualEventType.CENSUS)),
                                                List.of("Event","Date","Place"));
    }
}
