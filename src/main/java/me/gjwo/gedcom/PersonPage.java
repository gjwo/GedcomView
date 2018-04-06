package me.gjwo.gedcom;

import org.gedcom4j.model.Individual;

import java.io.IOException;

import static me.gjwo.gedcom.FileUtil.readFile;

public class PersonPage
{
    private final Individual person;

    public PersonPage(Individual person)
    {
        this.person = person;
    }

    public String toHTML()
    {
        try {
            LinkBuilder lb = new LinkBuilder();
            String content = readFile("IndividualsFamilies.html");
            content = content.replace("!NAME!", person.getFormattedName());
            content = content.replace("!SPOUSES!", lb.buildLinkedSpouseList(person));
            content = content.replace("!FAMILIES_SPOUSE!", lb.buildLinkedSpouseFamilyList(person));
            content = content.replace("!PARENTS!", lb.buildParentalFamilyList(person));
            return content;
        } catch (IOException e) {
            System.err.println("Person template missing.");
        }
        return "An unknown error has occurred.";
    }
}
