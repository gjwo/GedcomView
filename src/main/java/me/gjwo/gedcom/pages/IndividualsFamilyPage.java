package me.gjwo.gedcom.pages;

import me.gjwo.gedcom.pages.abstractions.WebPage;
import me.gjwo.gedcom.pages.elements.CoupleTable;
import me.gjwo.gedcom.pages.elements.FamilyTable;
import me.gjwo.gedcom.pages.elements.IndividualElement;
import org.gedcom4j.model.Family;
import org.gedcom4j.model.FamilyChild;
import org.gedcom4j.model.FamilySpouse;
import org.gedcom4j.model.Individual;

import java.io.IOException;

import static me.gjwo.gedcom.FileUtil.readFile;

public class IndividualsFamilyPage extends WebPage
{
    private static int NAME_ELEMENT = 0;
    private static int PARENTS_ELEMENT = 1;
    private static int FAMILIES_ELEMENT = 2;

    public IndividualsFamilyPage(Individual individual)
    {
        super();
        elements.put(NAME_ELEMENT, new IndividualElement(individual));
        elements.put(PARENTS_ELEMENT, new CoupleTable(
                individual.getFamiliesWhereChild() != null? individual.getFamiliesWhereChild().stream().map(FamilyChild::getFamily).toArray(Family[]::new):new Family[0]));
        elements.put(FAMILIES_ELEMENT, new FamilyTable(
                individual.getFamiliesWhereSpouse() != null? individual.getFamiliesWhereSpouse().stream().map(FamilySpouse::getFamily).toArray(Family[]::new):new Family[0]));
    }

    @Override
    public String render() throws IOException {
        String content = readFile("IndividualsFamilies.html");
        content = content.replace("!NAME!", elements.get(NAME_ELEMENT).render());
        content = content.replace("!FAMILIES_CHILD!", elements.get(PARENTS_ELEMENT).render());
        content = content.replace("!FAMILIES_SPOUSE!", elements.get(FAMILIES_ELEMENT).render());
        return content;
    }
}
