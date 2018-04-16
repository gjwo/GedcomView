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
import java.util.ArrayList;

import static me.gjwo.gedcom.FileUtil.readFile;

/**
 * NameIndexPage   -   This is the page for displaying the list of htmlString
 */
public class NamesElement extends WebElement
{

    private final String htmlString;

    /**
     * NamesElement     -   Constructor
     * @param np             a structure of list of people for the index plus the sub index letter
     */
    public NamesElement(NamesParams np)
    {
        super();
        String subIndex = np.subIndex;
        ArrayList<Individual> everybody = np.everybody;
        ArrayList<String> nameIndex = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        if (subIndex!=null)
            if (!subIndex.isEmpty())
            {
                if((subIndex.regionMatches(true,0,"1",0,1)))
                {
                    //the "other" case (not matching A-Z)
                    for (Individual person :everybody)
                    {
                        PersonFactBuilder pfb = new PersonFactBuilder(person);
                        Character c = pfb.getSurname().toUpperCase().charAt(0);
                        if (c<'A'||c>'Z') makeIndexLine(person,pfb,sb);
                    }
                }
                else
                for (Individual person :everybody)
                {
                    //Specific first letter (A-Z) selected
                    PersonFactBuilder pfb = new PersonFactBuilder(person);
                    if (pfb.getSurname().regionMatches(true,0,subIndex,0,1))
                        makeIndexLine(person,pfb,sb);
                }
            }
            else sb = makeFullIndex(everybody); //no sub index, "all" selected
        else sb = makeFullIndex(everybody); //no sub index, "all" selected

        htmlString = sb.toString();
    }

    private StringBuilder makeIndexLine(Individual person, PersonFactBuilder pfb, StringBuilder sb)
    {
        sb.append(HtmlWrapper.wrapHyperlink(pfb.buildPersonIndividualLink(person), pfb.getSurnameCommaForenames()));
        sb.append("&nbsp;");
        sb.append(pfb.getShortDates());
        sb.append("<br>");
        return sb;
    }
    private StringBuilder makeFullIndex(ArrayList<Individual> everybody)
    {
        StringBuilder sb = new StringBuilder();
        for (Individual person :everybody)
        {
            PersonFactBuilder pfb = new PersonFactBuilder(person);
            makeIndexLine(person,pfb,sb);
        }
        return sb;
    }

    @Override
    public String render()  {
        return htmlString;
    }
}
