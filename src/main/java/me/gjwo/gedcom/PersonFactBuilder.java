package me.gjwo.gedcom;
import org.gedcom4j.model.*;
import org.gedcom4j.model.enumerations.IndividualAttributeType;
import org.gedcom4j.model.enumerations.IndividualEventType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private final Map<IndividualEventType,String> eventMap; // Events with labels
    private final Map<IndividualAttributeType,String> attributeMap; // Events with labels
    private final boolean attributesNull;
    private final boolean eventsNull;
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
            String s = ie.toString();
            s = s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();
            eventMap.put(ie,s);
        }
        attributeMap = new HashMap<>();
        for(IndividualAttributeType ia:IndividualAttributeType.values())
        {
            String s = ia.toString();
            s = s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();
            attributeMap.put(ia,s);
        }
        attributesNull = (focusPerson.getAttributes()==null);
        eventsNull = (focusPerson.getEvents()==null);

    }
    String getIndividualEventLabel(IndividualEventType iet)
    {
        return eventMap.get(iet);
    }
    Individual getFocusPerson() {return this.focusPerson;}

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

    boolean getAttibutesNull() {return attributesNull;}

    boolean getEventsNull() {return eventsNull;}

    public String getSurnameCommaForenames()
    {
        String name[] = focusPerson.getFormattedName().split("/");
        if (name.length>1) return name[1]+", "+name[0];
        if (name.length>0)return name[0]+", ";
        return "no name";
    }

    public String getSurname()
    {
        String name[] = focusPerson.getFormattedName().split("/");
        if (name.length>1) return name[1]; else return  "(No surname)";
    }

    //
    // General methods for handling and presenting Personal Events
    //

    String getDateOfEvent(IndividualEvent ce)
    {
        if (eventsNull||(ce==null))return "";
        StringBuilder sb = new StringBuilder();
        if (ce.getDate() != null && ce.getDate().trim().length() > 0) {
            sb.append(ce.getDate());
        }
        return sb.toString();
    }

    String getPlaceOfEvent(IndividualEvent ce)
    {
        if (eventsNull||(ce==null))return "";
        StringBuilder sb = new StringBuilder();
        if (ce.getPlace() != null && ce.getPlace().getPlaceName() != null) {
            sb.append(ce.getPlace().getPlaceName());
        }
        return sb.toString();
    }

    String getDetailsOfEvent(IndividualEvent ce)
    {
        if (eventsNull||(ce==null))return "";
        StringBuilder sb = new StringBuilder();

        if (ce.getDescription() != null ) {
            sb.append(ce.getDescription());
        }
        return sb.toString();
    }

    //
    // Methods to handle personal attributes
    //


    String getIndividualAttributeLabel(IndividualAttributeType iat)
    {
        return attributeMap.get(iat);
    }
    String getDateOfAttribute(IndividualAttribute ia)
    {
        if (attributesNull)return "";
        StringBuilder sb = new StringBuilder();
        if (ia.getDate() != null && ia.getDate().trim().length() > 0) {
            sb.append(ia.getDate());
        }
        return sb.toString();
    }

    String getPlaceOfAttribute(IndividualAttribute ia)
    {
        if (attributesNull)return "";
        StringBuilder sb = new StringBuilder();
        if (ia.getPlace() != null && ia.getPlace().getPlaceName() != null) {
            sb.append(ia.getPlace().getPlaceName());
        }
        return sb.toString();
    }

    String getDescriptionOfAttribute(IndividualAttribute ia)
    {
        if (attributesNull)return "";
        if (ia.getDescription() != null)
            if (ia.getDescription().toString() != null)
                return ia.getDescription().toString();
        return "";
    }

//
// getters for specific types of key events
//

    String getDateOfBirth() {
        StringBuilder sb = new StringBuilder();
        List<IndividualEvent> birthDates = focusPerson.getEventsOfType(
                IndividualEventType.BIRTH);
        for (IndividualEvent ev : birthDates) sb.append(getDateOfEvent(ev));
        return sb.toString();
    }

    String getPlaceOfBirth() {
        StringBuilder sb = new StringBuilder();
        List<IndividualEvent> birthDates = focusPerson.getEventsOfType(
                IndividualEventType.BIRTH);
        for (IndividualEvent ev : birthDates) sb.append(getPlaceOfEvent(ev));
        return sb.toString();
    }

    String getDateOfDeath() {
        StringBuilder sb = new StringBuilder();
        List<IndividualEvent> DeathDates = focusPerson.getEventsOfType(
                IndividualEventType.DEATH);
        for (IndividualEvent ev : DeathDates) sb.append(getDateOfEvent(ev));
        return sb.toString();
    }

    String getPlaceOfDeath() {
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
    public String getShortDates()
    {
        return " ("+ getDateOfBirth()+" - "+getDateOfDeath()+")";
    }

 }
