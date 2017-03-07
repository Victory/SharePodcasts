package org.dfhu.sharepodcasts.util;

import org.dfhu.sharepodcasts.morphs.AbstractLog;
import spark.Request;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class StandardRequestLog {

    private static final String[] REAL_IP_HEADERS = {
            "X-Forwarded-For",
            "X-Real-IP"
    };

    /**
     * Create an abstract log object with ip, timestamp, headers, etc... populated from req
     * @param cls - class of the object you want to return
     * @param req
     * @param pathname
     * <T> T mock(Class<T> type)
     */
    public static <T extends AbstractLog> T build(Class<T> cls, Request req, String pathname) {
        AbstractLog log = getLogInstance(cls);

        log.ip = getRealIp(req);
        log.timeStamp = System.currentTimeMillis();
        log.userAgent = req.userAgent();

        Set<String> headers = req.headers();
        Map<String, String> headerMap = new HashMap<>(headers.size());
        for (String header: headers) {
            headerMap.put(header, req.headers(header));
        }

        log.headers = headerMap;
        log.pathname = pathname;

        return (T) log;
    }

    private static String getRealIp(Request req) {
        String ip;
        for (String kk: REAL_IP_HEADERS) {
           ip = req.headers(kk);
           if (ip != null) {
               return ip;
           }
        }
        return req.ip();
    }

    private static <T extends AbstractLog> AbstractLog getLogInstance(Class<T> cls) {
        AbstractLog log;
        try {
            log = (AbstractLog) cls.getConstructors()[0].newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException("Can't create class, make sure the there is a public zero parameter constructor for: " + cls.getName());
        }
        return log;
    }
}
