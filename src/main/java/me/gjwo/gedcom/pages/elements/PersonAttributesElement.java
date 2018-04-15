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
    private final String htmlString;

    /**
     * PersonAttributesElement  -   Constructor builds a table of personal attributes as an HTML string
     * @param person                The focus individual
     */
    public PersonAttributesElement(Individual person)
    {
        FactPicker factPicker = new FactPicker(person,null);
        htmlString =  HtmlWrapper.wrapTable(
                factPicker.getIndAttributeTableData(
                        List.of(IndividualAttributeType.OCCUPATION,
                                IndividualAttributeType.RESIDENCE)),
                List.of("Attribute","Date", "Place","Description"));
    }

    /**
     * render   -   Supplies the person's attributes as an HTML string
     * @return      the HTML String
     */
    @Override
    public String render() {return htmlString;}
}
