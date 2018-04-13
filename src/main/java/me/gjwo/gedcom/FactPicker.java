package me.gjwo.gedcom;

import org.gedcom4j.model.*;
import org.gedcom4j.model.enumerations.FamilyEventType;
import org.gedcom4j.model.enumerations.IndividualAttributeType;
import org.gedcom4j.model.enumerations.IndividualEventType;

import java.util.ArrayList;
import java.util.List;

public class FactPicker {
    private final Individual person;
    private final Family family;
    private final PersonFactBuilder pfb;
    private final FamilyFactBuilder ffb;
    private DataViews dataView;
    private final List<String> cellLables = new ArrayList<>();
    private final List<String> columnLables = new ArrayList<>();

    /**
     * FactPicker   -   Constructor
     * @param person    The focus person
     * @param family    The focus Family
     */
    public FactPicker(Individual person,Family family)
    {
        this.person = person;
        if (person != null)this.pfb = new PersonFactBuilder(person);
        else this.pfb = null;
        this.family = family;
        if (family != null) this.ffb = new FamilyFactBuilder(family);
        else this.ffb = null;
    }

    //getters and setters

    public List <String> getCellLables(){return cellLables;}
    public List <String> getColumnLables(){return columnLables;}
    public void setDataView(DataViews view){dataView = view;}

    // data based on Individual Events

    /**
     * PickIndEventRow      -   Gets an list of strings to form a table row from an event
     * @param ie                An individual event to be displayed in this row
     * @param withEventLable    true: include an event lable at the start of the row
     * @return                  list of result strings
     */
    private List<String> PickIndEventRow(IndividualEvent ie, boolean withEventLable)
    {
        List <String> content = new ArrayList<>();
        if (withEventLable) content.add(pfb.getIndividualEventLable(ie.getType()));
        content.add(pfb.getDateOfEvent(ie));
        content.add(pfb.getPlaceOfEvent(ie));
        content.add(pfb.getDetailsOfEvent(ie));
        return content;
    }

    /**
     *  pickIndEventTableData    -   extracts individual event data as string arrays
     * @param eventTypes    List of events types to be included in table data
     * @return  A list of lable rows each of which is a list of strings
     */
    public List <List<String>> pickIndEventTableData(List <IndividualEventType> eventTypes)
    {
        List <List <String>> results = new ArrayList<>();
        for (IndividualEventType type:eventTypes) {
            List<IndividualEvent> events = person.getEventsOfType((type));
            for (IndividualEvent event : events) {
                results.add(PickIndEventRow(event, true));
            }
        }
        return results;
    }

    public List<String> pickIndSummaryRow(PersonFactBuilder lpfb)
    {
        List <String> results = new ArrayList<>();

        results.add(lpfb.getRefNumber());
        results.add(HtmlWrapper.wrapHyperlink(  lpfb.buildPersonFamilyLink(lpfb.getFocusPerson()),
                                                lpfb.getFocusPerson().getFormattedName()));
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
     *  pickIndEventTableData    -   extracts individual event data as string arrays
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
    // data based on Family Events

    /**
     * PickFamilyEventRow   -   Gets an list of strings to form a table row from an event
     * @param fe                A Family event to be displayed in this row
     * @param withEventLable    true: include an event lable at th estart of the row
     * @return                  list of result strings
     */
    private List<String> PickFamilyEventRow(FamilyEvent fe, boolean withEventLable)
    {
        List <String> content = new ArrayList<>();
        if (withEventLable) content.add(ffb.getFamilyEventLable(fe.getType()));
        content.add(ffb.getDateOfEvent(fe));
        content.add(ffb.getPlaceOfEvent(fe));
        content.add(ffb.getDescriptionOfEvent(fe));
        return content;
    }

    /**
     *  pickIndEventTableData    -   extracts individual event data as string arrays
     * @param eventTypes    List of events types to be included in table data
     * @return  A list of lable rows each of which is a list of strings
     */
    public List <List<String>> pickFamilyEventTableData(List <FamilyEventType> eventTypes)
    {
        List <List <String>> results = new ArrayList<>();
        for (FamilyEventType type:eventTypes) {
            List<FamilyEvent> events = ffb.getEventsOfType((type));
            for (FamilyEvent event : events) {
                results.add(PickFamilyEventRow(event, true));
            }
        }
        return results;
    }

}
