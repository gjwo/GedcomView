package me.gjwo.gedcom;

import java.util.Iterator;
import java.util.List;

public class TableBuilder {

    private static String buildTableRow(List<String> rowData)
    {
        StringBuilder content = new StringBuilder("<tr>");
        for (String cell : rowData) content.append("<td>").append(cell).append("</td>");
        content.append("</tr>");
        return content.toString();
    }
    private static String buildTableHeaderRow(List<String> rowData)
    {
        StringBuilder content = new StringBuilder("<tr>");
        for (String cell : rowData) content.append("<th>").append(cell).append("</th>");
        content.append("</tr>");
        return content.toString();
    }
    public static String buildTable(List<List<String>> tableRows, List<String> columnLables)
    {
        StringBuilder content;
        Iterator tableItr = tableRows.iterator();
        Iterator columnLableItr = columnLables.iterator();
        content = new StringBuilder("<table>");
        if (columnLableItr.hasNext()) content.append(buildTableHeaderRow(columnLables));
        while (tableItr.hasNext()) content.append(buildTableRow(((List<String>) tableItr.next())));
        content.append("</table>");
        return content.toString();
    }

}
