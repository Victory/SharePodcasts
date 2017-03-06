package org.dfhu.sharepodcasts.morphs;

import com.google.gson.Gson;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Id;
import spark.Request;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public abstract class AbstractLog {
    @Id
    public ObjectId id;
    public String ip;
    public String userAgent;
    public Map<String, String> headers;
    public long timeStamp;
    public String pathname;

    /**
     * Set the ip, timeStamp, userAgent, pathname and JSONified headers
     * @param req - this request
     * @param pathname - pathname to associate with this log entry
     */
    public void populateCommon(Request req, String pathname) {
        this.ip = req.ip();
        this.timeStamp = System.currentTimeMillis();
        this.userAgent = req.userAgent();

        Set<String> headers = req.headers();
        Map<String, String> headerMap = new HashMap<>(headers.size());
        for (String header: headers) {
            headerMap.put(header, req.headers(header));
        }

        this.headers = headerMap;
        this.pathname = pathname;
    }
}
