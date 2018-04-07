package me.gjwo.gedcom;
import org.gedcom4j.model.*;
import org.gedcom4j.model.enumerations.IndividualEventType;

import java.io.IOException;
import java.util.List;

import static me.gjwo.gedcom.FileUtil.readFile;

public class PersonFactBuilder
{
    private Individual person;
    public PersonFactBuilder(Individual person)
    {
        this.person = person;
    }
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

    public String getRefNumber()
    {
        return person.getXref().replace("@I","").replace("@","");
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
    private String buildCensusRow(IndividualEvent ce, Boolean withLables) throws IOException {
        StringBuilder sb = new StringBuilder();
        String content;
        if (withLables) content = readFile("personCensusTableRowLables.html");
        else content = readFile("personCensusRowNoLables.html");
        content = content.replace("!DOC!", getDateOfEvent(ce));
        content = content.replace("!POC!", getPlaceOfEvent(ce));
        return content;
    }

    public String buildCensusTable(Boolean tableHeaders) throws IOException {
        StringBuilder sb = new StringBuilder();
        List<IndividualEvent> censuses = person.getEventsOfType(IndividualEventType.CENSUS);
        if(censuses != null)
        {
            sb.append("<table>");
            if(tableHeaders)
            {
                sb.append("<tr> <th>Census Date</th><th>Census Place</th></tr>");
            }
            for (IndividualEvent ce : censuses) {
                sb.append("<tr>");
                sb.append(buildCensusRow(ce,Boolean.FALSE));
                sb.append("</tr>");
            }
            sb.append("</table>");
        } else
        {
            sb.append("<table>");
            sb.append("<th>"+"No known census entries"+"</th>");
            sb.append("</table>");
        }
        return sb.toString();
    }

    private String getDateOfEvent(IndividualEvent ce)
    {
        StringBuilder sb = new StringBuilder();
        if (ce.getDate() != null && ce.getDate().trim().length() > 0) {
            sb.append(ce.getDate());
        }
        return sb.toString();
    }

    private String getPlaceOfEvent(IndividualEvent ce)
    {
        StringBuilder sb = new StringBuilder();
        if (ce.getPlace() != null && ce.getPlace().getPlaceName() != null) {
            sb.append(ce.getPlace().getPlaceName());
        }
        return sb.toString();
    }

    private String getDetailsOfEvent(IndividualEvent ce)
    {
        /*StringBuilder sb = new StringBuilder();

        if (ce.getPlace() != null && ce.getPlace().getPlaceName() != null) {
            sb.append(ce.getPlace().getPlaceName());
        }
        sb.append("something");
        return sb.toString(); */
        return "some details tbd";
    }

    private String buildFactRow(IndividualEvent ce, Boolean withLables, String eventLable) throws IOException {
        StringBuilder sb = new StringBuilder();
        String content;
        if (withLables)
        {
            content = readFile("eventRowLables.html");
            content = content.replace("!LABLE!",eventLable);
        }
        else content = readFile("eventRowNoLables.html");
        content = content.replace("!DATE!", getDateOfEvent(ce));
        content = content.replace("!PLACE!",getPlaceOfEvent(ce));
        content = content.replace("!DETAIL!",getDetailsOfEvent(ce));
        return content;
    }

    public String buildFactTable(Boolean tableHeaders, Boolean eventLables) throws IOException {
        StringBuilder sb = new StringBuilder();
        List<IndividualEvent> events;

        sb.append("<table>");
        if (tableHeaders) {
            sb.append("<tr><th>Event</td><th>Date</th><th>Place</th><th>Details</th></tr>");
        }
        events = person.getEventsOfType(IndividualEventType.BIRTH);
        for (IndividualEvent ce : events) {
            sb.append("<tr>");
            sb.append(buildFactRow(ce, eventLables, "Birth:"));
            sb.append("</tr>");
        }
        events = person.getEventsOfType(IndividualEventType.BAPTISM);
        for (IndividualEvent ce : events) {
            sb.append("<tr>");
            sb.append(buildFactRow(ce, eventLables, "Baptism:"));
            sb.append("</tr>");
        }
        events = person.getEventsOfType(IndividualEventType.DEATH);
        for (IndividualEvent ce : events) {
            sb.append("<tr>");
            sb.append(buildFactRow(ce, eventLables, "Death:"));
            sb.append("</tr>");
        }
        sb.append("</table>");
        return sb.toString();
    }
}
