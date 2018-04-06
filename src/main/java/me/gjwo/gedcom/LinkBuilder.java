package me.gjwo.gedcom;

import org.gedcom4j.model.*;

import java.io.IOException;

import static me.gjwo.gedcom.FileUtil.readFile;

public class LinkBuilder
{
    public String buildPersonLink(Individual person) throws IOException {
        String content = readFile("personReference.html");
        content = content.replace("!ID!", person.getXref());
        content = content.replace("!TEXT!", person.getFormattedName());
        return content;
    }

    public String buildPersonSummaryLink(Individual person) throws IOException {
        FactBuilder fb = new FactBuilder();
        String content = readFile("personSummary.html");
        content = content.replace("!ID!", person.getXref());
        content = content.replace("!TEXT!", person.getFormattedName());
        content = content.replace("!DOB!", fb.getDateOfBirth(person));
        content = content.replace("!DOD!", fb.getDateOfDeath(person));
        return content;
    }
    public String buildFamilyLink(Family family) throws IOException {

        String content = readFile("familyReference.html");
        content = content.replace("!ID!", family.getXref());
        content = content.replace("!TEXT!", family.toString());
        return content;
    }


    public String buildLinkedSpouseList(Individual person) throws IOException {
        StringBuilder sb = new StringBuilder();
        if (person.getSpouses()!= null) {
            for (Individual spouse : person.getSpouses()) {
                sb.append(buildPersonLink(spouse));
            }
        }
        else sb.append("No known spouses");
        return sb.toString();
    }

    public String buildLinkedSpouseFamilyList(Individual person) throws IOException {
        StringBuilder sb = new StringBuilder();
        if (person.getFamiliesWhereSpouse()!= null) {
            for (FamilySpouse family : person.getFamiliesWhereSpouse()) {
                //sb.append(buildFamilyLink(family.getFamily()));
                sb.append(buildCoupleLink(family.getFamily()));
                sb.append(buildChildrenLinksList(family.getFamily()));
                sb.append("<br>");
            }
        }
        else sb.append("No known children");
        return sb.toString();
    }

    public String buildParentalFamilyList(Individual person) throws IOException {
        StringBuilder sb = new StringBuilder();
        if (person.getFamiliesWhereChild()!=null) {
            for (FamilyChild family : person.getFamiliesWhereChild()) {
                sb.append(buildCoupleLink(family.getFamily()));
            }
        }
        else sb.append("Parents not known");
        return sb.toString();
    }
    public String buildChildrenLinksList(Family family) throws IOException {
        StringBuilder sb = new StringBuilder();
        if(family.getChildren() != null)
        {
            for(IndividualReference child:family.getChildren())
            {
                sb.append(buildPersonSummaryLink(child.getIndividual())+"<br>");
            }

        } else sb.append("No known children.");
        return sb.toString();
    }
    public String buildCoupleLink(Family family) throws IOException
    {
        String husband = "";
        String wife = "";
        if (family.getHusband()!= null) husband = buildPersonLink(family.getHusband().getIndividual());
        if (family.getWife()!= null) wife= buildPersonLink(family.getWife().getIndividual());
        String content = readFile("couple.html");
        content = content.replace("!HUSBAND!", husband);
        content = content.replace("!WIFE!", wife);
        return content;
    }
}
