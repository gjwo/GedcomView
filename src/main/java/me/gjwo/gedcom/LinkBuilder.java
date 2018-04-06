package me.gjwo.gedcom;

import org.gedcom4j.model.*;

import java.io.IOException;

import static me.gjwo.gedcom.FileUtil.readFile;

public class LinkBuilder
{
    public String buildPersonLink(Individual person) {
        return "<a href=\""+"/individualsfamily/" + person.getXref()+"\"> "+ person.getFormattedName()+ "</a>";
    }

    private String buildPersonSummaryLink(Individual person) throws IOException {
        FactBuilder fb = new FactBuilder();
        String content = readFile("personSummaryTableRow.html");
        content = content.replace("!ID!", person.getXref());
        content = content.replace("!TEXT!", person.getFormattedName());
        content = content.replace("!DOB!", fb.getDateOfBirth(person));
        content = content.replace("!DOD!", fb.getDateOfDeath(person));
        return content;
    }
    public String buildFamilyLink(Family family){

        return "<a href=\"/family/"+family.getXref()+"\"> "+family.toString()+ "</a><br>";
    }

    public String buildChildrenLinksTable(Family family) throws IOException {
        StringBuilder sb = new StringBuilder();
        if(family.getChildren() != null)
        {
            sb.append("<table>");
            sb.append("<th> Children </th>");
            for(IndividualReference child:family.getChildren())
            {
                sb.append("<tr>");
                sb.append(buildPersonSummaryLink(child.getIndividual()));
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
