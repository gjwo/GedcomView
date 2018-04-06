package me.gjwo.gedcom.pages;

import me.gjwo.gedcom.pages.abstractions.WebPage;
import me.gjwo.gedcom.pages.elements.CoupleTable;
import me.gjwo.gedcom.pages.elements.ElementTypes;
import me.gjwo.gedcom.pages.elements.IndividualElement;
import me.gjwo.gedcom.pages.elements.PageHeader;
import org.gedcom4j.model.Individual;

import java.io.IOException;

import static me.gjwo.gedcom.FileUtil.readFile;

/**
 * IndividualPage   -   The page displays facts/events for an individual
 */
public class IndividualPage extends WebPage {
    public IndividualPage(Individual individual)
    {
        super();
        elements.put(ElementTypes.PAGE_HEADER, new PageHeader("Individual Page"));
        IndividualElement ie = new IndividualElement(individual);
        ie.setlinkFamily();
        elements.put(ElementTypes.NAME_ELEMENT, ie);

    }

    @Override
    public String render() throws IOException {
        String content = readFile("IndividualPage.html");
        content = content.replace("!HEAD!", elements.get(ElementTypes.PAGE_HEADER).render());
        content = content.replace("!NAME!", elements.get(ElementTypes.NAME_ELEMENT).render());
        return content;
    }
}
