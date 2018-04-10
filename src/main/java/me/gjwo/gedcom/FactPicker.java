package me.gjwo.gedcom;

import org.gedcom4j.model.Family;
import org.gedcom4j.model.Individual;

import java.util.ArrayList;
import java.util.List;

public class FactPicker {
    private final Individual person;
    private final Family family;
    private DataViews dataView;

    enum linker{ fred};
    /**
     * FactPicker   -   Constructor
     * @param person
     * @param family
     */
    public FactPicker(Individual person,Family family)
    {
        this.person = person;
        this.family = family;
    }
    public void setDataView(DataViews view){dataView = view;}
    public String[][] getTableData(DataViews view)
    {
        List<String> rowLables = new ArrayList<>();
        List<String> rowData = new ArrayList<>();
        List<String> ColumnLables = new ArrayList<>();
        List<List<String>> tableData = new ArrayList<>();

        this.dataView = view;
        if (dataView.inPerson)
        {
            if(dataView.includeDate)ColumnLables.add("Date");
            if(dataView.includePlace)ColumnLables.add("Place");
            String s = dataView.iEvent.toString().toLowerCase();
            s = s.substring(0, 1).toUpperCase() + s.substring(1);
            rowLables.add(s);
        }
        return null;
    }
}
