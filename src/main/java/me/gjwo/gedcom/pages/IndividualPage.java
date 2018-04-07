package me.gjwo.gedcom.pages;

import me.gjwo.gedcom.PersonFactBuilder;
import me.gjwo.gedcom.pages.abstractions.WebPage;
import me.gjwo.gedcom.pages.elements.ElementTypes;
import me.gjwo.gedcom.pages.elements.PersonLinkElement;
import me.gjwo.gedcom.pages.elements.PageHeader;
import org.gedcom4j.model.Individual;
import org.gedcom4j.model.enumerations.IndividualEventType;

import java.io.IOException;

import static me.gjwo.gedcom.FileUtil.readFile;

/**
 * IndividualPage   -   The page displays facts/events for an individual
 */
public class IndividualPage extends WebPage {
    Individual individual;
    public IndividualPage(Individual individual)
    {
        super();
        this.individual = individual;
        elements.put(ElementTypes.PAGE_HEADER, new PageHeader("Individual Page"));
        PersonLinkElement ie = new PersonLinkElement(individual);
        ie.setlinkFamily();
        elements.put(ElementTypes.NAME_ELEMENT, ie);

    }

    @Override
    public String render() throws IOException {
        PersonFactBuilder pfb = new PersonFactBuilder(individual);
        String content = readFile("IndividualPage.html");
        content = content.replace("!HEAD!", elements.get(ElementTypes.PAGE_HEADER).render());
        content = content.replace("!NAME!", elements.get(ElementTypes.NAME_ELEMENT).render());
        content += pfb.getRefNumber()+"<br>" ;
        content += "Birth: " + pfb.getDateOfBirth()+ " at "+ pfb.getPlaceOfBirth()+"<br>";
        content += "Baptism: "+pfb.getDateOfBaptism()+ " at "+ pfb.getPlaceOfBaptism()+"<br>";
        content += "Death: " + pfb.getDateOfDeath()+ " at "+ pfb.getPlaceOfDeath()+"<br>";
        content += "Census: "+ pfb.getCensusInfo()+"<br>";
        /*
        if (individual.getEventsOfType(IndividualEventType.CENSUS)!=null)
        {
            IndividualEvent ie[] = ((IndividualEvent[]) individual.getEventsOfType(IndividualEventType.CENSUS).toArray());
            for (IndividualEvent census : ie)
            {
                content += "Census: " + census.toString() + "<br>-.-.-.-<br>";
            }
        }
        */
        //content += individual.getEvents().toString();
        return content;
    }
}
