package org.dfhu.sharepodcasts.morphs;

import org.mongodb.morphia.annotations.Entity;

@Entity("contact")
public class ContactMorph extends AbstractLog {
    public String email;
    public String name;
    public String msg;
}
