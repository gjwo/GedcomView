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

    public IndividualsFamilyPage(Individual individual)
    {
        super();
        elements.put(ElementTypes.PAGE_HEADER, new PageHeader("Individual's family Page"));
        elements.put(ElementTypes.NAME_ELEMENT, new IndividualElement(individual));
        elements.put(ElementTypes.PARENTS_ELEMENT, new CoupleTable(
                individual.getFamiliesWhereChild() != null? individual.getFamiliesWhereChild().stream().map(FamilyChild::getFamily).toArray(Family[]::new):new Family[0]));
        elements.put(ElementTypes.FAMILIES_ELEMENT, new FamilyTable(
                individual.getFamiliesWhereSpouse() != null? individual.getFamiliesWhereSpouse().stream().map(FamilySpouse::getFamily).toArray(Family[]::new):new Family[0]));
    }

    @Override
    public String render() throws IOException {
        String content = readFile("IndividualsFamiliesPage.html");
        content = content.replace("!HEAD!", elements.get(ElementTypes.PAGE_HEADER).render());
        content = content.replace("!NAME!", elements.get(ElementTypes.NAME_ELEMENT).render());
        content = content.replace("!PARENTS!", elements.get(ElementTypes.PARENTS_ELEMENT).render());
        content = content.replace("!FAMILIES_SPOUSE!", elements.get(ElementTypes.FAMILIES_ELEMENT).render());
        return content;
    }
}
