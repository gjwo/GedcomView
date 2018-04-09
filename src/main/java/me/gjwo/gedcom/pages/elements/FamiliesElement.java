package me.gjwo.gedcom.pages.elements;

import me.gjwo.gedcom.FamilyFactBuilder;
import me.gjwo.gedcom.PersonFactBuilder;
import me.gjwo.gedcom.pages.abstractions.WebElement;
import org.gedcom4j.model.Family;
import org.gedcom4j.model.Individual;

import java.io.IOException;

import static me.gjwo.gedcom.FileUtil.readFile;


public class FamiliesElement extends WebElement
{
    private final Family[] families;
    private final Individual focusPerson;
    private boolean showChildren;

    public FamiliesElement( Individual person, boolean showChildren, Family... families)
    {
        this.families = families;
        this.focusPerson = person;
        this.showChildren = showChildren;
    }

    private String buildSingleCoupleTable(Family family) throws IOException {
        String content = readFile("coupleNamesTable.html");

        String husband = "No Husband recorded";
        String wife = "No Wife recorded";

        PersonFactBuilder husbandFB, wifeFB;

        if(family.getHusband() != null)
        {
            husbandFB = new PersonFactBuilder(family.getHusband().getIndividual());
            husband = husbandFB.buildPersonFamilyLink(family.getHusband().getIndividual());
        }
        if(family.getWife() != null)
        {
            wifeFB = new PersonFactBuilder(family.getWife().getIndividual());
            wife = wifeFB.buildPersonFamilyLink(family.getWife().getIndividual());
        }

        content = content.replace("!HUSBAND!", husband);
        content = content.replace("!WIFE!", wife);

        return content;
    }

    private String buildSingleFamilyTable(Family family, boolean showChildren, boolean showEvents) throws IOException {
        if(family==null) return "";
        FamilyFactBuilder ffb = new FamilyFactBuilder(family);
        String content = buildSingleCoupleTable(family);
        if (showEvents) content += ffb.buildEventTable(Boolean.FALSE,Boolean.TRUE);
        if (showChildren) content += ffb.buildChildrenLinksTable(Boolean.TRUE);

        return content;
    }

    @Override
    public String render() throws IOException
    {
        StringBuilder sb = new StringBuilder();
        for(Family f:families)
        {
            sb.append(buildSingleFamilyTable(f,showChildren,true));
        }
        return sb.toString();
    }
}
