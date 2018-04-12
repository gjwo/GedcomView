package me.gjwo.gedcom;

import org.gedcom4j.model.Family;
import org.gedcom4j.model.Individual;
import org.gedcom4j.model.IndividualAttribute;
import org.gedcom4j.model.IndividualEvent;
import org.gedcom4j.model.enumerations.IndividualAttributeType;
import org.gedcom4j.model.enumerations.IndividualEventType;

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

    enum linker{ fred}
    /**
     * FactPicker   -   Constructor
     * @param person    The focus person
     * @param family    The focus Family
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

    public List <String> getRowLables()
    {
        return rowLables;
    }
    public List <String> getColumnLables()
    {
        return columnLables;
    }

    /**
     *  getIndEventTableData    -   extracts individual event data as string arrays
     * @param eventTypes    List of events types to be included in table data
     * @return  A list of lable rows each of which is a list of strings
     */
    public List <List<String>> getIndEventTableData(List <IndividualEventType> eventTypes)
    {
        List <List <String>> results = new ArrayList<>();
        for (IndividualEventType type:eventTypes) {
            List<IndividualEvent> events = person.getEventsOfType((type));
            for (IndividualEvent event : events) {
                results.add(PickEventRow(event, true));
            }
        }
        return results;
    }
    /**
     *  getIndEventTableData    -   extracts individual event data as string arrays
     * @param eventTypes    List of events types to be included in table data
     * @return  A list of lable rows each of which is a list of strings
     */
    public List <List<String>> getIndAttributeTableData(List <IndividualAttributeType> eventTypes)
    {

        List <List <String>> results = new ArrayList<>();
        for (IndividualAttributeType type:eventTypes) {
            List<IndividualAttribute> attributes = person.getAttributesOfType((type));
            for (IndividualAttribute attribute : attributes) {
                results.add(pickAttributeRow(attribute, true));
            }
        }
        return results;
    }
    private List<String> pickAttributeRow(IndividualAttribute ia, boolean withAttributeLable)
    {
        List <String> content = new ArrayList<>();
        if (withAttributeLable) content.add(pfb.getIndividualAttributeLable(ia.getType()));
        content.add(pfb.getDateOfAttribute(ia));
        content.add(pfb.getPlaceOfAttribute(ia));
        content.add(pfb.getDescriptionOfAttribute(ia));
        return content;
    }

}
