package me.gjwo.gedcom;

import java.util.Iterator;
import java.util.List;

public class TableBuilder {
    private String buildTableRow(String[] rowData, String rowLable)
    {
        StringBuilder content = new StringBuilder();
        if (rowData != null)
        {
            content = new StringBuilder("<tr>");
            if(rowLable!=null)
            {
                content.append("<td").append(rowLable).append("</td>");
            }
            for (String data:rowData) content.append("<td>").append(data).append("\td");
            content.append("/tr>");
        }
        return content.toString();
    }
    public String buildTable(String[][] tableRows, String[] ColumnLables, String[] rowLables )
    {
        StringBuilder content;
        content = new StringBuilder("<table>");
        if (tableRows != null) {
            if (ColumnLables != null) {
                content.append("<tr>");
                if (rowLables != null) content.append("<th></th>"); //shift columns left by one;
                for (String columnLable : ColumnLables) content.append("<th>").append(columnLable).append("</th>");
                content.append("</tr>");
            }
            if ((rowLables != null) && (rowLables.length == tableRows[0].length))
                for (int i = 0; i < tableRows.length; i++)
                    content.append(buildTableRow(tableRows[i], rowLables[i]));
            else
                for (String[] tableRow : tableRows) content.append(buildTableRow(tableRow, null));
          }
        content.append("</table>");
        return content.toString();
    }

    private String buildTableRow(List<String> rowData)
    {
        StringBuilder content = new StringBuilder("<tr>");
        for (String cell : rowData) content.append("<td>").append(cell).append("</td>");
        content.append("</tr>");
        return content.toString();
    }
    private String buildTableHeaderRow(List<String> rowData)
    {
        StringBuilder content = new StringBuilder("<th>");
        for (String cell : rowData) content.append("<td>").append(cell).append("</td>");
        content.append("</th>");
        return content.toString();
    }
    public String buildTable(List<List<String>> tableRows, List<String> columnLables)
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
