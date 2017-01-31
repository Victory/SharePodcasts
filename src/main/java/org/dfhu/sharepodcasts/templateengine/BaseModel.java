package org.dfhu.sharepodcasts.templateengine;

import com.fizzed.rocker.runtime.DefaultRockerModel;
import org.dfhu.sharepodcasts.RouteManager;

public class BaseModel extends DefaultRockerModel {
    public static String docType() {
        return "<!DOCTYPE html>";
    }

    public static RouteManager route() {
        return RouteManager.getInstance();
    }

    /**
     * Escapes an html attribute
     * @param attr - string to escape
     * @return
     */
    public static String attr(String attr) {
        // TODO
        return attr;
    }
}
