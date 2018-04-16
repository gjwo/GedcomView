/*
 * Copyright (c) 2018.     GedcomView generates live web pages from a .ged file
 *                                Copyright (C) 2018  Graham J Wood
 *
 *                                This program is free software: you can redistribute it and/or modify
 *                                it under the terms of the GNU General Public License as published by
 *                                the Free Software Foundation, either version 3 of the License, or
 *                                (at your option) any later version.
 *
 *                                This program is distributed in the hope that it will be useful,
 *                                but WITHOUT ANY WARRANTY; without even the implied warranty of
 *                                MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *                                GNU General Public License for more details.
 *
 *                                You should have received a copy of the GNU General Public License
 *                                along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

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
