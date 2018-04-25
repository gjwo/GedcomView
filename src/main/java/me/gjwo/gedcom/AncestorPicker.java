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

import java.util.Arrays;

/**
 * This class finds a specific ancestor of a given person based on a placeholder
 * which contains a route to the required ancestor for example
 * the person's Mother's Father's Mother would be "!MFM!"
 */
public class AncestorPicker
{
    private final Individual focusPerson;
    private char[] ancestorPath;

    /**
     * Class Constructor
     * @param person        The starting person
     * @param placeholder   !MFMF! where M = Mother F = Father so !MF! would be the maternal grandfather etc.
     * @throws IllegalArgumentException if the person is null
     */
    public AncestorPicker(Individual person, String placeholder)
    {
        focusPerson = person;
        if (!buildPath(placeholder)){
            ancestorPath = new char[0];
        }
    }

    /**
     * Finds the ancestor stored in the class  of the focus person stored in the class
     * @return the Individual ancestor or null if not found
     */
    public Individual findAncestor()
    {
        return findAncestor(focusPerson, ancestorPath);
    }

    /**
     * Finds the ancestor of the focus person stored in the class
     * @param placeholder   !MFMF! where M = Mother F = Father so !MF! would be the maternal grandfather etc.
     * @return the Individual ancestor or null if not found
     */
    public Individual findAncestor(String placeholder)
    {
        if (buildPath(placeholder))
            return findAncestor(focusPerson, ancestorPath);
        else return null;
    }

    /**
     * Validates and builds an array of characters from the placeholder string
     * @param placeholder !MFMF! where M = Mother F = Father so !MF! would be the maternal grandfather etc.
     * @return true if the string is valid and the class has been updated
     */
    private boolean buildPath(String placeholder)
    {
        String ph = placeholder.replace("!","").toUpperCase().trim();
        if (ph.length() !=0)
        {
            ancestorPath = ph.toCharArray();
            for (char c : ancestorPath)
                if ((c != 'F') && (c != 'M'))
                    return false;
        }
        else return false;
        return true;
    }

    /**
     * A recursive method to find the specified ancestor of the person
     * @param person    The immediate descendant of the next candidate ancestor
     * @param path      [M,F,M,F] where M = Mother F = Father so [M,F] would be the maternal grandfather etc.
     * @return          The Individual person if found, null otherwise
     */
    private Individual findAncestor(Individual person, char[] path)
    {
        Individual nextPerson;
        FamilyChild fc;
        Family family;
        IndividualReference parentRef;
        if (person == null) return null;
        if (path.length==0 ) return person;

        if (person.getFamiliesWhereChild()!= null) {
            if (person.getFamiliesWhereChild().get(0) != null)
                fc = person.getFamiliesWhereChild().get(0);
            else return null;
        }else return null;

        if (path[0]=='M') //find mother
            if (fc.getFamily() != null) {
                family = fc.getFamily();
                parentRef = family.getWife();
                if (parentRef != null)
                    nextPerson = family.getWife().getIndividual();
                else return null;
            } else return null;
        else
            if (path[0] == 'F') //find father
                if (fc.getFamily() != null) {
                family = fc.getFamily();
                parentRef = family.getHusband();
                if (parentRef != null)
                    nextPerson = family.getHusband().getIndividual();
                else return null;
            } else return null;
            else return null; //invalid character in path

        if (path.length==1)  return nextPerson; // we have found what we were looking for
        else
        { // not gone back far enough yet
            path = Arrays.copyOfRange(path, 1, path.length); //remove first character in path as we have done that
            return findAncestor(nextPerson,path); //recurse with new focus and path
        }
    }
}
