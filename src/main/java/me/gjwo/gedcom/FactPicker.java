package me.gjwo.gedcom;

import org.gedcom4j.model.Family;
import org.gedcom4j.model.Individual;
import org.gedcom4j.model.IndividualAttribute;
import org.gedcom4j.model.IndividualEvent;
import org.gedcom4j.model.enumerations.IndividualAttributeType;
import org.gedcom4j.model.enumerations.IndividualEventType;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static me.gjwo.gedcom.FileUtil.readFile;

public class FactPicker {
    private final Individual person;
    private final Family family;
    private final PersonFactBuilder pfb;
    private DataViews dataView;
    private List<String> cellLables = new ArrayList<>();
    private List<String> rowData = new ArrayList<>();
    private List<String> columnLables = new ArrayList<>();

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

    //getters and setters

    public List <String> getCellLables(){return cellLables;}
    public List <String> getColumnLables(){return columnLables;}
    public void setDataView(DataViews view){dataView = view;}

    // data based on Individual Events

    /**
     * PickEventRow         -   Gets an array of strings to form a table row from an event
     * @param ie                An individual event to be displayed in this row
     * @param withEventLable    true: include an event lable at th estart of the row
     * @return
     */
    private List<String> PickEventRow(IndividualEvent ie, boolean withEventLable)
    {
        List <String> content = new ArrayList<>();
        if (withEventLable) content.add(pfb.getIndividualEventLable(ie.getType()));
        content.add(pfb.getDateOfEvent(ie));
        content.add(pfb.getPlaceOfEvent(ie));
        content.add(pfb.getDetailsOfEvent(ie));
        return content;
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

    public List<String> pickIndSummaryRow(PersonFactBuilder lpfb)
    {
        List <String> results = new ArrayList<>();

        results.add(lpfb.getRefNumber());
        //results.add( lpfb.getFocusPerson().getXref());
        results.add(lpfb.getFocusPerson().getFormattedName());
        results.add(lpfb.getDateOfBirth());
        results.add(lpfb.getPlaceOfBirth());
        results.add(lpfb.getDateOfDeath());
        results.add(lpfb.getPlaceOfDeath());
        return results;
    }
    public List <List<String>> getIndSummaryTableData(List <Individual> people)
    {
        List <List <String>> results = new ArrayList<>();
        for (Individual lp:people) {
                results.add(pickIndSummaryRow(new PersonFactBuilder(lp)));
        }
        return results;
    }

    //Data based on Individual attributes

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
