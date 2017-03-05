package org.dfhu.sharepodcasts.morphs;

import org.mongodb.morphia.annotations.Entity;

@Entity("serversideerrors")
public class ServerSideErrorMorph extends AbstractLog {
    public String errorMessage;
    public String errorClass;
    public String stackTrace;
}
