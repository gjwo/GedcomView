package me.gjwo.gedcom.pages.elements;

import me.gjwo.gedcom.FactPicker;
import me.gjwo.gedcom.PersonFactBuilder;
import me.gjwo.gedcom.HtmlWrapper;
import me.gjwo.gedcom.pages.abstractions.WebElement;
import org.gedcom4j.model.Individual;
import org.gedcom4j.model.enumerations.IndividualAttributeType;

import java.util.List;

public class PersonAttributesElement extends WebElement
{
    private final Individual person;
    private boolean includeLabels;
    private boolean includeTitles;
    private final FactPicker factPicker;

    public void setLabels(Boolean includeLabels){this.includeLabels = includeLabels;}
    public void setDates(Boolean includeTitles){this.includeTitles = includeTitles;}

    public PersonAttributesElement(Individual person)
    {
        this.person = person;
        this.includeLabels = true;
        this.includeTitles = true;
        this.factPicker = new FactPicker(person,null);
    }

    @Override
    public String render()
    {
        PersonFactBuilder fb = new PersonFactBuilder(person);
        return HtmlWrapper.wrapTable(
                factPicker.getIndAttributeTableData(
                        List.of(IndividualAttributeType.OCCUPATION,
                                IndividualAttributeType.RESIDENCE)),
                        List.of("Attribute","Date", "Place","Description"));
    }
}
