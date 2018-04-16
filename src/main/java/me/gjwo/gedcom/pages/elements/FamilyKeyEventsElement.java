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
import me.gjwo.gedcom.HtmlWrapper;
import me.gjwo.gedcom.pages.abstractions.WebElement;
import org.gedcom4j.model.Family;
import org.gedcom4j.model.enumerations.FamilyEventType;
import org.gedcom4j.model.enumerations.IndividualEventType;

import java.util.List;

public class FamilyKeyEventsElement extends WebElement
{
    private final String htmlString;

    /**
     * PersonKeyEventsElement - Constructor builds an HTML table of key events
     * @param family            The focus family for the events
     */
    public FamilyKeyEventsElement(Family family)
    {
        FactPicker factPicker = new FactPicker(null,family);
        htmlString = HtmlWrapper.wrapTable(
                                    factPicker.pickFamilyEventTableData(List.of(
                                            FamilyEventType.MARRIAGE,
                                            FamilyEventType.DIVORCE)),
                                    List.of("Event","Date","Place"));
    }

    /**
     * render   -   Returns an HTML string containing a personal events table
     * @return      The html string
     */
    @Override
    public String render() {return htmlString;}
}