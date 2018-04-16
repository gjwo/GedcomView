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
import org.gedcom4j.model.enumerations.IndividualAttributeType;
import org.gedcom4j.model.enumerations.IndividualEventType;

import java.util.ArrayList;
import java.util.List;

public class FactPicker {
    private final Individual person;
    private final Family family;
    private final PersonFactBuilder pfb;
    private final FamilyFactBuilder ffb;
    private final List<String> cellLabels = new ArrayList<>();
    private final List<String> columnLabels = new ArrayList<>();

    /**
     * FactPicker   -   Constructor
     * @param person    The focus person
     * @param family    The focus Family
     */
    public FactPicker(Individual person,Family family)
    {
        this.person = person;
        if (person != null)this.pfb = new PersonFactBuilder(person);
        else this.pfb = null;
        this.family = family;
        if (family != null) this.ffb = new FamilyFactBuilder(family);
        else this.ffb = null;
    }

    //getters and setters

    public List <String> getCellLabels(){return cellLabels;}
    public List <String> getColumnLabels(){return columnLabels;}

    // data based on Individual Events

    /**
     * PickIndEventRow      -   Gets an list of strings to form a table row from an event
     * @param ie                An individual event to be displayed in this row
     * @param withEventLabel    true: include an event label at the start of the row
     * @return                  list of result strings
     */
    private List<String> PickIndEventRow(IndividualEvent ie, boolean withEventLabel)
    {
        List <String> content = new ArrayList<>();
        if (withEventLabel) content.add(pfb.getIndividualEventLabel(ie.getType()));
        content.add(pfb.getDateOfEvent(ie));
        content.add(pfb.getPlaceOfEvent(ie));
        content.add(pfb.getDetailsOfEvent(ie));
        return content;
    }

    /**
     *  pickIndEventTableData    -   extracts individual event data as string arrays
     * @param eventTypes    List of events types to be included in table data
     * @return  A list of label rows each of which is a list of strings
     */
    public List <List<String>> pickIndEventTableData(List <IndividualEventType> eventTypes)
    {
        List <List <String>> results = new ArrayList<>();
        for (IndividualEventType type:eventTypes)
            for (IndividualEvent event : person.getEventsOfType(type))
                results.add(PickIndEventRow(event, true));
        return results;
    }

    public List<String> pickIndSummaryRow(PersonFactBuilder lpfb)
    {
        List <String> results = new ArrayList<>();

        results.add(lpfb.getRefNumber());
        results.add(HtmlWrapper.wrapHyperlink(
                                    lpfb.buildPersonFamilyLink(lpfb.getFocusPerson()),
                                    lpfb.getFocusPerson().getFormattedName()));
        results.add(lpfb.getDateOfBirth());
        results.add(lpfb.getPlaceOfBirth());
        results.add(lpfb.getDateOfDeath());
        results.add(lpfb.getPlaceOfDeath());
        return results;
    }
    public List <List<String>> getIndSummaryTableData(List <Individual> people)
    {
        List <List <String>> results = new ArrayList<>();
        for (Individual lp:people)
            results.add(pickIndSummaryRow(new PersonFactBuilder(lp)));
        return results;
    }

    //Data based on Individual attributes

    /**
     *  pickIndEventTableData    -  extracts individual event data as string lists
     * @param eventTypes            List of events types to be included in table data
     * @return                      A list of label rows each of which is a list of strings
     */
    public List <List<String>> getIndAttributeTableData(List <IndividualAttributeType> eventTypes)
    {
        List <List <String>> results = new ArrayList<>();
        PersonFactBuilder pfb = new PersonFactBuilder(person);
        if(pfb.getAttibutesNull())return results;
        for (IndividualAttributeType type:eventTypes)
            for (IndividualAttribute attribute : person.getAttributesOfType(type))
                results.add(pickAttributeRow(attribute, true));
        return results;
    }

    private List<String> pickAttributeRow(IndividualAttribute ia, boolean withAttributeLabel)
    {
        List <String> content = new ArrayList<>();
        if (withAttributeLabel) content.add(pfb.getIndividualAttributeLabel(ia.getType()));
        content.add(pfb.getDateOfAttribute(ia));
        content.add(pfb.getPlaceOfAttribute(ia));
        content.add(pfb.getDescriptionOfAttribute(ia));
        return content;
    }
    // data based on Family Events

    /**
     * PickFamilyEventRow   -   Gets an list of strings to form a table row from an event
     * @param fe                A Family event to be displayed in this row
     * @param withEventLabel    true: include an event label at the start of the row
     * @return                  list of result strings
     */
    private List<String> PickFamilyEventRow(FamilyEvent fe, boolean withEventLabel)
    {
        List <String> content = new ArrayList<>();
        if (withEventLabel) content.add(ffb.getFamilyEventLabel(fe.getType()));
        content.add(ffb.getDateOfEvent(fe));
        content.add(ffb.getPlaceOfEvent(fe));
        content.add(ffb.getDescriptionOfEvent(fe));
        return content;
    }

    /**
     *  pickIndEventTableData    -   extracts individual event data as string arrays
     * @param eventTypes    List of events types to be included in table data
     * @return  A list of label rows each of which is a list of strings
     */
    public List <List<String>> pickFamilyEventTableData(List <FamilyEventType> eventTypes)
    {
        List <List <String>> results = new ArrayList<>();
        for (FamilyEventType type:eventTypes)
            for (FamilyEvent event : ffb.getEventsOfType(type))
                results.add(PickFamilyEventRow(event, true));
        return results;
    }

}
