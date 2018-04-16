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

import java.util.Iterator;
import java.util.List;

/**
 * HtmlWrapper  -   Wraps information in HTML constructs
 */
public class HtmlWrapper
{

    private static String wrapTableRow(List<String> rowData)
    {
        StringBuilder content = new StringBuilder("<tr>");
        for (String cell : rowData) content.append("<td>").append(cell).append("</td>");
        content.append("</tr>");
        return content.toString();
    }
    private static String wrapTableHeaderRow(List<String> rowData)
    {
        StringBuilder content = new StringBuilder();
        content.append("<tr>");
        for (String cell : rowData)
            content.append("<th>").append(cell).append("</th>");
        content.append("</tr>");
        return content.toString();
    }
    public static String wrapTable(List<List<String>> tableRows, List<String> columnLabels)
    {
        StringBuilder content= new StringBuilder();
        content.append("<table>");
        if (!columnLabels.isEmpty()) content.append(wrapTableHeaderRow(columnLabels));
        for(List<String> row : tableRows)
            content.append(wrapTableRow(row));
        content.append("</table>");
        return content.toString();
    }
    public static String wrapHyperlink(String link, String label) {
        return "<a href=\""+ link+"\"> "+ label+"</a>";
    }
    public static String wrapHeader(String headerText, int headerLevel){return "<h"+headerLevel +">"+ headerText+"</h"+headerLevel+">";}
    public static String wrapCenter(String text){return "<center>"+ text + "</center>";}
    public static String wrapDiv(String text){return "<div>"+ text + "</div>";}
}
