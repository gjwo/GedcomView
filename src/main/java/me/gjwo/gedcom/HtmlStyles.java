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

    private static String TableAncCell =
            "#tac {border-collapse:collapse; border-spacing:1px;}" +
            "#tac table {border: 1px; solid #ddd; padding: 1px;}"+
            "#tac td,tr {border: 0px; padding: 0px;}";
    public HtmlStyles()
    {
        styleMap = new HashMap<>();
        styleMap.put("tabledefault","");
        styleMap.put("tac",TableAncCell);
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
