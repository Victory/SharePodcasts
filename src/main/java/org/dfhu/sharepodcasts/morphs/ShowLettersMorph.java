package org.dfhu.sharepodcasts.morphs;

import org.mongodb.morphia.annotations.AlsoLoad;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

import java.util.List;

@Entity("showletters")
public class ShowLettersMorph {
    @Id
    public String titleLetter;
    @AlsoLoad("value")
    public int count;
    public List<String> shows;
    public String letter;
}
