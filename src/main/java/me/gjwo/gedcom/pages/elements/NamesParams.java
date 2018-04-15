package me.gjwo.gedcom.pages.elements;

import org.gedcom4j.model.Individual;

import java.util.ArrayList;

public class NamesParams
{
    public ArrayList<Individual> everybody;
    public String subIndex;
    public NamesParams(ArrayList<Individual> people, String sub){this.everybody = people; this.subIndex = sub;}
}
