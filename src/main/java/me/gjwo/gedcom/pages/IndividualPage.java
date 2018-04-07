package me.gjwo.gedcom.pages;

import me.gjwo.gedcom.PersonFactBuilder;
import me.gjwo.gedcom.pages.abstractions.WebPage;
import me.gjwo.gedcom.pages.elements.*;
import org.gedcom4j.model.Individual;

import java.io.IOException;

import static me.gjwo.gedcom.FileUtil.readFile;

/**
 * IndividualPage   -   The page displays facts/events for an individual
 */
public class IndividualPage extends WebPage {
    private Individual individual;
    public IndividualPage(Individual individual)
    {
        super();
        this.individual = individual;
        elements.put(ElementTypes.PAGE_HEADER, new PageHeaderElement("Individual Page"));
        PersonLinkElement ple = new PersonLinkElement(individual);

        ple.setlinkFamily();
        elements.put(ElementTypes.PERSON_LINK_ELEMENT, ple);
        PersonKeyEventsElement pkfe= new PersonKeyEventsElement(individual);
        elements.put(ElementTypes.PERSON_KEY_EVENTS_ELEMENT,pkfe);
        CensusTableElement cte = new CensusTableElement(individual);
        elements.put(ElementTypes.CENSUS_TABLE_ELEMENT,cte);

    }

    @Override
    public String render() throws IOException {
        PersonFactBuilder pfb = new PersonFactBuilder(individual);
        String content = readFile("IndividualPage.html");
        content = content.replace("!HEAD!", elements.get(ElementTypes.PAGE_HEADER).render());
        content = content.replace("!NAME!", elements.get(ElementTypes.PERSON_LINK_ELEMENT).render());
        content = content.replace("!KEYFACTS!", elements.get(ElementTypes.PERSON_KEY_EVENTS_ELEMENT).render());
        content= content.replace("!CENSUSTAB!",elements.get(ElementTypes.CENSUS_TABLE_ELEMENT).render());
         return content;
    }
}
