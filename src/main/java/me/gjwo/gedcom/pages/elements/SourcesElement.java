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
import me.gjwo.gedcom.pages.abstractions.WebElement;
import org.gedcom4j.model.Source;

import java.io.IOException;
import java.util.Map;

public class SourcesElement extends WebElement
{
    private final String htmlString;
    public SourcesElement(Map<String,Source> sources)
    {
        htmlString = HtmlWrapper.wrapDiv(buildSourcesTable(sources));
    }

    private String buildSourcesTable(Map<String,Source> sources)
    {
        StringBuilder sb = new StringBuilder();
        if(sources != null)
        {
            sb.append("<table>");
            sb.append(buildTableTitlesRow());
            for (String key :sources.keySet())
            {
                sb.append(buildSourcesTableRow(key,sources.get(key)));
            }
            sb.append("</table>");
        }
        else sb.append("No Sources");

        return sb.toString();
    }
    private String  buildSourcesTableRow(String key, Source source)
    {
        StringBuilder sb = new StringBuilder();
        sb.append("<tr>");
        sb.append("<td>");
        sb.append(key.replace("@","").replace("S",""));
        sb.append("</td>");
        sb.append("<td>");
        sb.append(getTitle(source));
        sb.append("</td>");
        sb.append("<td>");
        sb.append(getAuthor(source));
        sb.append("</td>");
        sb.append("<td>");
        sb.append(getNoteStructLine(source));
        sb.append("</td>");
        sb.append("</tr>");
        return sb.toString();
    }
    private String buildTableTitlesRow()
    {
        String content = "";
        content +="<tr>";
        content +="<th>Ref</th>";
        content +="<th>Title</th>";
        content +="<th>Author</th>";
        content +="<th>Source note</th>";
        content += "</tr>";
        return content;
    }
    private String getNoteStructLine(Source source)
    {
        if(source!=null)
            if(source.getNoteStructures()!=null)
                if(source.getNoteStructures().get(0)!=null)
                    if(source.getNoteStructures().get(0).getLines()!=null)
                        if(source.getNoteStructures().get(0).getLines().get(0)!=null)
                            return source.getNoteStructures().get(0).getLines().get(0);
                        else return "";
                    else return "";
                else return "";
            else return "";
        else return "";
    }
    private String getTitle(Source source)
    {
        if(source!=null)
            if(source.getTitle()!=null)
                if(source.getTitle().getLines()!=null)
                    if(source.getTitle().getLines().get(0)!=null)
                        return source.getTitle().getLines().get(0);
                    else return "";
                else return "";
            else return "";
        else return "";
    }
    private String getAuthor(Source source)
    {
        if(source!=null)
            if(source.getOriginatorsAuthors()!=null)
                if(source.getOriginatorsAuthors().getLines()!=null)
                    if(source.getOriginatorsAuthors().getLines().get(0)!=null)
                        return source.getOriginatorsAuthors().getLines().get(0);
                    else return "";
                else return "";
            else return "";
        else return "";
    }
    @Override
    public String render()
    {
        return htmlString;
    }
}
