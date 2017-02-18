package org.dfhu.sharepodcasts.viewmodels;

public class JsonResponse {
    public final boolean success;
    public final String msg;

    public JsonResponse(boolean success, String msg) {
        this.success = success;
        this.msg = msg;
    }
}
