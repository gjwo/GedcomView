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
    public static String wrapHeader(String headerText, int headerLevel)
    {
        return "<h"+headerLevel +">"+ headerText+"</h"+headerLevel+">";
    }
}
