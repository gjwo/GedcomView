package me.gjwo.gedcom.pages.elements;

import me.gjwo.gedcom.FactPicker;
import me.gjwo.gedcom.HtmlWrapper;
import me.gjwo.gedcom.pages.abstractions.WebElement;
import org.gedcom4j.model.Individual;

import java.util.List;

public class PersonFactsSummaryElement extends WebElement
{
    private final Individual person;
    private boolean includeLabels;
    private boolean includeTitles;
    private final FactPicker factPicker;

    public void setLabels(Boolean includeLabels){this.includeLabels = includeLabels;}
    public void setDates(Boolean includeTitles){this.includeTitles = includeTitles;}

    public PersonFactsSummaryElement(Individual person)
    {
        this.person = person;
        this.includeLabels = true;
        this.includeTitles = false;
        this.factPicker = new FactPicker(person,null);

    }

    @Override
    public String render()
    {
        return HtmlWrapper.wrapTable(
                factPicker.getIndSummaryTableData(List.of(person)),
                List.of("Ref","Name","Birth date","Birth place", "Death Date", "Death place"));
    }
}