package me.gjwo.gedcom.pages.elements;

import me.gjwo.gedcom.LinkBuilder;
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
        LinkBuilder lb = new LinkBuilder();
        String content = readFile("coupleNamesTable.html");

        String husband = "No Husband Present";
        String wife = "No Wife Present";

        if(family.getHusband() != null) husband = lb.buildPersonFamilyLink(family.getHusband().getIndividual());
        if(family.getWife() != null) wife = lb.buildPersonFamilyLink(family.getWife().getIndividual());

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
