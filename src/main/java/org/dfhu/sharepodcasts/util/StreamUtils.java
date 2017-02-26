package org.dfhu.sharepodcasts.util;

import java.util.Iterator;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class StreamUtils {

    /**
     * Converts an iterator into no parallel stream
     * @param sourceIterator - iterator to covert
     * @param <T>
     * @return - non parallel stream
     */
    public static <T> Stream<T> toStream(Iterator<T> sourceIterator) {
        Iterable<T> iterable = () -> sourceIterator;
        return StreamSupport.stream(iterable.spliterator(), false);
    }
}
