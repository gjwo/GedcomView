package me.gjwo.gedcom.pages.elements;

import me.gjwo.gedcom.pages.abstractions.WebElement;

import java.util.HashMap;
import java.util.Map;

public enum ElementTypes {
    PAGE_HEADER("!HEADER!",PageHeaderElement.class),
    PERSON_LINK_ELEMENT("!NAME!",PersonLinkElement.class),
    PARENTS_ELEMENT("!PARENTS!",FamiliesElement.class),
    FAMILIES_ELEMENT("!FAMILIES_SPOUSE!",FamiliesElement.class),
    CENSUS_TABLE_ELEMENT("!CENSUSTAB!",CensusTableElement.class),
    PERSON_KEY_EVENTS_ELEMENT("!KEYFACTS!",PersonKeyEventsElement.class),
    PERSON_ATTRIBUTES_ELEMENT("!ATTRIBUTES!",PersonAttributesElement.class),
    PERSON_FACTS_SUMMARY_ELEMENT("!FACTS!",PersonFactsSummaryElement.class),
    CITATIONS("!CITATIONS!",null), //TODO
    SOURCES("!SOURCES!",null), //TODO
    TEST_ELEMENT("!TEST!",TestElement.class);
    private final String placeholder;
    private final Class webElement;

    public String getPlaceholder(){return placeholder;}

    public Class getWebElement(){return webElement;}

    ElementTypes(String placeholder, Class webElement)
    {
        this.placeholder = placeholder;
        this.webElement = webElement;
    }
}
