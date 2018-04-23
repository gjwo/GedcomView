/*
 * Copyright (c) 2018.     GedcomView generates live web pages from a .ged file
 *                                Copyright (C) 2018  Graham J Wood
 *
 *                                This program is free software: you can redistribute it and/or modify
 *                                it under the terms of the GNU General Public License as published by
 *                                the Free Software Foundation, either version 3 of the License, or
 *                                (at your option) any later version.
 *
 *                                This program is distributed in the hope that it will be useful,
 *                                but WITHOUT ANY WARRANTY; without even the implied warranty of
 *                                MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *                                GNU General Public License for more details.
 *
 *                                You should have received a copy of the GNU General Public License
 *                                along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package me.gjwo.gedcom;

import me.gjwo.gedcom.pages.elements.NamesParams;
import org.gedcom4j.comparators.IndividualByLastNameFirstNameComparator;
import org.gedcom4j.exception.GedcomParserException;
import org.gedcom4j.model.*;
import org.gedcom4j.parser.GedcomParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static me.gjwo.gedcom.FileUtil.readFile;
import static spark.Spark.get;
import static spark.Spark.staticFiles;

public class GedcomView
{
    public static void main(String[] args) throws IOException, GedcomParserException {
        staticFiles.location("/public");
        GedcomParser gp = new GedcomParser();
        //gp.load("GJW20180414full.ged");
        gp.load(FileUtil.getBaseResourcePath()+"/public/data/test.ged");
        Gedcom g = gp.getGedcom();
        get("/hello", (req, res) -> "Hello World");
        get("/individualsfamily/:id", (req, res) ->
        {
            //example call from browser http://localhost:4567/individualsfamily/@I12@
            String id = req.params(":id");
            if(g.getIndividuals().containsKey(id))
            {
                Individual person = g.getIndividuals().get(id);
                String content = readFile("public/IndividualsFamiliesPage.html");
                PersonPageBuilder pageBuilder = new PersonPageBuilder(content,"Individual's familys", person,null,null);
                return pageBuilder.render();
            } else return "Unknown person";
        });
        get("/individual/:id", (req, res) ->
        {
            String id = req.params(":id");
            if(g.getIndividuals().containsKey(id))
            {
                Individual person = g.getIndividuals().get(id);
                String content = readFile("public/IndividualPage.html");
                PersonPageBuilder pageBuilder = new PersonPageBuilder(content,"Individual", person,null,null);
                return pageBuilder.render();
            } else return "Unknown person";
        });
        get("/ancestors/:id", (req, res) ->
        {
            //example call from browser http://localhost:4567/ancestors/@I12@
            String id = req.params(":id");
            if(g.getIndividuals().containsKey(id))
            {
                Individual person = g.getIndividuals().get(id);
                String content = readFile("public/AncestorsPage.html");
                PersonPageBuilder pageBuilder = new PersonPageBuilder(content,"Ancestors", person,null,null);
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
            everybody.sort(new IndividualByLastNameFirstNameComparator());
            String content = readFile("public/NameIndexPage.html");
            NamesParams np = new NamesParams(everybody,subIndex);
            PersonPageBuilder pageBuilder = new PersonPageBuilder(content,"Index page", null,np,null);
            //NameIndexPage nip = new NameIndexPage(everybody,subIndex);
            return pageBuilder.render();
        });
        get("/sourcesindex/", (req, res) ->
        {
            Map<String,Source> sources = g.getSources();
            String content = readFile("public/SourcesIndexPage.html");
            PersonPageBuilder pageBuilder = new PersonPageBuilder(content,"Sources Index", null,null,sources);
            return pageBuilder.render();
        });

        get("/test/:id", (req, res) ->
        {
            //example call from browser http://localhost:4567/test/@I12@
            String id = req.params(":id");
            if(g.getIndividuals().containsKey(id))
            {
                Individual person = g.getIndividuals().get(id);
                String content = readFile("public/TestPage.html");
                PersonPageBuilder pageBuilder = new PersonPageBuilder(content,"Test page", person,null,null);
                return pageBuilder.render();
            } else return "Unknown person";
        });
    }
}
