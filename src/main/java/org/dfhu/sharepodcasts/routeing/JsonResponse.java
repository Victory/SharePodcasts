package org.dfhu.sharepodcasts.routeing;

public class JsonResponse {

    private final boolean success;
    private final String msg;
    private final Object data;

    private final static Object EMPTY_DATA = new Object();

    public JsonResponse(boolean success, String msg) {
        this.success = success;
        this.msg = msg;
        this.data = EMPTY_DATA;
    }

    public JsonResponse(boolean success, String msg, Object data) {
        this.success = success;
        this.msg = msg;
        this.data = data;
    }
}
