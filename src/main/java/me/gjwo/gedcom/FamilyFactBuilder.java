package me.gjwo.gedcom;

import org.gedcom4j.model.*;
import org.gedcom4j.model.enumerations.FamilyEventType;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static me.gjwo.gedcom.FileUtil.readFile;

/**
 * FamilyFactBuilder    -   Build strings and html fragments from a family's data including
 *                          Event data Marriage, Divorce etc
 *                          Relationship data  Spouse, Children etc.
 *                          Data is often formatted into tables for use as an element of a page
 */
public class FamilyFactBuilder
{
    private final Family family;
    private Individual FocusPerson;

    public FamilyFactBuilder(Family family)
    {
        this.family = family;
    }

    public static Family getSpousalFamily(Individual person, Individual spouse)
    {
        Family fa[];
        Family f;
        if (person.getFamiliesWhereSpouse() != null)
        {
            //fa = person.getFamiliesWhereSpouse().stream().map(FamilySpouse::getFamily).toArray(Family[]::new);
            for( FamilySpouse fs: person.getFamiliesWhereSpouse())
            {
                f = fs.getFamily();
                if(f.getHusband().getIndividual().equals(spouse))return f;
                else if(f.getWife().getIndividual().equals(spouse)) return f;
            }
        }
        else return (new Family[0])[0];
        System.out.print("no spousal match");
        return (new Family[0])[0]; //shouldn't app
    }
    public static Family getParentalFamily(Individual person)
    {
        return person.getFamiliesWhereChild().get(0).getFamily();
    }
    public String getRefNumber()
    {
        return family.getXref().replace("@F","").replace("@","");
    }

    public String buildChildrenLinksTable(Boolean tableHeaders) throws IOException {
        StringBuilder sb = new StringBuilder();
        if(family.getChildren() != null)
        {
            sb.append("<table>");
            sb.append("<th> Children </th>");
            if(tableHeaders)
            {
                sb.append("<tr> <th>Ref</th><th>Name</th><th>Birth Date</th><th>Birth place</th><th>Death date</th><th>Death Place</th></tr>");
            }
            for(IndividualReference child:family.getChildren())
            {
                PersonFactBuilder pfb = new PersonFactBuilder(child.getIndividual());
                sb.append("<tr>");
                sb.append(pfb.buildPersonSummaryRow(Boolean.FALSE));
                sb.append("</tr>");
            }
            sb.append("</table>");
        } else
        {
            sb.append("<table>");
            sb.append("<th>"+"No known children"+"</th>");
            sb.append("</table>");
        }

        return sb.toString();
    }

     private String getDateOfEvent(FamilyEvent fe)
    {
        StringBuilder sb = new StringBuilder();
        if (fe.getDate() != null && fe.getDate().trim().length() > 0) {
            sb.append(fe.getDate());
        }
        return sb.toString();
    }

    private String getPlaceOfEvent(FamilyEvent fe)
    {
        StringBuilder sb = new StringBuilder();
        if (fe.getPlace() != null && fe.getPlace().getPlaceName() != null) {
            sb.append(fe.getPlace().getPlaceName());
        }
        return sb.toString();
    }

    private String getDetailsOfEvent(FamilyEvent fe)
    {
        /*StringBuilder sb = new StringBuilder();

        if (ce.getPlace() != null && ce.getPlace().getPlaceName() != null) {
            sb.append(ce.getPlace().getPlaceName());
        }
        sb.append("something");
        return sb.toString(); */
        return "some details tbd";
    }

    private String buildEventRow(FamilyEvent fe, Boolean withLables, String eventLable){
        StringBuilder sb = new StringBuilder();
        String content = "";
        if (withLables) content = "<td>"+eventLable+"</td>";
        content += "<td>"+getDateOfEvent(fe)+"</td>";
        content += "<td>"+getPlaceOfEvent(fe)+"</td>";
        content += "<td>"+getDetailsOfEvent(fe)+"</td>";
        return content;
    }

    public String buildEventTable(Boolean tableHeaders, Boolean eventLables) throws IOException {
        StringBuilder sb = new StringBuilder();
        List<FamilyEvent> events;

        sb.append("<table>");
        if (tableHeaders) {
            sb.append("<tr><th>Event</td><th>Date</th><th>Place</th><th>Details</th></tr>");
        }
        events = getEventsOfType(family,FamilyEventType.MARRIAGE);
        for (FamilyEvent fe : events) {
            sb.append("<tr>");
            sb.append(buildEventRow(fe, eventLables, "Married:"));
            sb.append("</tr>");
        }
        events = getEventsOfType(family,FamilyEventType.DIVORCE);
        for (FamilyEvent fe : events) {
            sb.append("<tr>");
            sb.append(buildEventRow(fe, eventLables, "Divorced:"));
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
