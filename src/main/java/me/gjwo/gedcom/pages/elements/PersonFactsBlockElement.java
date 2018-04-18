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

import me.gjwo.gedcom.HtmlWrapper;
import me.gjwo.gedcom.PersonFactBuilder;
import me.gjwo.gedcom.pages.abstractions.WebElement;
import org.gedcom4j.model.Individual;

import java.util.Collections;
import java.util.List;

public class PersonFactsBlockElement extends WebElement
{
    private final String htmlString;

    /**
     * PersonFactsSummaryElement    -   Builds an HTML table containing key personal events on a single row
     * @param person                    the person that is the focus
     */
    public PersonFactsBlockElement(Individual person)
    {
        PersonFactBuilder pfb = new PersonFactBuilder(person);
        String nameLink = HtmlWrapper.wrapHyperlink(pfb.buildPersonFamilyLink(person), pfb.tail(person.getFormattedName().replace("/",""),25));
        String birth = "b."+pfb.tail(pfb.getDateOfBirth(),4)+"&nbsp;"+pfb.head(pfb.getPlaceOfBirth(),22);
        String death = "d."+pfb.tail(pfb.getDateOfDeath(),4)+"&nbsp;"+pfb.head(pfb.getPlaceOfDeath(),22);
        htmlString = HtmlWrapper.wrapTable( List.of(List.of(nameLink),List.of(birth),List.of(death)),
                                    Collections.emptyList());
    }

    /**
     * render   -   returns HTML containing a summary fact table
     * @return      the html string
     */
    @Override
    public String render(){return htmlString;}
}