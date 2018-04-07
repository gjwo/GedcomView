package me.gjwo.gedcom;

import org.gedcom4j.model.*;

import java.io.IOException;

import static me.gjwo.gedcom.FileUtil.readFile;

public class LinkBuilder
{
    public String buildPersonFamilyLink(Individual person) {
        return "<a href=\""+"/individualsfamily/" + person.getXref()+"\"> "+ person.getFormattedName()+ "</a>";
    }

    public String buildPersonIndividualLink(Individual person) {
        return "<a href=\""+"/individual/" + person.getXref()+"\"> "+ person.getFormattedName()+ "</a>";
    }

    private String buildPersonSummaryRow(Individual person, Boolean withLables) throws IOException {
        PersonFactBuilder fb = new PersonFactBuilder(person);
        String content;
        if (withLables) content = readFile("personSummaryTableRowLables.html");
        else content = readFile("personSummaryTableRowNoLables.html");
        content = content.replace("!REF!", fb.getRefNumber());
        content = content.replace("!ID!", person.getXref());
        content = content.replace("!TEXT!", person.getFormattedName());
        content = content.replace("!DOB!", fb.getDateOfBirth());
        content = content.replace("!POB!", fb.getPlaceOfBirth());
        content = content.replace("!DOD!", fb.getDateOfDeath());
        content = content.replace("!POD!", fb.getPlaceOfDeath());
        return content;
    }
    public String buildFamilyLink(Family family){

        return "<a href=\"/family/"+family.getXref()+"\"> "+family.toString()+ "</a><br>";
    }

    public String buildChildrenLinksTable(Family family, Boolean tableHeaders) throws IOException {
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
                sb.append("<tr>");
                sb.append(buildPersonSummaryRow(child.getIndividual(),Boolean.FALSE));
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
}
