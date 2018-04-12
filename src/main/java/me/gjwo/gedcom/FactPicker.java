package me.gjwo.gedcom;

import me.gjwo.gedcom.pages.elements.PersonKeyEventsElement;
import org.gedcom4j.model.Family;
import org.gedcom4j.model.Individual;
import org.gedcom4j.model.IndividualEvent;
import org.gedcom4j.model.enumerations.IndividualEventType;

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
    private List<String> PickEventRow(IndividualEvent ce, boolean withEventLable)
    {
        List <String> content = new ArrayList<>();
        if (withEventLable) content.add(pfb.getIndividualEventLable(ce.getType()));
        content.add(pfb.getDateOfEvent(ce));
        content.add(pfb.getPlaceOfEvent(ce));
        content.add(pfb.getDetailsOfEvent(ce));
        return content;
    }

    List <String> getRowLables()
    {
        return rowLables;
    }
    List <String> getColumnLables()
    {
        return columnLables;
    }

    public List <List<String>> getTableData(IndividualEventType et)
    {
        List <List <String>> results = new ArrayList();
        List <IndividualEvent> events  = person.getEventsOfType((et));
        for (IndividualEvent event :events)
        {
            results.add(PickEventRow(event,true));
        }
        return results;
    }
}
