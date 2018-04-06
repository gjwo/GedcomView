package me.gjwo.gedcom;

import org.gedcom4j.model.Family;
import org.gedcom4j.model.IndividualReference;

import java.io.IOException;

import static me.gjwo.gedcom.FileUtil.readFile;

public class FamilyPage
{
    private final Family family;

    public FamilyPage(Family family)
    {
        this.family = family;
    }

    public String toHTML()
    {
        try {
            LinkBuilder lb = new LinkBuilder();
            String content = readFile("family.html");
            if(family.getWife() != null)
            {
                content = content.replace("!WIFE!", lb.buildPersonLink(family.getWife().getIndividual()));
            } else content = content.replace("!WIFE!","No wife.");
            if(family.getHusband() != null)
            {
                content = content.replace("!HUSBAND!", lb.buildPersonLink(family.getHusband().getIndividual()));
            } else content = content.replace("!HUSBAND!","No husband.");
            if(family.getChildren() != null)
            {
                StringBuilder sb = new StringBuilder();
                for(IndividualReference child:family.getChildren())
                {
                    sb.append(lb.buildPersonLink(child.getIndividual()));
                }
                content = content.replace("!CHILDREN!", sb.toString());
            } else content = content.replace("!CHILDREN!","No children.");

            return content;
        } catch (IOException e) {
            System.err.println("Person template missing.");
        }
        return "An unknown error has occurred.";
    }
}
