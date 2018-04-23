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

import java.util.HashMap;
import java.util.Map;

public class HtmlStyles
{
    private final Map<String,String> styleMap;

    private static String indblock = ".indblock {background-color: white;border: 3px solid black; padding: 2px;}";
    private static String TableAncCell =
            "#tac {border-collapse: collapse:separate; border-spacing:0 10px; width: 95%; background-color: AntiqueWhite;}" +
            "#tac div {background-color: AntiqueWhite; padding: 1px; width:96%}"+
            "#tac table {border: 3px solid black; padding: 1px;}"+
            "#tac tr { padding: 2px;}"+
            "#tac td { width: 200px; height:55;}"+
            indblock;
    public HtmlStyles()
    {
        styleMap = new HashMap<>();
        styleMap.put("tabledefault","");
        styleMap.put("tac",TableAncCell);
        styleMap.put("indblock",indblock);
    }

    public String getStyle(String styleName)
    {
        if (styleName!=null)
            if(styleName.isEmpty()) return "";
            else
                return styleMap.getOrDefault(styleName,"");
        else return "";
    }
    public String getAllStyles()
    {
        String styles = "";
        for(String key:styleMap.keySet())
            styles += styleMap.getOrDefault(key,"");
        return styles;
    }
}
