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
    private final Individual focusPerson;
    private final Map<IndividualEventType,String> eventMap; // Events with lables
    private final Map<IndividualAttributeType,String> attributeMap; // Events with lables
    /**
     * Constructor
     * @param person    Individual who holds the information
     */
    public PersonFactBuilder(Individual person)
    {
        this.focusPerson = person;
        eventMap = new HashMap<>();
        for(IndividualEventType ie:IndividualEventType.values())
        {
            String s = ie.toString().toLowerCase();
            s = s.substring(0, 1).toUpperCase() + s.substring(1);
            eventMap.put(ie,s);
        }
        attributeMap = new HashMap<>();
        for(IndividualAttributeType ia:IndividualAttributeType.values())
        {
            String s = ia.toString().toLowerCase();
            s = s.substring(0, 1).toUpperCase() + s.substring(1);
            attributeMap.put(ia,s);
        }
    }
    public String getIndividualEventLable(IndividualEventType iet)
    {
        return eventMap.get(iet);
    }
    public Individual getFocusPerson() {return this.focusPerson;}

    public String buildPersonFamilyLink(Individual person) {
        return "/individualsfamily/" + person.getXref();
    }

    public String buildPersonIndividualLink(Individual person) {
        return "/individual/" + person.getXref();
    }

    public String getRefNumber()
    {
        return focusPerson.getXref().replace("@I","").replace("@","");
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
        content = content.replace("!ID!", focusPerson.getXref());
        content = content.replace("!FNAME!", focusPerson.getFormattedName());
        content = content.replace("!DOB!", getDateOfBirth());
        content = content.replace("!POB!", getPlaceOfBirth());
        content = content.replace("!DOD!", getDateOfDeath());
        content = content.replace("!POD!", getPlaceOfDeath());
        return content;
    }


    //
    // General methods for handling and presenting Personal Events
    //

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

    //
    // Methods to handle personal attributes
    //


     public String getIndividualAttributeLable(IndividualAttributeType iat)
    {
        return attributeMap.get(iat);
    }
    public String getDateOfAttribute(IndividualAttribute ia)
    {
        StringBuilder sb = new StringBuilder();
        if (ia.getDate() != null && ia.getDate().trim().length() > 0) {
            sb.append(ia.getDate());
        }
        return sb.toString();
    }

    public String getPlaceOfAttribute(IndividualAttribute ia)
    {
        StringBuilder sb = new StringBuilder();
        if (ia.getPlace() != null && ia.getPlace().getPlaceName() != null) {
            sb.append(ia.getPlace().getPlaceName());
        }
        return sb.toString();
    }

    public String getDescriptionOfAttribute(IndividualAttribute ia)
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
        List<IndividualEvent> birthDates = focusPerson.getEventsOfType(
                IndividualEventType.BIRTH);
        for (IndividualEvent ev : birthDates) sb.append(getDateOfEvent(ev));
        return sb.toString();
    }

    public String getPlaceOfBirth() {
        StringBuilder sb = new StringBuilder();
        List<IndividualEvent> birthDates = focusPerson.getEventsOfType(
                IndividualEventType.BIRTH);
        for (IndividualEvent ev : birthDates) sb.append(getPlaceOfEvent(ev));
        return sb.toString();
    }

    public String getDateOfDeath() {
        StringBuilder sb = new StringBuilder();
        List<IndividualEvent> DeathDates = focusPerson.getEventsOfType(
                IndividualEventType.DEATH);
        for (IndividualEvent ev : DeathDates) sb.append(getDateOfEvent(ev));
        return sb.toString();
    }

    public String getPlaceOfDeath() {
        StringBuilder sb = new StringBuilder();
        List<IndividualEvent> DeathDates = focusPerson.getEventsOfType(
                IndividualEventType.DEATH);
        for (IndividualEvent ev : DeathDates) sb.append(getPlaceOfEvent(ev));
        return sb.toString();
    }

    public String getDateOfBaptism() {
        StringBuilder sb = new StringBuilder();
        List<IndividualEvent> baptisms = focusPerson.getEventsOfType(IndividualEventType.BAPTISM);
        for (IndividualEvent ev : baptisms)  sb.append(getDateOfEvent(ev));
        return sb.toString();
    }

    public String getPlaceOfBaptism() {
        StringBuilder sb = new StringBuilder();
        List<IndividualEvent> baptisms = focusPerson.getEventsOfType(IndividualEventType.BAPTISM);
        for (IndividualEvent ev : baptisms) sb.append(getPlaceOfEvent(ev));
        return sb.toString();
    }
 }
