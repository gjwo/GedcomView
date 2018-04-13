package me.gjwo.gedcom;

import java.util.Iterator;
import java.util.List;

/**
 * HtmlWrapper  -   Wraps information in HTML constructs
 */
public class HtmlWrapper
{

    public static String wrapTableRow(List<String> rowData)
    {
        StringBuilder content = new StringBuilder("<tr>");
        for (String cell : rowData) content.append("<td>").append(cell).append("</td>");
        content.append("</tr>");
        return content.toString();
    }
    public static String wrapTableHeaderRow(List<String> rowData)
    {
        StringBuilder content = new StringBuilder("<tr>");
        for (String cell : rowData) content.append("<th>").append(cell).append("</th>");
        content.append("</tr>");
        return content.toString();
    }
    public static String wrapTable(List<List<String>> tableRows, List<String> columnLables)
    {
        StringBuilder content;
        Iterator tableItr = tableRows.iterator();
        Iterator columnLableItr = columnLables.iterator();
        content = new StringBuilder("<table>");
        if (columnLableItr.hasNext()) content.append(wrapTableHeaderRow(columnLables));
        while (tableItr.hasNext()) content.append(wrapTableRow(((List<String>) tableItr.next())));
        content.append("</table>");
        return content.toString();
    }
    public static String wrapHyperlink(String link, String lable) {
        return "<a href=\""+ link+"\"> "+ lable+"</a>";
    }
    public static String wrapHeader(String headerText, int headerLevel)
    {
        return "<h"+headerLevel +">"+ headerText+"</h"+headerLevel+">";
    }
}
