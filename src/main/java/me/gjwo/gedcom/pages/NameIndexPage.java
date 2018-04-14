package me.gjwo.gedcom.pages;

import me.gjwo.gedcom.HtmlWrapper;
import me.gjwo.gedcom.PersonFactBuilder;
import me.gjwo.gedcom.pages.abstractions.WebPage;
import me.gjwo.gedcom.pages.elements.ElementTypes;
import me.gjwo.gedcom.pages.elements.PageHeaderElement;
import org.gedcom4j.model.Individual;

import java.io.IOException;
import java.util.ArrayList;

import static me.gjwo.gedcom.FileUtil.readFile;

/**
 * NameIndexPage   -   This is the page for displaying the list of names
 */
public class NameIndexPage extends WebPage {

    private final ArrayList<Individual> everybody;
    private final ArrayList<String> nameIndex;
    private String names;
    public NameIndexPage(ArrayList<Individual> everybody)
    {
        super();
        this.everybody = everybody;
        nameIndex = new ArrayList<>();
        names = "";
        StringBuilder sb = new StringBuilder();
        for (Individual person :everybody)
        {
            PersonFactBuilder pfb = new PersonFactBuilder(person);
            sb.append(HtmlWrapper.wrapHyperlink(pfb.buildPersonIndividualLink(person),pfb.getSurnameCommaForenames()));
            sb.append("&nbsp;");
            sb.append(pfb.getShortDates());
            sb.append("<br>");
        }
        names = sb.toString();
        elements.put(ElementTypes.PAGE_HEADER, new PageHeaderElement("Name Index Page"));

    }
    @Override
    public String render() throws IOException {
        String content = readFile("NameIndexPage.html");
        content = content.replace("!HEAD!", elements.get(ElementTypes.PAGE_HEADER).render());
        content = content.replace("!NAMES!",names);
        return content;
    }
}
