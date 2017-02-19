package org.dfhu.sharepodcasts.morphs;

import com.mongodb.MongoClient;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;


public class DataProvider {
    private static final DataProvider INSTANCE = new DataProvider();
    private Datastore datastore;

    private DataProvider() {
        final Morphia morphia = new Morphia();

        // tell Morphia where to find your classes
        // can be called multiple times with different packages or classes
        try {
            morphia.mapPackage("org.dfhu.sharepodcasts.morphs");
        } catch (Throwable t) {
            throw new RuntimeException("Could not find packages: " + t.getMessage());
        }

        String mongohostname = System.getProperty("mongohostname");

        // create the Datastore connecting to the default port on the local host
        datastore = morphia.createDatastore(
                new MongoClient(mongohostname), "podcasts");
        datastore.ensureIndexes();
    }

    public static Datastore get() {
        return INSTANCE.datastore;
    }

}
