package me.gjwo.gedcom;
import org.gedcom4j.model.*;
import org.gedcom4j.model.enumerations.IndividualEventType;

import java.util.List;

public class PersonFactBuilder
{
    Individual person;
    public PersonFactBuilder(Individual person)
    {
        this.person = person;
    }
    public String getDateOfBirth() {
        StringBuilder sb = new StringBuilder();
        List<IndividualEvent> birthDates = person.getEventsOfType(
                IndividualEventType.BIRTH);
        for (IndividualEvent bd : birthDates) {
            if (bd.getDate() != null && bd.getDate().trim()
                    .length() > 0) {
                sb.append(bd.getDate());
            }
        }
        return sb.toString();
    }

    public String getPlaceOfBirth() {
        StringBuilder sb = new StringBuilder();
        List<IndividualEvent> birthDates = person.getEventsOfType(
                IndividualEventType.BIRTH);
        for (IndividualEvent bd : birthDates) {
            if (bd.getPlace() != null && bd.getPlace()
                    .getPlaceName() != null) {
                sb.append(bd.getPlace().getPlaceName());
            }
        }
        return sb.toString();
    }

    public String getDateOfDeath() {
        StringBuilder sb = new StringBuilder();
        List<IndividualEvent> DeathDates = person.getEventsOfType(
                IndividualEventType.DEATH);
        for (IndividualEvent dd : DeathDates) {
            if (dd.getDate() != null && dd.getDate().trim().length() > 0) {
                sb.append(dd.getDate());
            }
        }
        return sb.toString();
    }

    public String getPlaceOfDeath() {
        StringBuilder sb = new StringBuilder();
        List<IndividualEvent> DeathDates = person.getEventsOfType(
                IndividualEventType.DEATH);
        for (IndividualEvent dd : DeathDates) {
            if (dd.getPlace() != null && dd.getPlace()
                    .getPlaceName() != null) {
                sb.append(dd.getPlace().getPlaceName());
            }
        }
            return sb.toString();
    }

    public String getRefNumber()
    {
        return person.getXref().toString().replace("@I","Ref: ").replace("@","");
    }

    public String getDateOfBaptism() {
        StringBuilder sb = new StringBuilder();
        List<IndividualEvent> baptisms = person.getEventsOfType(IndividualEventType.BAPTISM);
        for (IndividualEvent be : baptisms) {
            if (be.getDate() != null && be.getDate().trim().length() > 0) {
                sb.append(be.getDate());
            }
        }
        return sb.toString();
    }

    public String getPlaceOfBaptism() {
        StringBuilder sb = new StringBuilder();
        List<IndividualEvent> baptisms = person.getEventsOfType(IndividualEventType.BAPTISM);
        for (IndividualEvent be : baptisms) {
            if (be.getPlace() != null && be.getPlace().getPlaceName() != null) {
                sb.append(be.getPlace().getPlaceName());
            }
        }
        return sb.toString();
    }
    public String getCensusInfo() {
        StringBuilder sb = new StringBuilder();
        List<IndividualEvent> censuses = person.getEventsOfType(IndividualEventType.CENSUS);
        for (IndividualEvent ce : censuses) {
            if (ce.getDate() != null && ce.getDate().trim().length() > 0) {
                sb.append(ce.getDate());
                sb.append(" ");
            }
            if (ce.getPlace() != null && ce.getPlace().getPlaceName() != null) {
                sb.append(ce.getPlace().getPlaceName());
            }/*
            if (ce.getDescription()!= null){
                sb.append(ce.getDescription().toString());
            }*/
            sb.append("<br>");
        }
        return sb.toString();
    }

    public String getDateOfCensus() {
        StringBuilder sb = new StringBuilder();
        List<IndividualEvent> censuses = person.getEventsOfType(IndividualEventType.CENSUS);
        for (IndividualEvent ce : censuses) {
            if (ce.getDate() != null && ce.getDate().trim().length() > 0) {
                sb.append(ce.getDate());
             }
          }
        return sb.toString();
    }

    public String getPlaceOfCensus() {
        StringBuilder sb = new StringBuilder();
        List<IndividualEvent> censuses = person.getEventsOfType(IndividualEventType.CENSUS);
        for (IndividualEvent ce : censuses) {
            if (ce.getPlace() != null && ce.getPlace().getPlaceName() != null) {
                sb.append(ce.getPlace().getPlaceName());
            }
        }
        return sb.toString();
    }

}
