package me.gjwo.gedcom;

import me.gjwo.gedcom.pages.IndividualPage;
import me.gjwo.gedcom.pages.IndividualsFamilyPage;
import me.gjwo.gedcom.pages.NameIndexPage;
import me.gjwo.gedcom.pages.TestPage;
import org.gedcom4j.comparators.IndividualByLastNameFirstNameComparator;
import org.gedcom4j.exception.GedcomParserException;
import org.gedcom4j.model.*;
import org.gedcom4j.parser.GedcomParser;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
            //example call from browser http://localhost:4567/individual/@I12@
            String id = req.params(":id");
            if(g.getIndividuals().containsKey(id))
            {
                IndividualPage ip = new IndividualPage(g.getIndividuals().get(id));
                return ip.render();
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
            NameIndexPage nip = new NameIndexPage(everybody,subIndex);
            return nip.render();
        });

        get("/test/:id", (req, res) ->
        {
            //example call from browser http://localhost:4567/test/@I12@
            String id = req.params(":id");
            if(g.getIndividuals().containsKey(id))
            {
                Individual person = g.getIndividuals().get(id);
                String content = readFile("IndividualPage.html");
                PersonPageBuilder pageBuilder = new PersonPageBuilder(content,person);
                return pageBuilder.render();
            } else return "Unknown person";
        });
    }
/*
    public static String readFile(String path, Charset encoding) throws IOException
    {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);
    }*/
}
