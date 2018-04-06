package me.gjwo.gedcom.pages.elements;

import me.gjwo.gedcom.pages.abstractions.WebElement;

import java.io.IOException;

import static me.gjwo.gedcom.FileUtil.readFile;

public class PageHeader extends WebElement{
    private String title;
    public PageHeader(String title){
        this.title = title;
    }

    @Override
    public String render() throws IOException {
        String content = readFile("head.html");
        content = content.replace("!TITLE!", this.title);
        return content;
    }
}
