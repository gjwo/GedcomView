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

import me.gjwo.gedcom.pages.abstractions.WebElement;
import me.gjwo.gedcom.pages.elements.ElementTypes;
import me.gjwo.gedcom.pages.elements.NamesParams;
import me.gjwo.gedcom.pages.elements.AncestorParams;
import org.gedcom4j.model.Family;
import org.gedcom4j.model.Individual;
import org.gedcom4j.model.Source;

import java.lang.reflect.Constructor;
import java.util.Map;

public class PersonPageBuilder
{
    private String htmlContent;
    PersonPageBuilder(String template,
                      String title,
                      Individual person,
                      NamesParams namesParams,
                      Map<String,Source> sources)
    {
        htmlContent = template;
        for (ElementTypes et:ElementTypes.values())
        {
            if (htmlContent.contains(et.getPlaceholder())) //search for placeholder in template
            {
                try {
                    Class<? extends WebElement> we = et.getWebElement(); //get the Class
                    WebElement webElementInst;
                    Constructor<? extends WebElement> constructor;
                    if(et.getConstructorParam()==null)
                    {
                        constructor = we.getConstructor(); // get the class constructor
                        webElementInst = constructor.newInstance(); // create a new class instance with Individual
                    } else {
                        constructor = we.getConstructor(et.getConstructorParam()); // get the class constructor
                        if(et.getConstructorParam()==Individual.class)
                            webElementInst = constructor.newInstance(person); // create a new class instance with Individual
                        else
                        if(et.getConstructorParam()==String.class)
                            webElementInst = constructor.newInstance(title); // create a new class with string
                        else
                        if(et.getConstructorParam()==NamesParams.class)
                            webElementInst = constructor.newInstance(namesParams);
                        else
                        if (et.getConstructorParam()==Map.class)
                            webElementInst = constructor.newInstance(sources);
                        else
                        if(et.getConstructorParam()==AncestorParams.class)
                            webElementInst = constructor.newInstance(new AncestorParams(person,et.getPlaceholder()));
                        else
                            throw new Exception("constructor signature not handled");
                    }
                    htmlContent = htmlContent.replace(et.getPlaceholder(), webElementInst.render()); //invoke a class method
                } catch (Exception e)
                {
                    System.out.println("Exception in PersonPageBuilder");
                    System.out.println(title);
                    System.out.println(et.toString());
                    System.out.println(e.getMessage());
                    e.printStackTrace();
                }
            }
        }
    }
    public String render()
    {
        return htmlContent;
    }
}
