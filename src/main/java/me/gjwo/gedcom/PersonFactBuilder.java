package me.gjwo.gedcom;
import org.gedcom4j.model.*;
import org.gedcom4j.model.enumerations.IndividualAttributeType;
import org.gedcom4j.model.enumerations.IndividualEventType;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static me.gjwo.gedcom.FileUtil.readFile;

/**
 * PersonFactBuilder    -   Build strings and html fragments from an individual's data including
 *                          Event data (birth, death etc.
 *                          Attribute data  Occupation, Residence etc.
 *                          Reference Number
 *                          Data is often formatted into tables for use as an element of a page
 */
@SuppressWarnings("SpellCheckingInspection")
public class PersonFactBuilder
{
    private final Individual person;
    private final Map<IndividualEventType,String> eventMap; // Events with lables
    /**
     * Constructor
     * @param person    Individual who holds the information
     */
    public PersonFactBuilder(Individual person)
    {
        this.person = person;
        eventMap = new HashMap<>();
        for(IndividualEventType ie:IndividualEventType.values())
        {
            String s = ie.toString().toLowerCase();
            s = s.substring(0, 1).toUpperCase() + s.substring(1);
            eventMap.put(ie,s);
        }
    }
    public String getIndividualEventLable(IndividualEventType iet)
    {
        return eventMap.get(iet);
    }
    public String buildPersonFamilyLink(Individual person) {
        return "<a href=\""+"/individualsfamily/" + person.getXref()+"\"> "+ person.getFormattedName()+ "</a>";
    }

    public String buildPersonIndividualLink(Individual person) {
        return "<a href=\""+"/individual/" + person.getXref()+"\"> "+ person.getFormattedName()+ "</a>";
    }

    public String getRefNumber()
    {
        return person.getXref().replace("@I","").replace("@","");
    }

    public String buildPersonSummaryRow( boolean withLables) throws IOException {
        String content;
        /*
        <tr>
            <td>Ref:&nbsp; !REF!</td>
            <td><a href="/individualsfamily/!ID!"> !TEXT! </a></td>
            <td>Born:&nbsp; !DOB!</td>
            <td>in:&nbsp; !POB!</td>
            <td>Died:&nbsp; !DOD!</td>
            <td>in:&nbsp; !POD!</td>
        </tr>
         */
        if (withLables) content = readFile("personSummaryTableRowLables.html");
        else content = readFile("personSummaryTableRowNoLables.html");
        content = content.replace("!REF!", getRefNumber());
        content = content.replace("!ID!", person.getXref());
        content = content.replace("!FNAME!", person.getFormattedName());
        content = content.replace("!DOB!", getDateOfBirth());
        content = content.replace("!POB!", getPlaceOfBirth());
        content = content.replace("!DOD!", getDateOfDeath());
        content = content.replace("!POD!", getPlaceOfDeath());
        return content;
    }


    /**
     * buildKeyEventsTable  -   Builds a html table for key personal events
     * @param tableHeaders  true shows header row in table
     * @param eventLables   true shows event type lables for each event
     * @return              The html string for insertion in a page template
     */
    public String buildKeyEventsTable(boolean tableHeaders, boolean eventLables) throws IOException {
        StringBuilder sb = new StringBuilder();
        List<IndividualEvent> events;

        sb.append("<table>");
        if (tableHeaders) {
            sb.append("<tr><th>Event</td><th>Date</th><th>Place</th><th>Details</th></tr>");
        }
        events = person.getEventsOfType(IndividualEventType.BIRTH);
        for (IndividualEvent ce : events) {
            sb.append("<tr>");
            sb.append(buildEventRow(ce, eventLables));
            sb.append("</tr>");
        }
        events = person.getEventsOfType(IndividualEventType.BAPTISM);
        for (IndividualEvent ce : events) {
            sb.append("<tr>");
            sb.append(buildEventRow(ce, eventLables));
            sb.append("</tr>");
        }
        events = person.getEventsOfType(IndividualEventType.DEATH);
        for (IndividualEvent ce : events) {
            sb.append("<tr>");
            sb.append(buildEventRow(ce, eventLables));
            sb.append("</tr>");
        }
        sb.append("</table>");
        return sb.toString();
    }

