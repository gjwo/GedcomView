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
import me.gjwo.gedcom.FamilyFactBuilder;
import me.gjwo.gedcom.PersonFactBuilder;
import me.gjwo.gedcom.pages.abstractions.WebElement;
import me.gjwo.gedcom.HtmlWrapper;
import org.gedcom4j.model.Family;
import org.gedcom4j.model.Individual;
import org.gedcom4j.model.IndividualReference;
import org.gedcom4j.model.enumerations.FamilyEventType;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class FamiliesElement extends WebElement
{
    private final Individual focusPerson;
    private final String htmlString;

    /**
     * FamiliesElement  -   Constructor
     * @param person        Focus person for the page
     * @param showChildren  Show children in familys
     * @param families      0 or more families involving the focus person
     */
    public FamiliesElement( Individual person, boolean showChildren, boolean showEvents, Family... families)
    {
        this.focusPerson = person;
        StringBuilder sb = new StringBuilder();
        for(Family f:families)
        {
            sb.append(buildSingleFamilyTable(f,showChildren,showEvents));
        }
        htmlString =  sb.toString();
    }

    /**
     * buildSingleCoupleTable   -   Build a table of the husband and wife of this family
     * @param family                The family
     * @return                      HTML string conbtaining the table
     */
    private String buildSingleCoupleTable(Family family)
    {
        List<String> titles = List.of("Husband","Wife");
        List<String> row = new ArrayList<>();
        List<List<String>> tableRows= new ArrayList<>();
        PersonFactBuilder husbandFB, wifeFB;

        if(family.getHusband() != null)
        {
            Individual husband = family.getHusband().getIndividual();
            husbandFB = new PersonFactBuilder(husband);
            row.add( HtmlWrapper.wrapHyperlink(husbandFB.buildPersonFamilyLink(husband),husband.getFormattedName()));
        } else row.add("No Husband recorded");
        if(family.getWife() != null)
        {
            Individual wife = family.getWife().getIndividual();
            wifeFB = new PersonFactBuilder(wife);
            row.add( HtmlWrapper.wrapHyperlink(wifeFB.buildPersonFamilyLink(wife),wife.getFormattedName()));
        }else row.add("No Wife recorded");
        tableRows.add(row);
        return HtmlWrapper.wrapTable(tableRows, titles);
    }

    /**
     * buildSingleFamilyTable   -   Builds a family table optionally including events and children
     * @param family                The family to be built
     * @param showChildren          true: build a children table
     * @param showEvents            true: build a family events table
     * @return                      HTML string containing the tables
     */
    private String buildSingleFamilyTable(Family family, boolean showChildren, boolean showEvents)
    {
        List<String> titles = List.of("Ref","Name","Birth date", "Birth place", "Death date", "Death date");
        List<String> row = new ArrayList<>();
        List<List<String>> tableRows= new ArrayList<>();
        String content = "";
        if(family==null) return content;

        FamilyFactBuilder ffb = new FamilyFactBuilder(family);
        FactPicker fp = new FactPicker(null,family);
        content += buildSingleCoupleTable(family);

        if (showEvents)
        {
            content += HtmlWrapper.wrapTable(
                            fp.pickFamilyEventTableData(List.of(
                                        FamilyEventType.MARRIAGE,
                                        FamilyEventType.DIVORCE)),
                    List.of("Event","Date","Place", "Description"));
        }

        if(showChildren) {
            if (family.getChildren() != null) {
                content += HtmlWrapper.wrapHeader("Children", 4);
                for (IndividualReference childRef : family.getChildren()) {
                    Individual child = childRef.getIndividual();
                    FactPicker cfp = new FactPicker(child, family);
                    PersonFactBuilder pfb = new PersonFactBuilder(child);
                    tableRows.add(cfp.pickIndSummaryRow(pfb));
                }
                content += HtmlWrapper.wrapTable(tableRows, titles);
            } else content += HtmlWrapper.wrapHeader("No children recorded", 4);
        }
        return content;
    }

    /**
     * render           -   returns HTML tables for all of the families in the list
     * @return              HTML string containing the tables
     */
    @Override
    public String render() {return htmlString;}
}
