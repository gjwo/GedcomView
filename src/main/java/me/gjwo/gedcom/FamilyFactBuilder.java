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

import org.gedcom4j.model.*;
import org.gedcom4j.model.enumerations.FamilyEventType;

import java.util.*;

/**
 * FamilyFactBuilder    -   Build strings and html fragments from a focusFamily's data including
 *                          Event data Marriage, Divorce etc
 *                          Relationship data  Spouse, Children etc.
 *                          Data is often formatted into tables for use as an element of a page
 */
public class FamilyFactBuilder
{
    private final Family focusFamily;
    private final Map<FamilyEventType,String> eventMap; // Events with labels
    private Individual focusPerson;

    public FamilyFactBuilder(Family family)
    {
        this.focusFamily = family;
        eventMap = new HashMap<>();
        for(FamilyEventType fe:FamilyEventType.values())
        {
            String s = fe.toString();
            s = s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();
            eventMap.put(fe,s);
        }

    }
    String getFamilyEventLabel(FamilyEventType fet)
    {
        return eventMap.get(fet);
    }

    public static Family getSpousalFamily(Individual person, Individual spouse)
    {
        Family fa[];
        Family f;
        if (person.getFamiliesWhereSpouse() != null)
        {
            //fa = person.getFamiliesWhereSpouse().stream().map(FamilySpouse::getFamily).toArray(Family[]::new);
            for( FamilySpouse fs: person.getFamiliesWhereSpouse())
            {
                f = fs.getFamily();
                if(f.getHusband().getIndividual().equals(spouse))return f;
                else if(f.getWife().getIndividual().equals(spouse)) return f;
            }
            System.out.print("no spousal match");
            return null;
        }
        else return null;
     }
    public static Family getParentalFamily(Individual person)
    {
        return person.getFamiliesWhereChild().get(0).getFamily();
    }

    public String getRefNumber()
    {
        return focusFamily.getXref().replace("@F","").replace("@","");
    }


    String getDateOfEvent(FamilyEvent fe)
    {
        StringBuilder sb = new StringBuilder();
        if (fe.getDate() != null && fe.getDate().trim().length() > 0)
            sb.append(fe.getDate());
        return sb.toString();
    }

    String getPlaceOfEvent(FamilyEvent fe)
    {
        StringBuilder sb = new StringBuilder();
        if (fe.getPlace() != null && fe.getPlace().getPlaceName() != null)
            sb.append(fe.getPlace().getPlaceName());
        return sb.toString();
    }

    String getDescriptionOfEvent(FamilyEvent fe)
    {
        StringBuilder sb = new StringBuilder();

        if (fe.getDescription()!= null)
            sb.append(fe.getDescription());
        return sb.toString();
    }


    /**
     * Get a list of events of the supplied type for the specified focusFamily
     * @param type      the type of event to get
     * @return          a list of events of the specified type
     */

    List<FamilyEvent> getEventsOfType(FamilyEventType type) {
        List<FamilyEvent> result = new ArrayList<>(0);
        List<FamilyEvent> events = focusFamily.getEvents(true);
        if (events != null)
            for (FamilyEvent fe : events)
                if (fe.getType() == type)
                    result.add(fe);
        return Collections.unmodifiableList(result);
    }

}
