package me.gjwo.gedcom;

import me.gjwo.gedcom.pages.IndividualsFamilyPage;
import me.gjwo.gedcom.pages.elements.NamesParams;
import org.gedcom4j.comparators.IndividualByLastNameFirstNameComparator;
import org.gedcom4j.exception.GedcomParserException;
import org.gedcom4j.model.*;
import org.gedcom4j.parser.GedcomParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import static me.gjwo.gedcom.FileUtil.readFile;
import static spark.Spark.get;

public class GedcomView
{
    public static void main(String[] args) throws IOException, GedcomParserException {
        GedcomParser gp = new GedcomParser();
        gp.load("test.GED");
        Gedcom g = gp.getGedcom();
        get("/hello", (req, res) -> "Hello World");
        get("/individualsfamily/:id", (req, res) ->
        {
            //example call from browser http://localhost:4567/individualsfamily/@I12@
            String id = req.params(":id");
            if(g.getIndividuals().containsKey(id))
            {
                IndividualsFamilyPage ifp = new IndividualsFamilyPage(g.getIndividuals().get(id));
                return ifp.render();
            } else return "Unknown person";
        });
        get("/individual/:id", (req, res) ->
        {
            String id = req.params(":id");
            if(g.getIndividuals().containsKey(id))
            {
                Individual person = g.getIndividuals().get(id);
                String content = readFile("IndividualPage.html");
                PersonPageBuilder pageBuilder = new PersonPageBuilder(content,"Individual page", person,null);
                return pageBuilder.render();
            } else return "Unknown person";
        });
        get("/nameindex/*", (req, res) ->
        {
            int nbrParams = req.splat().length;
            String subIndex = "";
            if (nbrParams>0) subIndex = req.splat()[0];
            // Get a list of everyone and sort them last name first
            ArrayList<Individual> everybody = new ArrayList<>(g.getIndividuals().values());
            Collections.sort(everybody,
                    new IndividualByLastNameFirstNameComparator());
            String content = readFile("NameIndexPage.html");
            NamesParams np = new NamesParams(everybody,subIndex);
            PersonPageBuilder pageBuilder = new PersonPageBuilder(content,"Index page", null,np);
            //NameIndexPage nip = new NameIndexPage(everybody,subIndex);
            return pageBuilder.render();
        });

        get("/test/:id", (req, res) ->
        {
            //example call from browser http://localhost:4567/test/@I12@
            String id = req.params(":id");
            if(g.getIndividuals().containsKey(id))
            {
                Individual person = g.getIndividuals().get(id);
                String content = readFile("TestPage.html");
                PersonPageBuilder pageBuilder = new PersonPageBuilder(content,"Test page", person,null);
                return pageBuilder.render();
            } else return "Unknown person";
        });
    }
}
