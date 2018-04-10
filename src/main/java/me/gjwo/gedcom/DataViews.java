package me.gjwo.gedcom;

import org.gedcom4j.model.enumerations.IndividualEventType;


public enum DataViews {
    CENSUS_EVENTS_DATE_ATTR_PLACE_ATTR(true,false, IndividualEventType.CENSUS,true,true);

    public final boolean inPerson;
    public final boolean inFamily;
    public final IndividualEventType iEvent;
    public final boolean includeDate;
    public final boolean includePlace;
    DataViews(boolean inP,boolean inF, IndividualEventType iev, boolean inD, boolean inPl)
    {
        this.inPerson = inP;
        this.inFamily = inF;
        this.iEvent = iev;
        this.includeDate = inD;
        this.includePlace = inPl;

    }
}
