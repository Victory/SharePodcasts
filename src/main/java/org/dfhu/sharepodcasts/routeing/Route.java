package org.dfhu.sharepodcasts.routeing;


public interface Route {
    enum METHOD {
        GET,
        POST
    }

    /** Get the url pattern for this route */
    String getPath();
    /** get the HTTP request method */
    METHOD getMethod();

    /** Add the route to spark */
    void addRoute();
}
