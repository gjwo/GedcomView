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
                sb.append(" " + bd.getDate());
            }
        }
        return sb.toString();
    }
    public String getDateOfDeath(Individual person) {
        StringBuilder sb = new StringBuilder();
        List<IndividualEvent> DeathDates = person.getEventsOfType(
                IndividualEventType.DEATH);
        for (IndividualEvent bd : DeathDates) {
            if (bd.getDate() != null && bd.getDate().trim()
                    .length() > 0) {
                sb.append(" " + bd.getDate());
            }
        }
        return sb.toString();
    }
}
