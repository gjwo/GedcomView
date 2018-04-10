package me.gjwo.gedcom;

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
                for (int i = 0; i < tableRows.length; i++)
                    content += buildTableRow(tableRows[i],null);
          }
        content += "/table>";
        return content;
    }

}
