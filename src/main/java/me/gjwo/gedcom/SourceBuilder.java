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

import org.gedcom4j.model.Source;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.lang.Integer.parseInt;

/**
 * SourceBuilder    -   Methods for extracting and combining data related to sources
 */
public class SourceBuilder
{
    private final Map<String,Source> sources;

    /**
     * SourceBuilder    -   Constructor
     * @param sources       The map of gedcom extracted sources with references
     */
    public SourceBuilder(Map<String,Source> sources)
    {
        this.sources = sources;
    }

    //data building methods

    /**
     * buildSourcesTable    -   Builds the data for a sources table from the Ged4Java model for sources
     * @return                  A list of table rows
     */
    public List<List<String>> buildSourcesTable()
    {
        List<List<String>> tableRows = new ArrayList<>();
        for (String key :sources.keySet())
            tableRows.add(buildSourcesTableRow(key,sources.get(key)));
        tableRows.sort((o1, o2) -> {
            try {
                int i1, i2;
                i1 = parseInt(o1.get(0));
                i2 = parseInt(o2.get(0));
                return i1 - i2;
            } catch (Exception e) {
                return 0;
            }
        });
        return tableRows;
    }

    /**
     * buildSourcesTableRow -   Builds a row of data relating to one source
     * @param key               The source reference
     * @param source            A single source data structure from the Ged4Java model
     * @return                  A list of strings containing source information
     */
    private List<String> buildSourcesTableRow(String key, Source source)
    {
        List<String> row = new ArrayList<>();
        row.add(key.replace("@","").replace("S",""));
        row.add(getTitle(source));
        row.add(getAuthor(source));
        row.add(getNoteLine(source));
        return row;
    }

    /**
     * buildColumnLables    -   builds an array of column lables
     * @return                  The column lables for sources index
     */
    public List<String> buildColumnLables()
    {
         return List.of("Ref","Title","Author","Source note");
    }

    //data extraction methods

    /**
     * getNoteLine      -  Gets a note structure line from the model
     * @param source       A single source data structure from the Ged4Java model
     * @return             A string containing the first note line
     */
    private String getNoteLine(Source source)
    {
        if(source!=null)
            if(source.getNoteStructures()!=null)
                if(source.getNoteStructures().get(0)!=null)
                    if(source.getNoteStructures().get(0).getLines()!=null)
                        if(source.getNoteStructures().get(0).getLines().get(0)!=null)
                            return source.getNoteStructures().get(0).getLines().get(0);
                        else return "";
                    else return "";
                else return "";
            else return "";
        else return "";
    }

    /**
     * getTitle     -   Gets a source title from the model
     * @param source    A single source data structure from the Ged4Java model
     * @return          A string containing the title
     */
    private String getTitle(Source source)
    {
        if(source!=null)
            if(source.getTitle()!=null)
                if(source.getTitle().getLines()!=null)
                    if(source.getTitle().getLines().get(0)!=null)
                        return source.getTitle().getLines().get(0);
                    else return "";
                else return "";
            else return "";
        else return "";
    }

    /**
     * getAuthor        -   gets the Author of the source
     * @param source        A single source data structure from the Ged4Java model
     * @return               A string containing the Author
     */
    private String getAuthor(Source source)
    {
        if(source!=null)
            if(source.getOriginatorsAuthors()!=null)
                if(source.getOriginatorsAuthors().getLines()!=null)
                    if(source.getOriginatorsAuthors().getLines().get(0)!=null)
                        return source.getOriginatorsAuthors().getLines().get(0);
                    else return "";
                else return "";
            else return "";
        else return "";
    }
}
