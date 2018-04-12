package me.gjwo.gedcom;

import java.util.Iterator;
import java.util.List;

public class TableBuilder {
    public String buildTableRow(String[] rowData, String rowLable )
    {
        String content ="";
        if (rowData != null)
        {
            content = "<tr>";
            if(rowLable!=null)
            {
                content+= "<td"+rowLable+"</td>";
            }
            for (String data:rowData) content += "<td>"+data+"\td";
            content += "/tr>";
        }
        return content;
    }
    public String buildTable(String[][] tableRows, String[] ColumnLables, String[] rowLables )
    {
        String content;
        content = "<table>";
        if (tableRows != null) {
            if (ColumnLables != null) {
                content += "<tr>";
                if (rowLables != null) content += "<th></th>"; //shift columns left by one;
                for (String columnLable : ColumnLables) content += "<th>" + columnLable + "</th>";
                content += "</tr>";
            }
            if ((rowLables != null) && (rowLables.length == tableRows[0].length))
                for (int i = 0; i < tableRows.length; i++)
                    content += buildTableRow(tableRows[i],rowLables[i]);
            else
                for (String[] tableRow : tableRows) content += buildTableRow(tableRow, null);
          }
        content += "/table>";
        return content;
    }

    public String buildTableRow(List<String> rowData )
    {
        String content ="<tr>";
        for (Object aRowData : rowData) content += "<td>" + aRowData + "</td>";
        content += "</tr>";
        return content;
    }
    public String buildTableHeaderRow(List<String> rowData )
    {
        String content ="<th>";
        Iterator rowItr = rowData.iterator();
        while( rowItr.hasNext()) content += "<td>"+rowItr.next()+"</td>";
        content += "</th>";
        return content;
    }
    public String buildTable(List<List<String>> tableRows, List<String> columnLables)
    {
        String content;
        Iterator tableItr = tableRows.iterator();
        Iterator columnLableItr = columnLables.iterator();
        content = "<table>";
        if (columnLableItr.hasNext()) content += buildTableHeaderRow(columnLables);
        while (tableItr.hasNext())content += buildTableRow(((List<String>)tableItr.next()));
        content += "/table>";
        return content;
    }

}
