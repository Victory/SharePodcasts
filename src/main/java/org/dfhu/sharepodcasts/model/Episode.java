package org.dfhu.sharepodcasts.model;


public class Episode {
    public int id;
    public int showId = -1;
    public String title = "unkown title";
    public String description;
    public String url;
    public String localUri;
    public int sizeInBytes = -1;
    public long pubDate;

    public static final int DS_NORMAL = 0;
    public static final int DS_DO_NOT_DELETE = 1;

    @Override
    public String toString() {
        return "[id:" + id + " showId:" + showId + " -- " + title + " pubDate:" + pubDate + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Episode)) {
            return false;
        }
        return ((Episode) o).url.equals(this.url);
    }

}

