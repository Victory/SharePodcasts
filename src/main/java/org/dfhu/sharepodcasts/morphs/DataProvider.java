package org.dfhu.sharepodcasts.morphs;

import com.mongodb.MongoClient;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import java.io.FileInputStream;
import java.io.IOException;

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
            throw new RuntimeException("Could not find packages");
        }

        try {
            System.getProperties().load(new FileInputStream("config.properties"));
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Could not find config file config.properties");
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
