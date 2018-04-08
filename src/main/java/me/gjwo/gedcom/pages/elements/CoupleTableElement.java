package me.gjwo.gedcom.pages.elements;

import me.gjwo.gedcom.PersonFactBuilder;
import me.gjwo.gedcom.pages.abstractions.WebElement;
import org.gedcom4j.model.Family;

import java.io.IOException;

import static me.gjwo.gedcom.FileUtil.readFile;


public class CoupleTableElement extends WebElement
{
    private final Family[] families;

    public CoupleTableElement(Family... families)
    {
        this.families = families;
    }

    private String buildSingleCoupleTable(Family family) throws IOException {
        String content = readFile("coupleNamesTable.html");

        String husband = "No Husband Present";
        String wife = "No Wife Present";

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

    @Override
    public String render() throws IOException
    {
        StringBuilder sb = new StringBuilder();
        for(Family f:families) sb.append(buildSingleCoupleTable(f));
        return sb.toString();
    }
}
