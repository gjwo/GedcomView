package me.gjwo.gedcom;

import me.gjwo.gedcom.pages.IndividualsFamilyPage;
import org.gedcom4j.exception.GedcomParserException;
import org.gedcom4j.model.*;
import org.gedcom4j.parser.GedcomParser;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static spark.Spark.get;

public class GedcomTest
{
    public static void main(String[] args) throws IOException, GedcomParserException {
        GedcomParser gp = new GedcomParser();
        gp.load("test.GED");
        Gedcom g = gp.getGedcom();
        //System.out.println(g.toString());
        /*
        for(String key: g.getFamilies().keySet())
        {
            System.out.println(key);
            if (g.getFamilies().get(key).getHusband() != null) printIndividual("h: ", g.getFamilies().get(key).getHusband());
            if (g.getFamilies().get(key).getWife() != null) printIndividual("w: ", g.getFamilies().get(key).getWife());
            List<IndividualReference> kids = g.getFamilies().get(key).getChildren();
            if(kids != null)
            {
                for(IndividualReference kid:kids) printIndividual("c: ", kid);
            }
        }
*/
        get("/hello", (req, res) -> "Hello World");

        get("/person/:id", (req, res) ->
        {
            String id = req.params(":id");
            if(g.getIndividuals().containsKey(id))
            {
                PersonPage pp = new PersonPage(g.getIndividuals().get(id));
                return pp.toHTML();
            } else return "Unknown person";
        });
        get("/family/:id", (req, res) ->
        {
            String id = req.params(":id");
            if(g.getFamilies().containsKey(id))
            {
                FamilyPage fp = new FamilyPage(g.getFamilies().get(id));
                return fp.toHTML();
            } else return "Unknown family";
        });
        get("/individualsfamily/:id", (req, res) ->
        {
            String id = req.params(":id");
            if(g.getIndividuals().containsKey(id))
            {
                IndividualsFamilyPage pp = new IndividualsFamilyPage(g.getIndividuals().get(id));
                return pp.render();
            } else return "Unknown person";
        });
    }

    public static void printIndividual(String prefix, IndividualReference individual)
    {
        System.out.println(prefix + individual.getIndividual().getXref() + " > " + individual.getIndividual().getFormattedName());
    }

    public static String readFile(String path, Charset encoding) throws IOException
    {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);
    }
}
