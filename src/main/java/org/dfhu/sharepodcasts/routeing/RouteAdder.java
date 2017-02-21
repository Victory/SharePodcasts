package org.dfhu.sharepodcasts.routeing;

abstract class RouteAdder<T extends Route> {
    /** Get the url pattern for this route */
    protected abstract String getPath();
    /** get the HTTP request method */
    protected abstract Route.METHOD getMethod();

    public abstract void doGet(RouteAdder<T> routeAdder);

    public void addRoute() {
        Route.METHOD method = getMethod();
        switch (method) {
            case GET:
                doGet(this);
                break;
            case POST:
            default:
                throw new RuntimeException("Request Method not implemented");
        }
    }

}
