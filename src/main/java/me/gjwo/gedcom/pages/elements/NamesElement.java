package me.gjwo.gedcom.pages.elements;

import me.gjwo.gedcom.HtmlWrapper;
import me.gjwo.gedcom.PersonFactBuilder;
import me.gjwo.gedcom.pages.abstractions.WebElement;
import org.gedcom4j.model.Individual;

import java.io.IOException;
import java.util.ArrayList;

import static me.gjwo.gedcom.FileUtil.readFile;

/**
 * NameIndexPage   -   This is the page for displaying the list of htmlString
 */
public class NamesElement extends WebElement
{

    private final String htmlString;

    /**
     * NamesElement     -   Constructor
     * @param np             a structure of list of people for the index plus the sub index letter
     */
    public NamesElement(NamesParams np)
    {
        super();
        String subIndex = np.subIndex;
        ArrayList<Individual> everybody = np.everybody;
        ArrayList<String> nameIndex = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        if (subIndex!=null)
            if (!subIndex.isEmpty())
            {
                if((subIndex.regionMatches(true,0,"1",0,1)))
                {
                    //the "other" case (not matching A-Z)
                    for (Individual person :everybody)
                    {
                        PersonFactBuilder pfb = new PersonFactBuilder(person);
                        Character c = pfb.getSurname().toUpperCase().charAt(0);
                        if (c<'A'||c>'Z') makeIndexLine(person,pfb,sb);
                    }
                }
                else
                for (Individual person :everybody)
                {
                    //Specific first letter (A-Z) selected
                    PersonFactBuilder pfb = new PersonFactBuilder(person);
                    if (pfb.getSurname().regionMatches(true,0,subIndex,0,1))
                        makeIndexLine(person,pfb,sb);
                }
            }
            else sb = makeFullIndex(everybody); //no sub index, "all" selected
        else sb = makeFullIndex(everybody); //no sub index, "all" selected

        htmlString = sb.toString();
    }

    private StringBuilder makeIndexLine(Individual person, PersonFactBuilder pfb, StringBuilder sb)
    {
        sb.append(HtmlWrapper.wrapHyperlink(pfb.buildPersonIndividualLink(person), pfb.getSurnameCommaForenames()));
        sb.append("&nbsp;");
        sb.append(pfb.getShortDates());
        sb.append("<br>");
        return sb;
    }
    private StringBuilder makeFullIndex(ArrayList<Individual> everybody)
    {
        StringBuilder sb = new StringBuilder();
        for (Individual person :everybody)
        {
            PersonFactBuilder pfb = new PersonFactBuilder(person);
            makeIndexLine(person,pfb,sb);
        }
        return sb;
    }

    @Override
    public String render()  {
        return htmlString;
    }
}
