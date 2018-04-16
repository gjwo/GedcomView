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

import java.io.IOException;

public class PersonFamLinkElement extends WebElement
{
    private final Individual person;
    private boolean linkToFamily;
    private boolean includeRef;
    private boolean includeShortDates;

    public void setLinkIndividual(){linkToFamily=false;}
    public void setlinkFamily(){linkToFamily=true;}
    public void setRef(Boolean includeRef){this.includeRef=includeRef;}
    public void setDates(Boolean includeShortDates){this.includeShortDates = includeShortDates;}

    /**
     * PersonLinkElement    -   Constructor
     * @param person            The focus person
     */
    public PersonFamLinkElement(Individual person)
    {
        this.person = person;
        this.linkToFamily = true;
        this.includeRef = true;
        this.includeShortDates = true;
    }

    @Override
    public String render()
    {
        PersonFactBuilder pfb = new PersonFactBuilder(person);
        StringBuilder sb = new StringBuilder();
        sb.append(linkToFamily? HtmlWrapper.wrapHyperlink(pfb.buildPersonFamilyLink(person), person.getFormattedName())
                                :HtmlWrapper.wrapHyperlink(pfb.buildPersonIndividualLink(person), person.getFormattedName()));
        if(includeRef)
        {
            sb.append(" #");
            sb.append(pfb.getRefNumber());
            sb.append(" ");
        }
        if(includeShortDates) sb.append(pfb.getShortDates());
        return sb.toString();
    }
}