    public String buildCensusTable(boolean tableHeaders) throws IOException{
        return buildEventsOfTypeTable(IndividualEventType.CENSUS,tableHeaders,true);
    }

    public String buildKeyEventsTable2(Boolean tableHeaders, Boolean eventLables) throws IOException {
        StringBuilder sb = new StringBuilder();
        sb.append(buildEventsOfTypeTable(IndividualEventType.BIRTH,tableHeaders,true));
        sb.append(buildEventsOfTypeTable(IndividualEventType.BAPTISM,Boolean.FALSE,true));
        sb.append(buildEventsOfTypeTable(IndividualEventType.DEATH,Boolean.FALSE,true));
        return sb.toString();
    }

    //
    // General methods for handling and presenting Personal Events
    //

    public String buildEventsOfTypeTable(IndividualEventType eventType, boolean tableHeaders, boolean eventLables) throws IOException {
        StringBuilder sb = new StringBuilder();
        List<IndividualEvent> events;

        events = person.getEventsOfType(eventType);
        if (events != null) {
            sb.append("<table>");
            if (tableHeaders) {
                sb.append("<tr><th>Event</td><th>Date</th><th>Place</th><th>Details</th></tr>");
            }
            for (IndividualEvent ce : events) {
                sb.append("<tr>");
                sb.append(buildEventRow(ce, eventLables));
                sb.append("</tr>");
            }
            sb.append("</table>");
        }
        else
        {
            sb.append("no ");
            sb.append(eventMap.get(eventType));
            sb.append(" events");
        }
        return sb.toString();
    }

    private String buildEventRow(IndividualEvent ce, boolean withLables) throws IOException {
        String content = "";
        if (withLables) content = "<td>"+eventMap.get(ce.getType())+"</td>";
        content += "<td>"+getDateOfEvent(ce)+"</td>";
        content += "<td>"+getPlaceOfEvent(ce)+"</td>";
        content += "<td>"+getDetailsOfEvent(ce)+"</td>";
        return content;
    }

    public String getDateOfEvent(IndividualEvent ce)
    {
        StringBuilder sb = new StringBuilder();
        if (ce.getDate() != null && ce.getDate().trim().length() > 0) {
            sb.append(ce.getDate());
        }
        return sb.toString();
    }

    public String getPlaceOfEvent(IndividualEvent ce)
    {
        StringBuilder sb = new StringBuilder();
        if (ce.getPlace() != null && ce.getPlace().getPlaceName() != null) {
            sb.append(ce.getPlace().getPlaceName());
        }
        return sb.toString();
    }

    public String getDetailsOfEvent(IndividualEvent ce)
    {
        StringBuilder sb = new StringBuilder();

        if (ce.getDescription() != null ) {
            sb.append(ce.getDescription());
        }
        return sb.toString();
    }
    public String getDatesOfEvent(IndividualEventType et) {
        StringBuilder sb = new StringBuilder();
        List<IndividualEvent> birthDates = person.getEventsOfType(et);
        for (IndividualEvent ev : birthDates) sb.append(getDateOfEvent(ev));
        return sb.toString();
    }

    //
    // Methods to handle personal attributes
    //


    public String buildAttributesTable(Boolean tableHeaders, boolean attributeLables) throws IOException {
        StringBuilder sb = new StringBuilder();
        List<IndividualAttribute> attributes;

        attributes = person.getAttributes();
        if (attributes != null) {
            sb.append("<table>");
            if (tableHeaders) {
                sb.append("<tr><th>Attribute</td><th>Date</th><th>Place</th><th>Details</th></tr>");
            }
            for (IndividualAttribute ia : attributes) {
                sb.append("<tr>");
                sb.append(buildAttributeRow(ia,ia.getType().toString()));
                sb.append("</tr>");
            }
            sb.append("</table>");
        }
        else sb.append("no attributes found");
        return sb.toString();
    }


