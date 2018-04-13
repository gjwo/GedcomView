package me.gjwo.gedcom.pages;

import me.gjwo.gedcom.HtmlWrapper;
import me.gjwo.gedcom.PersonFactBuilder;
import me.gjwo.gedcom.pages.abstractions.WebPage;
import me.gjwo.gedcom.pages.elements.ElementTypes;
import me.gjwo.gedcom.pages.elements.PageHeaderElement;
import org.gedcom4j.comparators.IndividualByLastNameFirstNameComparator;
import org.gedcom4j.model.Individual;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static me.gjwo.gedcom.FileUtil.readFile;

/**
 * NameIndexPage   -   This is the page for displaying the list of names
 */
public class NameIndexPage extends WebPage {

    final ArrayList<Individual> everybody;
    final ArrayList<String> nameIndex;
    public NameIndexPage(ArrayList<Individual> everybody)
    {
        super();
        this.everybody = everybody;
        nameIndex = new ArrayList<>();
        for (Individual person :everybody)
        {
            PersonFactBuilder pfb = new PersonFactBuilder(person);
            nameIndex.add(HtmlWrapper.wrapHyperlink(pfb.buildPersonIndividualLink(person),person.getFormattedName())+"&nbsp;"+pfb.getShortDates()+" ");
        }
        elements.put(ElementTypes.PAGE_HEADER, new PageHeaderElement("Name Index Page"));

    }
    @Override
    public String render() throws IOException {
        String content = readFile("NameIndexPage.html");
        content = content.replace("!HEAD!", elements.get(ElementTypes.PAGE_HEADER).render());
        content = content.replace("!NAMES!",nameIndex.toString());
        return content;
    }
}
