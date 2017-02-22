package org.dfhu.sharepodcasts.routeing;

public class JsonResponse {
    public final boolean success;
    public final String msg;

    public JsonResponse(boolean success, String msg) {
        this.success = success;
        this.msg = msg;
    }
}
