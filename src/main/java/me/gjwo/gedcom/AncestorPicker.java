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

package me.gjwo.gedcom;

import org.gedcom4j.model.Family;
import org.gedcom4j.model.FamilyChild;
import org.gedcom4j.model.Individual;
import org.gedcom4j.model.IndividualReference;

public class AncestorPicker
{
    private final Individual focusPerson;
    private char[] ancestorPath;
    AncestorPicker(Individual person, String placeholder) throws IllegalArgumentException
    {
        if (person == null) throw new IllegalArgumentException("Null person in AncestorPicker constructor");
        focusPerson = person;
        String ph = placeholder.replace("!","").toUpperCase().trim();
        if (ph.length() !=0)
        {
            ancestorPath = ph.toCharArray();
            for (char c : ancestorPath)
            {
                if( (c!='M') && (c!='F')) throw new IllegalArgumentException("invalid character in ancestor path in AncestorPicker constructor");

            }
        }
        else  throw new IllegalArgumentException("no valid characters in ancestor path in AncestorPicker constructor");
        // have a valid ancestor path
    }

    Individual findAncestor(Individual person, char[] path)
    {
        FamilyChild fc;
        if (person.getFamiliesWhereChild()!= null) {
            if (person.getFamiliesWhereChild().get(0) != null)
                fc = person.getFamiliesWhereChild().get(0);
            else return null;
        }else return null;
        if (path[0]=='M') {
            //find mother
            Family family;
            if (fc.getFamily() != null) {
                family = fc.getFamily();
                IndividualReference wifeRef = family.getWife();
                if (wifeRef != null)
                    return family.getWife().getIndividual();
            } else return null;
        }
        if (path[0]=='F')
        {
            //find father
            Family family;
            if (fc.getFamily()!= null) {
                family = fc.getFamily();
                IndividualReference husbandRef = family.getHusband();
                if (husbandRef!=null)
                    return family.getHusband().getIndividual();
            }else return null;
        }
        return null; //invalid character
    }
}
