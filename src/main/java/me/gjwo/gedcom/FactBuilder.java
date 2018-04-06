package me.gjwo.gedcom;
import org.gedcom4j.model.*;
import org.gedcom4j.model.enumerations.IndividualEventType;

import java.util.List;

public class FactBuilder {
    public String getDateOfBirth(Individual person) {
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

    public String getPlaceOfBirth(Individual person) {
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

    public String getDateOfDeath(Individual person) {
        StringBuilder sb = new StringBuilder();
        List<IndividualEvent> DeathDates = person.getEventsOfType(
                IndividualEventType.DEATH);
        for (IndividualEvent dd : DeathDates) {
            if (dd.getDate() != null && dd.getDate().trim()
                    .length() > 0) {
                sb.append(dd.getDate());
            }
        }
        return sb.toString();
    }

    public String getPlaceOfDeath(Individual person) {
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
}
