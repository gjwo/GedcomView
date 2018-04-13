package me.gjwo.gedcom.pages.elements;

import me.gjwo.gedcom.FactPicker;
import me.gjwo.gedcom.FamilyFactBuilder;
import me.gjwo.gedcom.PersonFactBuilder;
import me.gjwo.gedcom.pages.abstractions.WebElement;
import me.gjwo.gedcom.HtmlWrapper;
import org.gedcom4j.model.Family;
import org.gedcom4j.model.Individual;
import org.gedcom4j.model.IndividualReference;
import org.gedcom4j.model.enumerations.FamilyEventType;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class FamiliesElement extends WebElement
{
    private final Family[] families;
    private final Individual focusPerson;
    private final boolean showChildren;

    public FamiliesElement( Individual person, boolean showChildren, Family... families)
    {
        this.families = families;
        this.focusPerson = person;
        this.showChildren = showChildren;
    }

    private String buildSingleCoupleTable(Family family) {
        List<String> titles = List.of("Husband","Wife");
        List<String> row = new ArrayList<>();
        List<List<String>> tableRows= new ArrayList<>();
         PersonFactBuilder husbandFB, wifeFB;

        if(family.getHusband() != null)
        {
            Individual husband = family.getHusband().getIndividual();
            husbandFB = new PersonFactBuilder(husband);
            row.add( HtmlWrapper.wrapHyperlink(husbandFB.buildPersonFamilyLink(husband),husband.getFormattedName()));
        } else row.add("No Husband recorded");
        if(family.getWife() != null)
        {
            Individual wife = family.getWife().getIndividual();
            wifeFB = new PersonFactBuilder(wife);
            row.add( HtmlWrapper.wrapHyperlink(wifeFB.buildPersonFamilyLink(wife),wife.getFormattedName()));
        }else row.add("No Wife recorded");
        tableRows.add(row);
        return HtmlWrapper.wrapTable(tableRows, titles);
    }

    private String buildSingleFamilyTable(Family family, boolean showChildren, boolean showEvents) throws IOException {
        List<String> titles = List.of("Ref","Name","Birth date", "Birth place", "Death date", "Death date");
        List<String> row = new ArrayList<>();
        List<List<String>> tableRows= new ArrayList<>();
        String content = "";
        if(family==null) return content;

        FamilyFactBuilder ffb = new FamilyFactBuilder(family);
        FactPicker fp = new FactPicker(null,family);
        content += buildSingleCoupleTable(family);

        if (showEvents)
        {
            content += HtmlWrapper.wrapTable(
                            fp.pickFamilyEventTableData(List.of(
                                        FamilyEventType.MARRIAGE,
                                        FamilyEventType.DIVORCE)),
                    List.of("Event","Date","Place", "Description"));
        }

        if(showChildren) {
            if (family.getChildren() != null) {
                content += HtmlWrapper.wrapHeader("Children", 4);
                for (IndividualReference childRef : family.getChildren()) {
                    Individual child = childRef.getIndividual();
                    FactPicker cfp = new FactPicker(child, family);
                    PersonFactBuilder pfb = new PersonFactBuilder(child);
                    tableRows.add(cfp.pickIndSummaryRow(pfb));
                }
                content += HtmlWrapper.wrapTable(tableRows, titles);
            } else content += HtmlWrapper.wrapHeader("No children recorded", 4);
        }
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
