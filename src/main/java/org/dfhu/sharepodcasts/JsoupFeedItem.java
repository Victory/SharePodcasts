package org.dfhu.sharepodcasts;


import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


class JsoupFeedItem {

    private static final String TAG = JsoupFeedItem.class.getSimpleName();

    private final Element elm;

    JsoupFeedItem(Element elm) {
        this.elm = elm;
    }

    public String getTitle(){
        return elm.select("title").text();
    }

    public String getUrl() {

        Elements elms;

        elms = elm.select("enclosure");
        String type;
        for(Element elm: elms) {
            type = elm.attr("type");
            if (type == null || !type.startsWith("audio")) {
                continue;
            }
            return elm.attr("url");
        }

        elms = elm.select("link");
        if (elms.size() > 0) {
            return elms.get(0).text();
        }

        return "";
    }

    public long getPubDate() {
        long date = System.currentTimeMillis() / 1000L;

        Elements elms = elm.select("pubDate");
        if (elms.size() <= 0) {
            return date;
        }

        DateFormat fromFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z", Locale.ENGLISH);
        String pubDate = elms.get(0).text();

        try {
            Date parsedDate = fromFormat.parse(pubDate);
            date = parsedDate.getTime() / 1000L;
        } catch (ParseException e) {
        }

        return date;
    }
}

