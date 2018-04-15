package me.gjwo.gedcom.pages.abstractions;

import org.gedcom4j.model.Individual;

import java.io.IOException;

public abstract class WebElement
{
    public abstract String render() throws IOException;
}
