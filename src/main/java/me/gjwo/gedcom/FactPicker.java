package me.gjwo.gedcom;

import me.gjwo.gedcom.pages.elements.PersonKeyEventsElement;
import org.gedcom4j.model.Family;
import org.gedcom4j.model.Individual;
import org.gedcom4j.model.IndividualEvent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FactPicker {
    private final Individual person;
    private final Family family;
    private final PersonFactBuilder pfb;
    private DataViews dataView;
    private List<String> rowLables = new ArrayList<>();
    private List<String> rowData = new ArrayList<>();
    private List<String> columnLables = new ArrayList<>();
    private List<List<String>> tableData = new ArrayList<>();

    enum linker{ fred};
    /**
     * FactPicker   -   Constructor
     * @param person
     * @param family
     */
    public FactPicker(Individual person,Family family)
    {
        this.person = person;
        this.pfb = new PersonFactBuilder(person);
        this.family = family;
    }
    public void setDataView(DataViews view){dataView = view;}
    public String[][] getTableData(DataViews view)
    {
        this.dataView = view;
        if (dataView.inPerson)
        {
            if(dataView.includeDate)columnLables.add("Date");
            if(dataView.includePlace)columnLables.add("Place");
            String s = dataView.iEvent.toString().toLowerCase();
            s = s.substring(0, 1).toUpperCase() + s.substring(1);
            rowLables.add(s);
        }
        return null;
    }
    private List<String> PickEventRow(IndividualEvent ce, boolean withEventLable) throws IOException
    {
        List <String> content = new ArrayList<>();
        if (withEventLable) content.add(pfb.getIndividualEventLable(ce.getType()));
        content.add(pfb.getDateOfEvent(ce));
        content.add(pfb.getPlaceOfEvent(ce));
        content.add(pfb.getDetailsOfEvent(ce));
        return content;
    }

    List getRowLables()
    {
        return rowLables;
    }
    List getColumnLables()
    {
        return columnLables;
    }
    boolean hasRow()
    {
        return true;
    }
    List nextRow()
    {
        return null;
    }
}
