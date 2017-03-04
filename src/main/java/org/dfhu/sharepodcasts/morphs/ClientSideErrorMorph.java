package org.dfhu.sharepodcasts.morphs;

import org.mongodb.morphia.annotations.Entity;

@Entity("clientsideerrors")
public class ClientSideErrorMorph extends AbstractLog {
    public String errorMessage;
}
