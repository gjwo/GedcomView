package me.gjwo.gedcom.pages.abstractions;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

public abstract class WebElement
{
    public abstract String render() throws IOException;
}
