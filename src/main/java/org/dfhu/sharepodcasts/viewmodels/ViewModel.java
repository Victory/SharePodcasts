package org.dfhu.sharepodcasts.viewmodels;

public interface ViewModel {

    boolean isAjax();

    /**
     * Set the inner HTML of the script tag that will go in the head tag of the template
     *
     * e.g. window.foo.bar = {foo:"bar"};
     * @return
     */
    default String getTopScript() {
        return "";
    }

    /**
     * Used at the top of html elements
     * @return
     */
    default String topScript() {
        String topScript = getTopScript();
        if (topScript == null || topScript.isEmpty()) {
            return "";
        }
        return "<script>" + getTopScript() + "</script>";
    }
}
