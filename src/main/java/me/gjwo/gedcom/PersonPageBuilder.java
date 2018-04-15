package me.gjwo.gedcom;

import me.gjwo.gedcom.pages.abstractions.WebElement;
import me.gjwo.gedcom.pages.elements.ElementTypes;
import org.gedcom4j.model.Individual;

import java.lang.reflect.Constructor;

public class PersonPageBuilder
{
    private String htmlContent;
    PersonPageBuilder(String template, String title, Individual person)
    {
        htmlContent = template;
        System.out.println("PersonPageBuilder constructor:"+title);
        for (ElementTypes et:ElementTypes.values())
        {
            if (htmlContent.contains(et.getPlaceholder())) //search for placeholder in template
            {
                try {
                    System.out.println(et.toString());
                    Class<? extends WebElement> we = et.getWebElement(); //get the Class
                    WebElement webElementInst;
                    Constructor<? extends WebElement> constructor;
                    if(et.getConstructorParam()==null)
                    {
                        constructor = we.getConstructor(); // get the class constructor
                        webElementInst = constructor.newInstance(); // create a new class instance with Individual
                    } else{
                        constructor = we.getConstructor(et.getConstructorParam()); // get the class constructor
                        if(et.getConstructorParam()==Individual.class)
                            webElementInst = constructor.newInstance(person); // create a new class instance with Individual
                        else
                        if(et.getConstructorParam()==String.class)
                            webElementInst = constructor.newInstance(title); // create a new class with string
                        else throw new Exception("constructor signature not handled");
                    }
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
