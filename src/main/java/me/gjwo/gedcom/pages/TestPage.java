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
@SuppressWarnings("SpellCheckingInspection")
public class TestPage extends WebPage {
    private final Individual individual;
    public TestPage(Individual individual)
    {
        super();
        this.individual = individual;
        elements.put(ElementTypes.PAGE_HEADER, new PageHeaderElement("Test Page"));
        PersonLinkElement ple = new PersonLinkElement(individual);
        ple.setlinkFamily();
        elements.put(ElementTypes.PERSON_LINK_ELEMENT, ple);
        TestElement te = new TestElement(individual);
        elements.put(ElementTypes.TEST_ELEMENT,te);
    }

    @Override
    public String render() throws IOException {
        PersonFactBuilder pfb = new PersonFactBuilder(individual);
        String content = readFile("TestPage.html");
        content = content.replace("!HEAD!", elements.get(ElementTypes.PAGE_HEADER).render());
        content = content.replace("!NAME!", elements.get(ElementTypes.PERSON_LINK_ELEMENT).render());
        content = content.replace("!TEST!", elements.get(ElementTypes.TEST_ELEMENT).render());
         return content;
    }
}
