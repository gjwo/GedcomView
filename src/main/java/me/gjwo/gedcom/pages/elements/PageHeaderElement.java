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

package me.gjwo.gedcom.pages.elements;

import me.gjwo.gedcom.HtmlStyles;
import me.gjwo.gedcom.pages.abstractions.WebElement;

import java.io.IOException;

import static me.gjwo.gedcom.FileUtil.readFile;

public class PageHeaderElement extends WebElement{
    private final String title;

    /**
     * PageHeaderElement    -   Constructor, this element contains the HTML Header block
     * @param title             The title for the web page
     */
    public PageHeaderElement(String title){
        this.title = title;
    }

    @Override
    public String render() throws IOException {
        String content = readFile("public/head.html");
        content = content.replace("!TITLE!", this.title);
        HtmlStyles htmlStyles = new HtmlStyles();
        content = content.replace( "!STYLES!",htmlStyles.getStyle("tac"));
        return content;
    }
}
