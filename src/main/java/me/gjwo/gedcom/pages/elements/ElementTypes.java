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

import me.gjwo.gedcom.pages.abstractions.WebElement;
import org.gedcom4j.model.Family;
import org.gedcom4j.model.Individual;

public enum ElementTypes {
    PAGE_HEADER("!HEAD!",PageHeaderElement.class, String.class),
    PERSON_FAM_LINK_ELEMENT("!NAMEFAMLINK!",PersonFamLinkElement.class,Individual.class),
    PERSON_IND_LINK_ELEMENT("!NAMEINDLINK!",PersonIndLinkElement.class,Individual.class),
    CENSUS_TABLE_ELEMENT("!CENSUSTAB!",CensusTableElement.class,Individual.class),
    PERSON_KEY_EVENTS_ELEMENT("!KEYFACTS!",PersonKeyEventsElement.class,Individual.class),
    PERSON_ATTRIBUTES_ELEMENT("!ATTRIBUTES!",PersonAttributesElement.class,Individual.class),
    PERSON_FACTS_SUMMARY_ELEMENT("!FACTSUM!",PersonFactsSummaryElement.class,Individual.class),
    PARENTS_ELEMENT("!PARENTS!",ParentsElement.class,Individual.class),
    PARENTS_AND_EVENTS_ELEMENT("!PARENTSEV!",ParentsAndEventsElement.class,Individual.class),
    SPOUSE_FAMILIES_EVENTS_ELEMENT("!FAMILIES_SPOUSE!",SpouseFamsAndEventsElement.class,Individual.class),
    FAMILIES_ELEMENT("!INTERNAL USE ONLY!",FamiliesElement.class,Individual.class),
    FAMILY_KEY_EVENTS_ELEMENT("!INTERNAL USE ONLY!",FamilyKeyEventsElement.class, Family.class),
    CITATIONS("!CITATIONS!",CitationsTableElement.class,Individual.class),
    SOURCES("!SOURCES!",null,Individual.class), //TODO
    INDEX("!INDEX!",IndexElement.class,null),
    NAMES("!NAMES!",NamesElement.class,NamesParams.class),
    TEST_ELEMENT("!TEST!",TestElement.class,Individual.class);

    private final String placeholder;
    private final Class<? extends WebElement> webElement;
    private final Class paramClass;

    public String getPlaceholder(){return placeholder;}

    public Class<? extends WebElement> getWebElement(){return webElement;}

    public Class getConstructorParam(){return paramClass;}

    ElementTypes(String placeholder, Class<? extends WebElement> webElement, Class paramClass)
    {
        this.placeholder = placeholder;
        this.webElement = webElement;
        this.paramClass = paramClass;
    }
}
