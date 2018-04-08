package me.gjwo.gedcom;

import org.gedcom4j.model.Family;
import org.gedcom4j.model.FamilyEvent;
import org.gedcom4j.model.enumerations.FamilyEventType;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static me.gjwo.gedcom.FileUtil.readFile;

public class FamilyFactBuilder
{
    private Family family;
    public FamilyFactBuilder(Family family)
    {
        this.family = family;
    }
    /*
    public String getDateOfMarriage() {
        StringBuilder sb = new StringBuilder();
        List<FamilyEvent> birthDates = family.getEvents().getEventsOfType(FamilyEventType.MARRIAGE);
        for (FamilyEvent ev : birthDates) sb.append(getDateOfEvent(ev));
        return sb.toString();
    }

    public String getPlaceOfMarriage() {
        StringBuilder sb = new StringBuilder();
        List<FamilyEvent> birthDates = family.getEventsOfType(FamilyEventType.MARRIAGE);
        for (FamilyEvent ev : birthDates) sb.append(getPlaceOfEvent(ev));
        return sb.toString();
    }
    */
    public String getRefNumber()
    {
        return family.getXref().replace("@F","").replace("@","");
    }

     private String getDateOfEvent(FamilyEvent ce)
    {
        StringBuilder sb = new StringBuilder();
        if (ce.getDate() != null && ce.getDate().trim().length() > 0) {
            sb.append(ce.getDate());
        }
        return sb.toString();
    }

    private String getPlaceOfEvent(FamilyEvent ce)
    {
        StringBuilder sb = new StringBuilder();
        if (ce.getPlace() != null && ce.getPlace().getPlaceName() != null) {
            sb.append(ce.getPlace().getPlaceName());
        }
        return sb.toString();
    }

    private String getDetailsOfEvent(FamilyEvent ce)
    {
        /*StringBuilder sb = new StringBuilder();

        if (ce.getPlace() != null && ce.getPlace().getPlaceName() != null) {
            sb.append(ce.getPlace().getPlaceName());
        }
        sb.append("something");
        return sb.toString(); */
        return "some details tbd";
    }

    private String buildFactRow(FamilyEvent ev, Boolean withLables, String eventLable) throws IOException {
        StringBuilder sb = new StringBuilder();
        String content;
        if (withLables)
        {
            content = readFile("eventRowLables.html");
            content = content.replace("!LABLE!",eventLable);
        }
        else content = readFile("eventRowNoLables.html");
        content = content.replace("!DATE!", getDateOfEvent(ev));
        content = content.replace("!PLACE!",getPlaceOfEvent(ev));
        content = content.replace("!DETAIL!",getDetailsOfEvent(ev));
        return content;
    }

    public String buildFactTable(Boolean tableHeaders, Boolean eventLables) throws IOException {
        StringBuilder sb = new StringBuilder();
        List<FamilyEvent> events;

        sb.append("<table>");
        if (tableHeaders) {
            sb.append("<tr><th>Event</td><th>Date</th><th>Place</th><th>Details</th></tr>");
        }
        events = getEventsOfType(family,FamilyEventType.MARRIAGE);
        for (FamilyEvent fe : events) {
            sb.append("<tr>");
            sb.append(buildFactRow(fe, eventLables, "Married:"));
            sb.append("</tr>");
        }
        events = getEventsOfType(family,FamilyEventType.DIVORCE);
        for (FamilyEvent fe : events) {
            sb.append("<tr>");
            sb.append(buildFactRow(fe, eventLables, "Divorced:"));
            sb.append("</tr>");
        }
        sb.append("</table>");
        return sb.toString();
    }


    /**
     * Get a list of events of the supplied type for the specified family
     * @param family    the family to be searched
     * @param type      the type of event to get
     * @return          a list of events of the specified type
     */

    private static List<FamilyEvent> getEventsOfType(Family family,FamilyEventType type) {
        List<FamilyEvent> result = new ArrayList<>(0);
        List<FamilyEvent> events = family.getEvents();
        if (events != null) {
            for (FamilyEvent fe : events) {
                if (fe.getType() == type) {
                    result.add(fe);
                }
            }
        }
        return Collections.unmodifiableList(result);
    }

}
