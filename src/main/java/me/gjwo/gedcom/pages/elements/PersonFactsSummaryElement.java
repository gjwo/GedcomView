package me.gjwo.gedcom.pages.elements;

import me.gjwo.gedcom.PersonFactBuilder;
import me.gjwo.gedcom.pages.abstractions.WebElement;
import org.gedcom4j.model.Individual;
import org.gedcom4j.model.IndividualReference;

import java.io.IOException;

public class PersonFactsSummaryElement extends WebElement
{
    private final Individual person;
    private boolean includeLables;
    private boolean includeTitles;

    public void setLables(Boolean includeLables){this.includeLables = includeLables;}
    public void setDates(Boolean includeTitles){this.includeTitles = includeTitles;}

    public PersonFactsSummaryElement(Individual person)
    {
        this.person = person;
        this.includeLables = true;
        this.includeTitles = false;
    }

    @Override
    public String render() throws IOException {
        String content;
        PersonFactBuilder pfb = new PersonFactBuilder(person);

        content = "<h3><table>";
        if(includeTitles)
        {
            content +="<tr> <th>Ref</th><th>Name</th><th>Birth Date</th><th>Birth place</th><th>Death date</th><th>Death Place</th></tr>";
        }
        content += "<tr>";
        content += pfb.buildPersonSummaryRow(includeLables);
        content += "</tr>";

        content += "</table></h3>";

        return content;
    }
}
