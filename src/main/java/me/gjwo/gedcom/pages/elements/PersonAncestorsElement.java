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

import me.gjwo.gedcom.pages.abstractions.WebElement;
import org.gedcom4j.model.Family;
import org.gedcom4j.model.FamilyChild;
import org.gedcom4j.model.Individual;

public class PersonAncestorsElement extends WebElement
{
    private final String htmlString;

    /**
     * PersonKeyEventsElement - Constructor builds an HTML table of key events
     * @param person            The focus person for information
     */
    public PersonAncestorsElement(Individual person)
    {
        Family fa[];
        FamiliesElement pfe;
        fa = person.getFamiliesWhereChild() != null? person.getFamiliesWhereChild().stream().map(FamilyChild::getFamily).toArray(Family[]::new):new Family[0];
        pfe = new FamiliesElement(person,false,false,fa);
        pfe.render();

        PersonFactsBlockElement pfbe;
        pfbe = new PersonFactsBlockElement(person);
        htmlString = pfbe.render();
    }

    /**
     * render   -   Returns an HTML string containing a personal events table
     * @return      The html string
     */
    @Override
    public String render() {return htmlString;}
}