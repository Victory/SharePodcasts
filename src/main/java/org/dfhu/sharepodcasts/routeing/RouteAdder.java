package org.dfhu.sharepodcasts.routeing;

abstract class RouteAdder<T extends Route> {
    /** Get the url pattern for this route */
    protected abstract String getPath();
    /** get the HTTP request method */
    protected abstract Route.METHOD getMethod();

    public void doGet(RouteAdder<T> routeAdder) { Halting.haltNotImplemented(); }
    public void doPost(RouteAdder<T> routeAdder) { Halting.haltNotImplemented(); }

    public void addRoute() {
        Route.METHOD method = getMethod();
        switch (method) {
            case GET:
                doGet(this);
                break;
            case POST:
                doPost(this);
                break;
            default:
                throw new RuntimeException("Request Method not implemented");
        }
    }

}
