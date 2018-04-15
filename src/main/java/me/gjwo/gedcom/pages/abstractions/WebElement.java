package me.gjwo.gedcom.pages.abstractions;

import org.gedcom4j.model.Individual;

import java.io.IOException;

public abstract class WebElement
{
    //Individual person;
    public WebElement(Individual person){}
    public abstract String render() throws IOException;
}
