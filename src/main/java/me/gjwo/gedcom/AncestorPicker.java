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
    public AncestorPicker(Individual person, String placeholder) throws IllegalArgumentException
    {
        if (person == null) throw new IllegalArgumentException("Null person in AncestorPicker constructor");
        focusPerson = person;
        if (!buildPath(placeholder)) throw new IllegalArgumentException("Invalid ancestor path in AncestorPicker constructor");
        // have a valid ancestor path
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
                if ((c != 'M') && (c != 'F')) return false;
        }
        else  return false;
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
        Individual nextPerson = null;

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
                    nextPerson = family.getWife().getIndividual();
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
                    nextPerson =  family.getHusband().getIndividual();
            }else return null;
        }
        if (nextPerson==null)return  null; //invalid character

        if (path.length==1)  return nextPerson;
        else // not gone back far enough yet, recurse
            return findAncestor(nextPerson, Arrays.copyOfRange(path,1,path.length-1));
    }
}
