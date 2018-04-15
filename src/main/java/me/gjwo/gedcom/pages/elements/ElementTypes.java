package me.gjwo.gedcom.pages.elements;

import me.gjwo.gedcom.pages.abstractions.WebElement;
import org.gedcom4j.model.Individual;

import java.util.HashMap;
import java.util.Map;

public enum ElementTypes {
    PAGE_HEADER("!HEAD!",PageHeaderElement.class, String.class),
    PERSON_LINK_ELEMENT("!NAME!",PersonLinkElement.class,Individual.class),
    PARENTS_ELEMENT("!PARENTS!",FamiliesElement.class,Individual.class),
    FAMILIES_ELEMENT("!FAMILIES_SPOUSE!",FamiliesElement.class,Individual.class),
    CENSUS_TABLE_ELEMENT("!CENSUSTAB!",CensusTableElement.class,Individual.class),
    PERSON_KEY_EVENTS_ELEMENT("!KEYFACTS!",PersonKeyEventsElement.class,Individual.class),
    PERSON_ATTRIBUTES_ELEMENT("!ATTRIBUTES!",PersonAttributesElement.class,Individual.class),
    PERSON_FACTS_SUMMARY_ELEMENT("!FACTS!",PersonFactsSummaryElement.class,Individual.class),
    CITATIONS("!CITATIONS!",null,Individual.class), //TODO
    SOURCES("!SOURCES!",null,Individual.class), //TODO
    TEST_ELEMENT("!TEST!",TestElement.class,Individual.class);
    private final String placeholder;
    private final Class webElement;
    private final Class paramClass;

    public String getPlaceholder(){return placeholder;}

    public Class getWebElement(){return webElement;}

    public Class getConstructorParam(){return paramClass;}

    ElementTypes(String placeholder, Class webElement, Class paramClass)
    {
        this.placeholder = placeholder;
        this.webElement = webElement;
        this.paramClass = paramClass;
    }
}