    public String buildAttributesOfTypeTable(IndividualAttributeType attributeType, String attributeLable, boolean tableHeaders, boolean attributeLables) throws IOException {
        StringBuilder sb = new StringBuilder();
        List<IndividualAttribute> attributes;

        attributes = person.getAttributesOfType(attributeType);
        if (attributes != null) {
            sb.append("<table>");
            if (tableHeaders) {
                sb.append("<tr><th>Attribute</td><th>Date</th><th>Place</th><th>Details</th></tr>");
            }
            for (IndividualAttribute ia : attributes) {
                sb.append("<tr>");
                sb.append(buildAttributeRow(ia, attributeLable));
                sb.append("</tr>");
            }
            sb.append("</table>");
        }
        else
        {
            sb.append("no ");
            sb.append(attributeLable);
            sb.append(" attributes");
        }
        return sb.toString();
    }

    private String buildAttributeRow(IndividualAttribute ia, String attributeLable) throws IOException {
        String content;
        content = "<td>"+attributeLable+"</td>";
        content += "<td>"+getDateOfAttribute(ia)+"</td>";
        content += "<td>"+getPlaceOfAttribute(ia)+"</td>";
        content += "<td>"+getDescriptionOfAttribute(ia)+"</td>";
        return content;
    }

    private String getDateOfAttribute(IndividualAttribute ia)
    {
        StringBuilder sb = new StringBuilder();
        if (ia.getDate() != null && ia.getDate().trim().length() > 0) {
            sb.append(ia.getDate());
        }
        return sb.toString();
    }

    private String getPlaceOfAttribute(IndividualAttribute ia)
    {
        StringBuilder sb = new StringBuilder();
        if (ia.getPlace() != null && ia.getPlace().getPlaceName() != null) {
            sb.append(ia.getPlace().getPlaceName());
        }
        return sb.toString();
    }

    private String getDescriptionOfAttribute(IndividualAttribute ia)
    {
        StringBuilder sb = new StringBuilder();
        if (ia.getDescription() != null) {
            sb.append(ia.getDescription());
        }
        return sb.toString();
    }

//
// getters for specific types of key events
//

    public String getDateOfBirth() {
        StringBuilder sb = new StringBuilder();
        List<IndividualEvent> birthDates = person.getEventsOfType(
                IndividualEventType.BIRTH);
        for (IndividualEvent ev : birthDates) sb.append(getDateOfEvent(ev));
        return sb.toString();
    }

    public String getPlaceOfBirth() {
        StringBuilder sb = new StringBuilder();
        List<IndividualEvent> birthDates = person.getEventsOfType(
                IndividualEventType.BIRTH);
        for (IndividualEvent ev : birthDates) sb.append(getPlaceOfEvent(ev));
        return sb.toString();
    }

    public String getDateOfDeath() {
        StringBuilder sb = new StringBuilder();
        List<IndividualEvent> DeathDates = person.getEventsOfType(
                IndividualEventType.DEATH);
        for (IndividualEvent ev : DeathDates) sb.append(getDateOfEvent(ev));
        return sb.toString();
    }

    public String getPlaceOfDeath() {
        StringBuilder sb = new StringBuilder();
        List<IndividualEvent> DeathDates = person.getEventsOfType(
                IndividualEventType.DEATH);
        for (IndividualEvent ev : DeathDates) sb.append(getPlaceOfEvent(ev));
        return sb.toString();
    }

    public String getDateOfBaptism() {
        StringBuilder sb = new StringBuilder();
        List<IndividualEvent> baptisms = person.getEventsOfType(IndividualEventType.BAPTISM);
        for (IndividualEvent ev : baptisms)  sb.append(getDateOfEvent(ev));
        return sb.toString();
    }

    public String getPlaceOfBaptism() {
        StringBuilder sb = new StringBuilder();
        List<IndividualEvent> baptisms = person.getEventsOfType(IndividualEventType.BAPTISM);
        for (IndividualEvent ev : baptisms) sb.append(getPlaceOfEvent(ev));
        return sb.toString();
    }
 }
