package me.gjwo.gedcom;

import me.gjwo.gedcom.pages.abstractions.WebElement;
import me.gjwo.gedcom.pages.elements.ElementTypes;
import org.gedcom4j.model.Individual;

import java.lang.annotation.ElementType;
import java.lang.reflect.Constructor;

public class PersonPageBuilder
{
    private String htmlContent;
    public PersonPageBuilder(String template, Individual person)
    {
        htmlContent = template;
        System.out.println("PersonPageBuilder constructor");
        for (ElementTypes et:ElementTypes.values())
        {
            if (htmlContent.contains(et.getPlaceholder())) //search for placeholder in template
            {
                try {
                    Class<WebElement> we = et.getWebElement(); //get the Class
                    Constructor<WebElement> constructor = we.getConstructor(Individual.class); // get the class constructor
                    WebElement webElementInst = constructor.newInstance(person); // create a new class instance
                    htmlContent = htmlContent.replace(et.getPlaceholder(), webElementInst.render()); //invoke a class method
                } catch (Exception e)
                {
                    System.out.println("Exception in PersonPageBuilder");
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
