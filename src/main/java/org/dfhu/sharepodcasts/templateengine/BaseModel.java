package org.dfhu.sharepodcasts.templateengine;

import com.fizzed.rocker.runtime.DefaultRockerModel;

public class BaseModel extends DefaultRockerModel {
    public static String docType() {
        return "<!DOCTYPE html>";
    }
}
