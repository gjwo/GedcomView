package me.gjwo.gedcom.pages;

import me.gjwo.gedcom.pages.abstractions.WebPage;
import me.gjwo.gedcom.pages.elements.*;
import org.gedcom4j.model.Family;
import org.gedcom4j.model.FamilyChild;
import org.gedcom4j.model.FamilySpouse;
import org.gedcom4j.model.Individual;

import java.io.IOException;

import static me.gjwo.gedcom.FileUtil.readFile;

/**
 * IndividualsFamilyPage    -   This class produces a family group sheet form an individual's perspective covering
 *                              - Their Parents
 *                              - Any families including spouse and shared children
 */
public class IndividualsFamilyPage extends WebPage
{

    public IndividualsFamilyPage(Individual person)
    {
        super();
        elements.put(ElementTypes.PAGE_HEADER, new PageHeaderElement("Individual's family Page"));
        PersonLinkElement ple = new PersonLinkElement(person);
        ple.setLinkIndividual();
        elements.put(ElementTypes.PERSON_LINK_ELEMENT, ple);
        Family fa[];
        fa = person.getFamiliesWhereChild() != null? person.getFamiliesWhereChild().stream().map(FamilyChild::getFamily).toArray(Family[]::new):new Family[0];
        FamiliesElement pfe = new FamiliesElement(person,false,fa);
        elements.put(ElementTypes.PARENTS_ELEMENT, pfe);
        fa = person.getFamiliesWhereSpouse() != null? person.getFamiliesWhereSpouse().stream().map(FamilySpouse::getFamily).toArray(Family[]::new):new Family[0];
        FamiliesElement fe =  new FamiliesElement(person,true,fa);
        elements.put(ElementTypes.FAMILIES_ELEMENT,fe);
    }

    @Override
    public String render() throws IOException {
        String content = readFile("IndividualsFamiliesPage.html");
        content = content.replace("!HEAD!", elements.get(ElementTypes.PAGE_HEADER).render());
        content = content.replace("!NAME!", elements.get(ElementTypes.PERSON_LINK_ELEMENT).render());
        content = content.replace("!PARENTS!", elements.get(ElementTypes.PARENTS_ELEMENT).render());
        content = content.replace("!FAMILIES_SPOUSE!", elements.get(ElementTypes.FAMILIES_ELEMENT).render());
        return content;
    }
}
